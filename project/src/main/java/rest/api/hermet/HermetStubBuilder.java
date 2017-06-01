package rest.api.hermet;

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


public class HermetStubBuilder {
	private JsonElement response;
	private ImposterBuilder imposterBuilder = new ImposterBuilder();
	private StubBuilder stubBuilder = imposterBuilder.stub();

	public PredicateTypeBuilder addPredicate() {
		return stubBuilder.predicate();
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

	public JsonObject build() {
		JsonObject stub = new JsonObject();
		JsonObject responseBody = buildResponse();
		JsonElement predicates = buildPredicates();
		stub.add("response", responseBody);
		stub.add("predicates", predicates);
		return stub;
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
		Imposter imposterWithPredicates = imposterBuilder.build();
		String predicatesList = imposterWithPredicates.getStub(0).getPredicates().toString();
		JsonElement result = new JsonParser().parse(predicatesList);
		return result;
	}

}
