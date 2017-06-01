package internal;

import base.BaseTest;
import com.google.gson.JsonObject;
import data.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.api.hermet.HermetSessionManager;
import rest.api.hermet.HermetStubBuilder;
import utils.runner.Assert;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class HermetClientTests extends BaseTest {

	private String hermetSessionId;
	private final String commonApiUrl = Configuration.getParameters().apiURL;

	@BeforeClass
	public void setupHermetSession(){
		hermetSessionId = HermetSessionManager.getId(commonApiUrl);
	}

	@Test
	public void hermetSessionIdRemainsConstantForTargetUrl() {
		String sessionId = HermetSessionManager.getId(commonApiUrl);
		Assert.assertThat("Hermet session id should remain constant between tests",
				sessionId, is(hermetSessionId));
	}

	@Test
	public void hermetSessionIdIsDifferentForAnotherTargetUrl() {
		String anotherTargetUrl = "www.sometesturl.com";
		String sessionId = HermetSessionManager.getId(anotherTargetUrl);
		Assert.assertThat("Hermet session id should remain constant between tests",
				sessionId, is(not(hermetSessionId)));
	}

	@Test
	public void testHermetStubBuilder_responseAsString() {
		String stubResponse = "{\"token\":\"test-token\",\"sso_token\":\"test-sso-token\"}";
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse(stubResponse);
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject stubContent = hermetStubBuilder.build();

		String expectedStubContent = "{\n" +
				"    \"response\": {\n" +
				"        \"body\": {\n" +
				"            \"token\":\"test-token\",\n" +
				"            \"sso_token\":\"test-sso-token\"\n" +
				"        }\n" +
				"     },\n" +
				"     \"predicates\": [\n" +
				"          {\n" +
				"                \"equals\": {\n" +
				"                    \"path\": \"/api/sessions\",\n" +
				"                    \"method\": \"POST\"\n" +
				"                }\n" +
				"          }\n" +
				"      }\n" +
				"   ]\n" +
				"}";
		expectedStubContent = expectedStubContent.replace("\\n", "")
				.replaceAll("\\s", "");
		Assert.assertThat("Hermet stub builder produces expected result with response as String",
				stubContent.toString(), is(expectedStubContent));
	}
}
