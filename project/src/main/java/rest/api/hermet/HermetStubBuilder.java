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
import java.net.URL;


public class HermetStubBuilder {
	private JsonElement response;
	private ImposterBuilder imposterBuilder = new ImposterBuilder();
	private StubBuilder stubBuilder = imposterBuilder.stub();

	public static final String HERMET_JSON_DATA_FOLDER = "hermet";

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

	public void setResponseAsFile(File dataFile) {
		try {
			JsonParser parser = new JsonParser();
			response = parser.parse(new FileReader(dataFile));
		} catch (FileNotFoundException e) {
			throw new FailedConfigurationException("File ["+dataFile+"] with response data was not found", e);
		}
	}

	public void setResponseAsFile(String resourceFilePath) {
		ClassLoader classLoader = getClass().getClassLoader();
		String resourcePath = HERMET_JSON_DATA_FOLDER + File.separator + resourceFilePath;
		URL resource = classLoader.getResource(resourcePath);
		if (resource == null) {
			throw new FailedConfigurationException(String.format(
					"Data file for Hermet was not found as resource at path %s", resourcePath));
		}
		String filePath = resource.getPath();
		File dataFile = new File(filePath);
		setResponseAsFile(dataFile);
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
		return new JsonParser().parse(predicatesList);
	}

}
