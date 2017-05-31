package rest.api.hermet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import utils.exceptions.FailedConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HermetStubBuilder {
	private JsonElement response;
	private List<StubPredicate> predicates;

	public HermetStubBuilder() {
		predicates = new ArrayList<>();
	}

	public void addPredicate(StubPredicate predicate) {
		predicates.add(predicate);
	}

	public void setResponse(String json) {
		JsonParser parser = new JsonParser();
		response = parser.parse(json);
	}

	public void setResponse(JsonElement jsonElement) {
		response = jsonElement;
	}

	public void setResponse(File fileName) {
		try {
			JsonParser parser = new JsonParser();
			response = parser.parse(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			throw new FailedConfigurationException("File ["+fileName+"] with response data was not found", e);
		}
	}

	public String build() {
		JsonObject stub = new JsonObject();
		JsonObject responseBody = buildResponse();
		stub.add("response", responseBody);
	}

	private JsonObject buildResponse() {
		if (response == null || response.isJsonNull()) {
			throw new FailedConfigurationException("Cannot build stub with no response body");
		}
		JsonObject responseBody = new JsonObject();
		responseBody.add("body", response);
		return responseBody;
	}

	private JsonElement buildPredicates() {
//		if (predicates.isEmpty())
		return null;
	}
}

enum StubPredicate {
	EQUALS("equals"),
	CONTAINS("contains"),
	STARTS_WITH("startsWith"),
	ENDS_WITH("endsWith"),
	MATCHES("matches"),
	NOT("not"),
	OR("or"),
	AND("and");
	StubPredicate(String name) {
		this.name = name;
		nestedPredicates = new ArrayList<>();
		parameters = new HashMap<>();
	}
	private Map<String, String> parameters;
	private List<StubPredicate> nestedPredicates;
	private String name;
	void addNestedPredicate(StubPredicate p) {
		nestedPredicates.add(p);
	}
	void addParameter(String key, String value) {
		parameters.put(key, value);
	}
	void addParameters(Map<String, String> parameters) {
		this.parameters.putAll(parameters);
	}
}
