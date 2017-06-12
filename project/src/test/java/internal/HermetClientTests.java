package internal;

import actions.rest.HermetProxyActions;
import base.BaseTest;
import com.google.gson.JsonObject;
import data.Configuration;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.api.clients.DreamTripsAPIClient;
import rest.api.clients.HermetAPIClient;
import rest.api.hermet.HermetServiceManager;
import rest.api.hermet.HermetStubBuilder;
import rest.api.payloads.hermet.HermetProxyData;
import rest.api.payloads.hermet.response.HermetStub;
import rest.api.payloads.internal.SampleResponse;
import rest.api.payloads.login.response.UserProfile;
import rest.api.services.DreamTripsAPI;
import rest.api.services.HermetAPI;
import rest.api.services.SampleAPI;
import rest.helpers.HermetResponseParser;
import retrofit2.Response;
import user.UserSessionManager;
import utils.runner.Assert;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class HermetClientTests extends BaseTest {

	private String mainServiceId;
	private final String commonApiUrl = Configuration.getParameters().apiURL;
	private final String mockTargetUrl = "www.sometesturl.com";
	private HermetAPI hermetApi;
	private HermetProxyActions actions;

	@BeforeClass
	public void setupHermetSession() throws IOException {
		hermetApi = new HermetAPIClient().create(HermetAPI.class);
		actions = new HermetProxyActions();

		actions.deleteAllStubsForService(commonApiUrl);
		actions.deleteAllStubsForService(mockTargetUrl);

		mainServiceId = HermetServiceManager.getServiceId(commonApiUrl);
	}

	@Test
	public void hermetSessionIdRemainsConstantForTargetUrl() {
		String sessionId = HermetServiceManager.getServiceId(commonApiUrl);
		Assert.assertThat("Hermet session id is not null", sessionId, notNullValue());
		Assert.assertThat("Hermet session id should remain constant between tests",
				sessionId, is(mainServiceId));
	}

	@Test
	public void hermetSessionIdIsDifferentForAnotherTargetUrl() {
		String anotherServiceId = HermetServiceManager.getServiceId(mockTargetUrl);
		Assert.assertThat("Hermet session id should be different for different target URLs",
				anotherServiceId, is(not(mainServiceId)));
	}

	@Test
	public void test_getActiveSessions() throws IOException {
		String sessionId = HermetServiceManager.getServiceId(commonApiUrl);
		Response<List<HermetProxyData>> response = hermetApi.getActiveServices().execute();
		List<HermetProxyData> activeSessions = response.body();

		Assert.assertThat("Active session list exists", activeSessions, notNullValue());
		Assert.assertThat("Active session list is not empty", 0,
				is(lessThan(activeSessions.size())));
		HermetProxyData serviceData = activeSessions.stream()
				.filter(service -> service.getId().equals(sessionId))
				.findAny().orElse(null);
		Assert.assertThat("Active session list contains created session id", serviceData,
				is(not(nullValue())));
	}

	@Test
	public void test_createStub_andVerifyResponse() throws IOException {
		String mockServiceId = HermetServiceManager.getServiceId(mockTargetUrl);
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponseFromFile("internal/sample_login_response.json");
		hermetStubBuilder.addPredicate()
				.equals().path("/api/sessions").method("POST").end()
				.equals().query("some param", "some value").build();
		JsonObject actualStub = hermetStubBuilder.build();

		Response<Void> responseFromAddingStub = hermetApi.addStub(mockServiceId, actualStub).execute();
		Assert.assertThat("Response has proper code", responseFromAddingStub.code(), equalTo(201));
		Assert.assertThat("Response has proper message",
				responseFromAddingStub.message(), equalTo("Created"));
		Assert.assertThat("Response has Location header",
				responseFromAddingStub.headers().get("Location"), notNullValue());
	}

	@Test
	public void test_createStub_andVerifyOnProxyHost() throws IOException {
		String mockServiceId = HermetServiceManager.getServiceId(mockTargetUrl);
		String testToken = "test-token";
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.setResponse("{'body':{'token':'"+testToken+"','sso_token':'test-sso-token'}}");
		hermetStubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = hermetStubBuilder.build();

		Response<Void> responseFromAddingStub = hermetApi.addStub(mockServiceId, actualStub).execute();
		final String stubId = HermetResponseParser.getStubId(responseFromAddingStub.raw());

		List<HermetStub> response = actions.getStubsForService(mockTargetUrl);
		Assert.assertThat("Stubs list for "+mockTargetUrl + " is not empty",
				response, notNullValue());

		HermetStub stubData = response.stream()
				.filter(stub->stub.getId().equals(stubId)).findAny().orElse(null);
		Assert.assertThat("Stub was created", stubData, notNullValue());
		Assert.assertThat("Stub response equals predefined one",
				stubData.getResponse().get("body").getAsJsonObject().get("token").getAsString(),
				is(testToken));
	}

	@Test
	public void test_proxySampleRequest() throws IOException {
		UserSessionManager.setMockAuthenticationMode(true);
		SampleAPI sampleAPI = new DreamTripsAPIClient().create(SampleAPI.class);
		SampleResponse responseBody = new SampleResponse("test_id");

		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.addPredicate()
				.equals()
				.path(SampleAPI.SAMPLE_REQUEST_PATH)
				.method("GET")
				.build();
		hermetStubBuilder.setResponse(responseBody, SampleResponse.class);
		JsonObject stub = hermetStubBuilder.build();
		actions.addStub(commonApiUrl, stub);

		Response<SampleResponse> response = sampleAPI.getSampleResponse().execute();
		LogManager.getLogger().debug(response.message());
	}

	@Test
	//TODO: enable in https://techery.atlassian.net/browse/DTAUT-456
	public void test_createStub_andVerifyInterceptedRequest() throws IOException {
		UserSessionManager.setMockAuthenticationMode(true);
		HermetStubBuilder hermetStubBuilder = new HermetStubBuilder();
		hermetStubBuilder.addPredicate().endsWith().path("/api/profile").method("GET").build();
		hermetStubBuilder.setResponseFromFile("internal/sample_user_profile_response.json");
		JsonObject stub = hermetStubBuilder.build();

		String expectedUsername = stub.get("response").getAsJsonObject()
				.get("body").getAsJsonObject()
				.get("username").getAsString();

		actions.addStub(commonApiUrl, stub);

		DreamTripsAPI dreamTripsAPI = new DreamTripsAPIClient().create(DreamTripsAPI.class);
		Response<UserProfile> response = dreamTripsAPI.getUserProfile().execute();
		Assert.assertThat("Response body is not null", response.body(), notNullValue());
		Assert.assertThat("Response body contains valid data: id", response.body().getId(), equalTo(1) );
		System.out.println("call placed");
	}

	@Test(enabled = false)
	//
	//ENABLE WITH CAUTION! ALL SESSIONS WILL BE DELETED!
	//
	public void deleteAllSessions() throws IOException {
		Response<List<HermetProxyData>> response = hermetApi.getActiveServices().execute();
		List<HermetProxyData> activeSessions = response.body();
		if (activeSessions != null) {
			for (HermetProxyData proxyData : activeSessions) {
				String serviceId = proxyData.getId();
				hermetApi.deleteService(serviceId).execute();
			}
		}
		response = hermetApi.getActiveServices().execute();
		Assert.assertThat("Sessions are deleted", response.body() == null || response.body().isEmpty());
	}

	@AfterClass(alwaysRun = true)
	public void deleteAllCreatedStubs() throws IOException {
		actions.deleteAllCreatedStubsForService(commonApiUrl);
		actions.deleteAllCreatedStubsForService(mockTargetUrl);
		UserSessionManager.setMockAuthenticationMode(false);
	}

}
