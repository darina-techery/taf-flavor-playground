package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.worldventures.dreamtrips.api.api_common.converter.GsonProvider;

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

	public static JsonElement toJsonElement(Object object, Converter converter) {
		return converter.gson.toJsonTree(object);
	}

	public static JsonElement toJsonElementForGenerics(Object object, Type type, Converter converter) {
		return converter.gson.toJsonTree(object, type);
	}

	public static <T> T toObject(JsonElement element, Class<T> type, Converter converter) {
		return converter.gson.fromJson(element, type);
	}

	public static <T> T toObject(String jsonString, Class<T> type, Converter converter) {
		return converter.gson.fromJson(jsonString, type);
	}
}
