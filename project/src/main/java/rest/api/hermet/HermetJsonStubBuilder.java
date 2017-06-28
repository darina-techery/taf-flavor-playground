package rest.api.hermet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.worldventures.dreamtrips.api.api_common.converter.GsonProvider;
import utils.JsonUtils;
import utils.exceptions.FailedConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

public class HermetJsonStubBuilder extends HermetStubBuilder {

	public HermetJsonStubBuilder() {
		addResponseHeader("Content-Type", "application/json");
	}

	private JsonObject responseStub = new JsonObject();
	private Gson gson = new GsonProvider().provideGson();


	public void setResponse(ResponsePart part, String json) {
		JsonParser parser = new JsonParser();
		JsonElement content = parser.parse(json);
		addResponsePart(part, content);
	}

	public void addResponseHeader(String key, String value) {
		JsonElement responseHeaders = responseStub.get(ResponsePart.HEADERS.propertyName);
		if (responseHeaders == null) {
			responseHeaders = new JsonObject();
			addResponsePart(ResponsePart.HEADERS, responseHeaders);
		}
		responseHeaders.getAsJsonObject().addProperty(key, value);
	}

	/**
	 * This method should be used always, when object is NOT of generic type
	 * (e.g. String, not List&lt;String&gt;)
	 * For List&lt;String&gt; see {@link #setResponse(ResponsePart, Object, Type)}
	 *
	 * @param part is response part: header or body
	 * @param object is object with stub data
	 */
	public void setResponse(ResponsePart part, Object object) {
		JsonElement content = gson.toJsonTree(object);
		addResponsePart(part, content);
	}

	/**
	 * This method should be used when object is of generic type (i.e. List&lt;String&gt;)
	 * To obtain Type token of this object, use TypeToken class (see below).
	 * @param part
	 * @param object
	 * @param type The specific genericized type of src. You can obtain
	 * this type by using the {@link com.google.gson.reflect.TypeToken} class. For example,
	 * to get the type for {@code Collection<Foo>}, you should use:
	 * <pre>
	 * Type typeOfSrc = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
	 * </pre>
	 */
	public void setResponse(ResponsePart part, Object object, Type type) {
		JsonElement content = gson.toJsonTree(object, type);
		addResponsePart(part, content);
	}

	public void setResponse(ResponsePart part, JsonElement content) {
		addResponsePart(part, content);
	}

	public void setResponse(ResponsePart part, File dataFile) {
		try {
			JsonParser parser = new JsonParser();
			JsonElement content = parser.parse(new FileReader(dataFile));
			addResponsePart(part, content);
		} catch (FileNotFoundException e) {
			throw new FailedConfigurationException("File ["+dataFile+"] with response data was not found", e);
		}
	}

	private void addResponsePart(ResponsePart part, JsonElement json) {
		String propertyName = part.propertyName;
		if (json.isJsonArray() || json.getAsJsonObject().get(propertyName) == null) {
			responseStub.add(propertyName, json);
		} else {
			JsonElement content = json.getAsJsonObject().get(propertyName);
			responseStub.add(propertyName, content);
		}
	}

	public JsonObject build() {
		JsonObject stubJson = new JsonObject();
		stubJson.add("response", responseStub);
		stubJson.add("predicates", buildPredicates());
		return stubJson;
	}

	public JsonElement getExpectedResponseAsJson(ResponsePart part) {
		return responseStub.get(part.propertyName);
	}

	public <T> T getExpectedResponse(Class<T> responseClass) {
		return JsonUtils.toObject(responseStub.get(ResponsePart.BODY.propertyName),
				responseClass, JsonUtils.Converter.DREAM_TRIPS);
	}

	public <T> T getExpectedResponse(Type responseType) {
		return JsonUtils.toObject(responseStub.get(ResponsePart.BODY.propertyName),
				responseType, JsonUtils.Converter.DREAM_TRIPS);
	}

}
