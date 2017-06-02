package internal;

import base.BaseTest;
import com.google.gson.JsonObject;
import data.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.api.clients.HermetAPIClient;
import rest.api.hermet.HermetSessionManager;
import rest.api.hermet.HermetStubBuilder;
import rest.api.services.HermetAPI;
import utils.runner.Assert;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class HermetClientTests extends BaseTest {

	private String hermetSessionId;
	private final String commonApiUrl = Configuration.getParameters().apiURL;
	private HermetAPIClient client;
	private HermetAPI apiService;

	@BeforeClass
	public void setupHermetSession(){
		hermetSessionId = HermetSessionManager.getId(commonApiUrl);
		client = new HermetAPIClient();
		apiService = client.create(HermetAPI.class);
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
		Assert.assertThat("Hermet session id should be different for different target URLs",
				sessionId, is(not(hermetSessionId)));
	}

	@Test
	public void testCreateAndDeleteStub() {
		String stubId;
//		try {
			HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
			hermetStubBuilder.setResponseAsFile("internal/sample_login_response.json");
			hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
			JsonObject actualStub = hermetStubBuilder.build();
//		finally

	}
}
