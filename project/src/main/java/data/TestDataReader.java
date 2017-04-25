package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TestDataReader<T> {
	public static final String TEST_DATA_FOLDER = "src/test/resources/fixtures";
	Class<T> testDataClass;
	String filePath;
	GsonBuilder builder;
	JsonReader reader;

	public TestDataReader(String fileName, Class<T> testDataClass) {
		this.filePath = TEST_DATA_FOLDER + File.separator + fileName;
		this.testDataClass = testDataClass;
		builder = new GsonBuilder();
		if (testDataObjectContainsExposedFields(testDataClass)) {
			builder.excludeFieldsWithoutExposeAnnotation();
		}
	}

	private boolean testDataObjectContainsExposedFields(Class testDataClass) {
		while (!testDataClass.equals(Object.class)) {
			for (Field f : testDataClass.getDeclaredFields()) {
				if (testDataClass.isAnnotationPresent(Expose.class)) {
					return true;
				}
			}
			testDataClass = testDataClass.getSuperclass();
		}
		return false;
	}

	public T read() throws FileNotFoundException {
		reader = new JsonReader(new FileReader(filePath));
		Gson gson = builder.create();
		return gson.fromJson(reader, testDataClass);
	}

	public Map<String, T> readAllValues() throws FileNotFoundException {
		Map<String, T> result = new HashMap<>();
		reader = new JsonReader(new FileReader(filePath));
		Gson gson = builder.create();
		Map<String, LinkedTreeMap> rawTypeResult = gson.fromJson(reader, result.getClass());
		rawTypeResult.forEach((k,v) -> {
			JsonObject jsonObject = gson.toJsonTree(v).getAsJsonObject();
			result.put(k, gson.fromJson(jsonObject, testDataClass));
		});
		return result;
	}

	public T readByKey(String key) throws FileNotFoundException {
		return readAllValues().get(key);
	}

	public static <O> void readDataMembers(O objectWithTestData) throws FileNotFoundException, IllegalAccessException {
		Class classWithTestData = objectWithTestData.getClass();
		while (!classWithTestData.equals(Object.class)) {
			for (Field f : classWithTestData.getDeclaredFields()) {
				if (f.isAnnotationPresent(TestData.class)) {
					Class testDataClass = f.getType();
					TestData dataSource = f.getAnnotation(TestData.class);
					String filename = dataSource.file();
					String label = dataSource.key();

					TestDataReader reader = new TestDataReader(filename, testDataClass);
					Object value = label.isEmpty() ? reader.read() : reader.readByKey(label);
					f.setAccessible(true);
					f.set(objectWithTestData, value);
				}
			}
			classWithTestData = classWithTestData.getSuperclass();
		}
	}

}
