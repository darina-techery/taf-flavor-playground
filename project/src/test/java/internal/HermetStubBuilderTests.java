package internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.Test;
import rest.api.hermet.HermetStubBuilder;
import utils.runner.Assert;

import static org.hamcrest.core.Is.is;

public class HermetStubBuilderTests {
	private String expectedStubContentFromConfluencePage = "{\n" +
			"  \"response\": {\n" +
			"    \"body\": {\n" +
			"      \"token\":\"test-token\",\n" +
			"      \"sso_token\":\"test-sso-token\"\n" +
			"    }\n" +
			"  },\n" +
			"  \"predicates\": [\n" +
			"    {\n" +
			"      \"equals\": {\n" +
			"        \"path\": \"/api/sessions\",\n" +
			"        \"method\": \"POST\"\n" +
			"      }\n" +
			"    }\n" +
			"  ]\n" +
			"}".replace("\\n", "").replaceAll("\\s", "");

	@Test
	public void testBuildStub_withResponseAsString() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse("{\"token\":\"test-token\",\"sso_token\":\"test-sso-token\"}");
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();
		JsonElement expectedStub = new JsonParser().parse(expectedStubContentFromConfluencePage);

		Assert.assertThat("Hermet stub builder produces expected result with response as String",
				actualStub, is(expectedStub));
	}

	@Test
	public void testBuildStub_withResponseAsFile() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponseAsFile("internal/sample_login_response.json");
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();
		JsonElement expectedStub = new JsonParser().parse(expectedStubContentFromConfluencePage);

		Assert.assertThat("Hermet stub builder produces expected result with response as String",
				actualStub, is(expectedStub));

	}

	@Test
	public void testBuildStub_withResponseAsJsonObject() {
		JsonObject response = new JsonObject();
		response.addProperty("token", "test-token");
		response.addProperty("sso-token", "test-sso-token");

		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(response);
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();
		JsonElement expectedStub = new JsonParser().parse(expectedStubContentFromConfluencePage);

		Assert.assertThat("Hermet stub builder produces expected result with response as String",
				actualStub, is(expectedStub));

	}
}
