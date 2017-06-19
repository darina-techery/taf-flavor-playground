package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.worldventures.dreamtrips.api.api_common.converter.GsonProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;

public final class JsonUtils {

	public enum Converter {
		DREAM_TRIPS(new GsonProvider().provideGson()),
		DEFAULT(new Gson());
		final Gson gson;
		Converter(Gson gson) {
			this.gson = gson;
		}
	}

	private JsonUtils(){}

	public static String toString(Object o, Converter converter) {
		return converter.gson.toJson(o);
	}

	public static String toString(JsonElement element) {
		return element.getAsString();
	}

	public static JsonElement toJsonElement(String s) {
		JsonParser parser = new JsonParser();
		return parser.parse(s);
	}

	private static Reader getFileReader(File file) {
		try {
			return new FileReader(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not read JSON from file ["+file+"]", e);
		}
	}

	public static JsonElement toJsonElement(File file) {
		JsonElement element;
		JsonParser parser = new JsonParser();
		element = parser.parse(getFileReader(file));
		return element;
	}

	public static JsonElement toJsonElement(Object object, Converter converter) {
		return converter.gson.toJsonTree(object);
	}

	public static JsonElement toJsonElementForGenerics(Object object, Type type, Converter converter) {
		return converter.gson.toJsonTree(object, type);
	}


	/**
	 * Use for non-generic types (e.g. PrivateUserProfile)
	 * @param element
	 * @param nonGenericClass
	 * @param converter
	 * @param <T>
	 * @return
	 */
	public static <T> T toObject(JsonElement element, Class<T> nonGenericClass, Converter converter) {
		return converter.gson.fromJson(element, nonGenericClass);
	}


	/**
	 * Use for generics (e.g. List&lt;TripData&gt;)
	 * @param jsonString
	 * @param genericType
	 * @param converter
	 * @param <T>
	 * @return
	 */
	public static <T> T toObject(String jsonString, Type genericType, Converter converter) {
		return converter.gson.fromJson(jsonString, genericType);
	}

	public static <T> T toObject(File file, Class<T> nonGenericClass, Converter converter) {
		return converter.gson.fromJson(getFileReader(file), nonGenericClass);
	}

	public static <T> T toObject(File file, Type genericType, Converter converter) {
		return converter.gson.fromJson(getFileReader(file), genericType);
	}

}
