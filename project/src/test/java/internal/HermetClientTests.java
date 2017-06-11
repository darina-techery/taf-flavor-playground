package internal;

import actions.rest.HermetProxyActions;
import base.BaseTest;
import com.google.gson.JsonObject;
import data.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.api.clients.DreamTripsAPIClient;
import rest.api.clients.HermetAPIClient;
import rest.api.hermet.HermetServiceManager;
import rest.api.hermet.HermetStubBuilder;
import rest.api.payloads.hermet.HermetProxyData;
import rest.api.payloads.hermet.response.HermetStub;
import rest.api.payloads.login.request.LoginRequest;
import rest.api.payloads.login.response.LoginResponse;
import rest.api.services.AuthAPI;
import rest.api.services.HermetAPI;
import rest.helpers.HermetResponseParser;
import retrofit2.Response;
import utils.runner.Assert;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class HermetClientTests extends BaseTest {

	private String mainServiceId;
	private final String commonApiUrl = Configuration.getParameters().apiURL;
	private HermetAPI hermetApi;
	private HermetProxyActions actions;

	@BeforeClass
	public void setupHermetSession() throws IOException {
		mainServiceId = HermetServiceManager.getServiceId(commonApiUrl);
		hermetApi = new HermetAPIClient().create(HermetAPI.class);
		actions = new HermetProxyActions();
		actions.deleteAllStubsForService(commonApiUrl);
	}

	@Test
	public void hermetSessionIdRemainsConstantForTargetUrl() {
		String sessionId = HermetServiceManager.getServiceId(commonApiUrl);
		Assert.assertThat("Hermet session id should remain constant between tests",
				sessionId, is(mainServiceId));
	}

	@Test
	public void hermetSessionIdIsDifferentForAnotherTargetUrl() {
		String anotherTargetUrl = "www.sometesturl.com";
		String anotherServiceId = HermetServiceManager.getServiceId(anotherTargetUrl);
		Assert.assertThat("Hermet session id should be different for different target URLs",
				anotherServiceId, is(not(mainServiceId)));
	}

	@Test
	public void test_getActiveSessions() throws IOException {
		String sessionId = HermetServiceManager.getServiceId(commonApiUrl);
		Response<List<HermetProxyData>> response = hermetApi.getActiveServices().execute();
		List<HermetProxyData> activeSessions = response.body();

		Assert.assertThat("Active session list exists", activeSessions, is(not(nullValue())));
		Assert.assertThat("Active session list is not empty", 0,
				is(lessThan(activeSessions.size())));
		HermetProxyData serviceData = activeSessions.stream()
				.filter(service -> service.getId().equals(sessionId))
				.findAny().orElse(null);
		Assert.assertThat("Active session list contains created session id", serviceData,
				is(not(nullValue())));
	}

	@Test
	public void test_createStub_andVerifyOnProxyHost() throws IOException {
		String anotherTargetUrl = "www.sometesturl.com";
		String anotherServiceId = HermetServiceManager.getServiceId(anotherTargetUrl);

		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponseAsFile("internal/sample_login_response.json");
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();

		Response<Void> responseFromAddingStub = hermetApi.addStub(anotherServiceId, actualStub).execute();
		final String stubId = HermetResponseParser.getStubId(responseFromAddingStub.raw());

		Response<List<HermetStub>> response = hermetApi.getStubsForService(anotherServiceId).execute();
		HermetStub stubData = response.body().stream()
				.filter(stub->stub.getId().equals(stubId)).findAny().orElse(null);
		Assert.assertThat("Stub was created", stubData, is(not(nullValue())));
		Assert.assertThat("Stub response equals predefined one",
				stubData.getResponse(),
				equalTo(actualStub.get("response")));
	}

	@Test
	//TODO: enable in https://techery.atlassian.net/browse/DTAUT-456
	public void test_createStub_andVerifyInterceptedRequest() throws IOException {
		String testToken = "test-token";
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse("{'body':{'token':'"+testToken+"','sso_token':'test-sso-token'}}");
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();

		Response<Void> responseFromAddingStub = hermetApi.addStub(mainServiceId, actualStub).execute();
		final String stubId = HermetResponseParser.getStubId(responseFromAddingStub.raw());

		AuthAPI authAPI = new DreamTripsAPIClient().create(AuthAPI.class);
		LoginRequest loginRequest = new LoginRequest("foo", "bar");
		Response<LoginResponse> response = authAPI.login(loginRequest).execute();
		Assert.assertThat("Response body is not empty", response.body(), is(not(nullValue())));
		Assert.assertThat("Response contains token value from stub", response.body().getToken(),
				equalTo(testToken));
	}

	@Test(enabled = false)
	//
	//ENABLE WITH CAUTION! ALL SESSIONS WILL BE DELETED!
	//
	public void deleteAllSessions() throws IOException {
		Response<List<HermetProxyData>> response = hermetApi.getActiveServices().execute();
		List<HermetProxyData> activeSessions = response.body();
		for (HermetProxyData proxyData : activeSessions) {
			String serviceId = proxyData.getId();
			hermetApi.deleteService(serviceId).execute();
		}
		response = hermetApi.getActiveServices().execute();
		Assert.assertThat("Sessions are deleted", response.body() == null || response.body().isEmpty());
	}

}
