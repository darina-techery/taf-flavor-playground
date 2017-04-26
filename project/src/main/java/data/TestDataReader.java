package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import utils.exceptions.FailedConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class TestDataReader<T> {
	public static final String TEST_DATA_FOLDER = "fixtures";
	final Class<T> testDataClass;
	String filePath;
	JsonReader reader;
	Gson gson;

	public TestDataReader(String fileName, Class<T> testDataClass) throws FileNotFoundException {
		this.testDataClass = testDataClass;
		this.filePath = getDataFilePath(fileName);
		GsonBuilder builder = new GsonBuilder();
		if (testDataObjectContainsExposedFields(testDataClass)) {
			builder.excludeFieldsWithoutExposeAnnotation();
		}
		gson = builder.create();
	}

	private String getDataFilePath(String fileName) throws FileNotFoundException {
		ClassLoader classLoader = getClass().getClassLoader();
		String resourcePath = TEST_DATA_FOLDER + File.separator + fileName;
		URL resource = classLoader.getResource(resourcePath);
		if (resource == null) {
			throw new FileNotFoundException("Resource file was not found by path "+resourcePath);
		}
		return resource.getPath();

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
		return gson.fromJson(reader, testDataClass);
	}

	public Map<String, T> readAllValues() throws FileNotFoundException {
		Map<String, T> result = new HashMap<>();
		Map<String, LinkedTreeMap> rawTypeResult = getRawTypeValuesMap();
		rawTypeResult.forEach((k,v) -> {
			JsonObject jsonObject = gson.toJsonTree(v).getAsJsonObject();
			result.put(k, gson.fromJson(jsonObject, testDataClass));
		});
		return result;
	}

	public T readByKey(String key) throws FileNotFoundException {
		Map<String, LinkedTreeMap> rawTypeResult = getRawTypeValuesMap();
		JsonObject jsonObject = gson.toJsonTree( rawTypeResult.get(key) ).getAsJsonObject();
		return gson.fromJson(jsonObject, testDataClass);
	}

	public static <O> void readDataMembers(O objectWithTestData) {
		Class classWithTestData = objectWithTestData.getClass();
		while (!classWithTestData.equals(Object.class)) {
			for (Field f : classWithTestData.getDeclaredFields()) {
				if (f.isAnnotationPresent(TestData.class)) {
					Class testDataClass = f.getType();
					TestData dataSource = f.getAnnotation(TestData.class);
					String filename = dataSource.file();
					String label = dataSource.key();

					try {
						TestDataReader reader = new TestDataReader(filename, testDataClass);
						Object value = label.isEmpty() ? reader.read() : reader.readByKey(label);
						f.setAccessible(true);
						f.set(objectWithTestData, value);
					} catch (FileNotFoundException e) {
						throw new FailedConfigurationException(String.format(
								"Failed to read test data object %s in class %s: JSON file [%s] not found.",
								f.getName(), classWithTestData, filename));
					} catch (IllegalAccessException e) {
						throw new FailedConfigurationException(e, String.format(
								"Could not set value for field %s in class %s.",
								f.getName(), testDataClass));
					}
				}
			}
			classWithTestData = classWithTestData.getSuperclass();
		}
	}

	private Map<String, LinkedTreeMap> getRawTypeValuesMap() throws FileNotFoundException {
		reader = new JsonReader(new FileReader(filePath));
		Type resultType = new TypeToken<Map<String, LinkedTreeMap>>(){}.getType();
		return gson.fromJson(reader, resultType);
	}

}
