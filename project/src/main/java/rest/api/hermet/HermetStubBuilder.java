package rest.api.hermet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mbtest.javabank.fluent.ImposterBuilder;
import org.mbtest.javabank.fluent.PredicateTypeBuilder;
import org.mbtest.javabank.fluent.StubBuilder;
import org.mbtest.javabank.http.imposters.Imposter;
import utils.exceptions.FailedConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;


public class HermetStubBuilder {
	public static final String HERMET_JSON_DATA_FOLDER = "hermet";
	public enum StubType {
		HEADERS("headers"),
		BODY("body");
		private final String propertyName;

		StubType(String propertyName) {
			this.propertyName = propertyName;
		}
	}
	private JsonObject responseStub = new JsonObject();
//	private Map<StubType, JsonElement> response = new HashMap<>();
	private ImposterBuilder imposterBuilder = new ImposterBuilder();

	private StubBuilder stubBuilder = imposterBuilder.stub();

	public PredicateTypeBuilder addPredicate() {
		return stubBuilder.predicate();
	}

	private void addResponsePart(StubType type, JsonElement part) {
		String propertyName = type.propertyName;
		if (part.isJsonArray() || part.getAsJsonObject().get(propertyName) == null) {
			responseStub.add(propertyName, part);
		} else {
			JsonElement content = part.getAsJsonObject().get(propertyName);
			responseStub.add(propertyName, content);
		}
	}

	public void setResponse(StubType type, String json) {
		JsonParser parser = new JsonParser();
		JsonElement content = parser.parse(json);
		addResponsePart(type, content);
	}

	public <T> void setResponse(StubType type, T object, Class<T> responseType) {
		Gson gson = new Gson();
		JsonElement content = gson.toJsonTree(object, responseType);
		addResponsePart(type, content);	}

	public void setResponse(StubType type, JsonElement content) {
		addResponsePart(type, content);
	}

	public void setResponseFromFile(StubType type, File dataFile) {
		try {
			JsonParser parser = new JsonParser();
			JsonElement content = parser.parse(new FileReader(dataFile)).getAsJsonObject();
			addResponsePart(type, content);
		} catch (FileNotFoundException e) {
			throw new FailedConfigurationException("File ["+dataFile+"] with response data was not found", e);
		}
	}

	public void setResponseFromFile(StubType type, String resourceFilePath) {
		ClassLoader classLoader = getClass().getClassLoader();
		String resourcePath = HERMET_JSON_DATA_FOLDER + File.separator + resourceFilePath;
		URL resource = classLoader.getResource(resourcePath);
		if (resource == null) {
			throw new FailedConfigurationException(String.format(
					"Data file for Hermet was not found as resource at path %s", resourcePath));
		}
		String filePath = resource.getPath();
		File dataFile = new File(filePath);
		setResponseFromFile(type, dataFile);
	}

	public JsonObject build() {
		JsonObject stubJson = new JsonObject();
		stubJson.add("response", responseStub);
		stubJson.add("predicates", buildPredicates());
//		if (response.containsKey(StubType.HEADERS)) {
//			JsonObject header = getResponseProperty("headers", response.get(StubType.HEADERS));
//			responseJson.
//		}
//		if (response.containsKey(StubType.HEADERS)) {
//			JsonElement headerStub = response.get(StubType.HEADERS);
//			if (headerStub.isJsonArray() || headerStub.getAsJsonObject().get("header") == null) {
//				JsonObject responseHeader = new JsonObject();
//				responseHeader.add("header", headerStub);
//				responseJson.add("response", responseHeader);
//			} else {
//				responseJson.add("response", headerStub);
//			}
//		}
//		if (response.containsKey(StubType.BODY)) {
//			JsonElement bodyStub = response.get(StubType.BODY);
//			if (bodyStub.isJsonArray() || bodyStub.getAsJsonObject().get("body") == null) {
//				JsonObject responseBody = new JsonObject();
//				responseBody.add("header", bodyStub);
//				responseJson.add("response", responseBody);
//			} else {
//				responseJson.add("response", bodyStub);
//			}
//		}

		return stubJson;
	}

//	private JsonObject getResponseProperty(String propertyName, JsonElement responsePart) {
//		if (responsePart.isJsonArray() || responsePart.getAsJsonObject().get(propertyName) == null) {
//			JsonObject wrappedResponsePart = new JsonObject();
//			wrappedResponsePart.add(propertyName, responsePart);
//			return wrappedResponsePart;
//		} else {
//			return responsePart.getAsJsonObject();
//		}
//
//	}

	private JsonElement buildPredicates() {
		Imposter imposterWithPredicates = imposterBuilder.build();
		String predicatesList = imposterWithPredicates.getStub(0).getPredicates().toString();
		return new JsonParser().parse(predicatesList);
	}

}
