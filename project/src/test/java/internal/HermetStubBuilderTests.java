package internal;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import org.testng.annotations.Test;
import rest.api.hermet.HermetStubBuilder;
import utils.runner.Assert;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static rest.api.hermet.HermetStubBuilder.ResponsePart;

public class HermetStubBuilderTests {
	private String expectedStubContentWithHeadersAndBody = "{\n" +
			"  \"response\": {\n" +
			"    \"headers\": {\n" +
			"      \"token\":\"test-token\"\n" +
			"    },\n" +
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

	private String expectedStubContentWithHeader = "{\n" +
			"  \"response\": {\n" +
			"    \"headers\": {\n" +
			"      \"token\":\"test-token\"\n" +
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
	private JsonObject expectedStubWithBody = new JsonParser()
			.parse(expectedStubContentFromConfluencePage).getAsJsonObject();
	private JsonObject expectedStubWithHeader = new JsonParser()
			.parse(expectedStubContentWithHeader).getAsJsonObject();
	private JsonObject expectedStubWithHeaderAndBody = new JsonParser()
			.parse(expectedStubContentWithHeadersAndBody).getAsJsonObject();

	private void addDefaultPredicate(HermetStubBuilder builder) {
		builder.addPredicate().equals().path("/api/sessions").method("POST").build();
	}

	private void compareStubs(String message, JsonObject actualStub, JsonObject expectedStub) {
		Assert.assertThat(message, new Gson().toJson(actualStub).trim(), equalTo(new Gson().toJson(expectedStub).trim()));
	}

	private void compareDifferentStubs(String message, JsonObject actualStub, JsonObject expectedStub) {
		Assert.assertThat(message, new Gson().toJson(actualStub).trim(), not(equalTo(new Gson().toJson(expectedStub).trim())));
	}

	@Test
	public void testBuildStub_withResponseAsObject_Success() {
		FakeTokenResponse fakeTokenResponse = new FakeTokenResponse(
				"test-token", "test-sso-token");
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(ResponsePart.BODY, fakeTokenResponse);
		addDefaultPredicate(hermetStubBuilder);
		JsonObject actualStub = hermetStubBuilder.build();
		compareStubs("Valid stub built from Object", actualStub, expectedStubWithBody);
	}

	@Test
	public void testBuildStub_withResponseAsObject_Failure() {
		FakeTokenResponse fakeTokenResponse = new FakeTokenResponse(
				"invalid", "test-sso-token");
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(ResponsePart.BODY, fakeTokenResponse);
		addDefaultPredicate(hermetStubBuilder);
		JsonObject actualStub = hermetStubBuilder.build();
		compareDifferentStubs("Stubs are different", actualStub, expectedStubWithBody);
	}

	@Test
	public void testBuildStub_withHeaderResponseAsString_Success() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(ResponsePart.HEADERS, "{\"token\":\"test-token\"}");
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();
		compareStubs("Valid stub built from String", actualStub, expectedStubWithHeader);
	}

	@Test
	public void testBuildStub_withHeaderResponseAsString_Failure() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(ResponsePart.HEADERS, "{\"token\":\"foo\"}");
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();
		compareDifferentStubs("Stubs with headers are different", actualStub, expectedStubWithHeader);
	}

	@Test
	public void testBuildStub_withResponseAsString_Success() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(ResponsePart.BODY, "{\"body\":{\"token\":\"test-token\",\"sso_token\":\"test-sso-token\"}}");
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();
		compareStubs("Valid stub built from String", actualStub, expectedStubWithBody);
	}

	@Test
	public void testBuildStub_withResponseAsString_Failure() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(ResponsePart.BODY, "{\"body\":{\"sso_token\":\"test-sso-token\"}}");
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();
		compareDifferentStubs("Stubs are different", actualStub, expectedStubWithBody);
	}

	@Test
	public void testBuildStub_withResponseAsFile_Success() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponseFromFile(ResponsePart.BODY, "internal/sample_login_response.json");
		addDefaultPredicate(hermetStubBuilder);
		JsonObject actualStub = hermetStubBuilder.build();
		compareStubs("Valid stub built from file", actualStub, expectedStubWithBody);
	}

	@Test
	public void testBuildStub_withResponseAsFile_Failure() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponseFromFile(ResponsePart.BODY, "internal/sample_login_response_invalid.json");
		addDefaultPredicate(hermetStubBuilder);
		JsonObject actualStub = hermetStubBuilder.build();
		compareDifferentStubs("Stubs are different", actualStub, expectedStubWithBody);

	}

	@Test
	public void testBuildStub_withResponseAsJsonObject_Success() {
		JsonObject response = new JsonObject();
		JsonObject body = new JsonObject();
		body.addProperty("token", "test-token");
		body.addProperty("sso_token", "test-sso-token");
		response.add("body", body);

		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(ResponsePart.BODY, response);
		addDefaultPredicate(hermetStubBuilder);
		JsonObject actualStub = hermetStubBuilder.build();

		compareStubs("Valid stub built from JsonObject", actualStub, expectedStubWithBody);
	}

	@Test
	public void testBuildStub_withResponseAsJsonObject_Failure() {
		JsonObject response = new JsonObject();
		JsonObject body = new JsonObject();
		body.addProperty("sso_token", "test-sso-token");
		response.add("body", body);

		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(ResponsePart.BODY, response);
		addDefaultPredicate(hermetStubBuilder);
		JsonObject actualStub = hermetStubBuilder.build();

		compareDifferentStubs("Stubs are different", actualStub, expectedStubWithBody);
	}

	@Test
	public void testBuildStub_WithHeaderAndBody_Success() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();

		JsonObject headers = new JsonObject();
		headers.addProperty("token", "test-token");
		hermetStubBuilder.setResponse(ResponsePart.HEADERS, headers);

		JsonObject body = new JsonObject();
		body.addProperty("token", "test-token");
		body.addProperty("sso_token", "test-sso-token");
		hermetStubBuilder.setResponse(ResponsePart.BODY, body);

		addDefaultPredicate(hermetStubBuilder);
		JsonObject actualStub = hermetStubBuilder.build();
		compareStubs("Stubs with headers and body are equal", actualStub, expectedStubWithHeaderAndBody);
	}

	@Test
	public void testBuildStub_WithHeaderAndBody_Failure() {
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();

		JsonObject headers = new JsonObject();
		headers.addProperty("token", "foo");
		hermetStubBuilder.setResponse(ResponsePart.HEADERS, headers);

		JsonObject body = new JsonObject();
		body.addProperty("token", "test-token");
		body.addProperty("sso_token", "test-sso-token");
		hermetStubBuilder.setResponse(ResponsePart.BODY, body);

		addDefaultPredicate(hermetStubBuilder);
		JsonObject actualStub = hermetStubBuilder.build();
		compareDifferentStubs("Stubs with headers and body are different",
				actualStub, expectedStubWithHeaderAndBody);
	}

	class FakeTokenResponse {
		FakeTokenResponse(String token, String sso_token) {
			this.token = token;
			this.sso_token = sso_token;
		}

		@SerializedName("token")
		private String token;

		@SerializedName("sso_token")
		private String sso_token;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getSso_token() {
			return sso_token;
		}

		public void setSso_token(String sso_token) {
			this.sso_token = sso_token;
		}
	}
}
