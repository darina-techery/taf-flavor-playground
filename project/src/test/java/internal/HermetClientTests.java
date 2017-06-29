package internal;

import actions.rest.HermetProxyActions;
import base.BaseTest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import com.worldventures.dreamtrips.api.session.model.Session;
import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import data.Configuration;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rest.api.clients.DreamTripsAPIClient;
import rest.api.clients.HermetAPIClient;
import rest.api.hermet.HermetJsonStubBuilder;
import rest.api.hermet.HermetServiceManager;
import rest.api.hermet.HermetStubBuilder;
import rest.api.model.hermet.HermetProxyData;
import rest.api.model.hermet.response.HermetStub;
import rest.api.model.login.request.LoginRequest;
import rest.api.services.AuthAPI;
import rest.api.services.DreamTripsAPI;
import rest.api.services.HermetAPI;
import retrofit2.Response;
import steps.HermetProxySteps;
import steps.TripsAPISteps;
import user.UserCredentials;
import user.UserSessionManager;
import utils.FileUtils;
import utils.log.CommonLogMessages;
import utils.runner.Assert;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class HermetClientTests extends BaseTest implements CommonLogMessages {

	private String mainServiceId;
	private final String commonApiUrl = Configuration.getParameters().apiURL;
	private HermetAPI hermetApi;
	private HermetProxyActions actions;
	private HermetJsonStubBuilder stubBuilder;
	private HermetProxySteps hermetSteps = new HermetProxySteps();

	@BeforeClass
	public void setupHermetSession() throws IOException {
		hermetApi = new HermetAPIClient().create(HermetAPI.class);
		actions = new HermetProxyActions();

		actions.deleteAllStubsForService(commonApiUrl);
		mainServiceId = HermetServiceManager.getServiceId(commonApiUrl);
	}

	@BeforeMethod
	public void initStubBuilder() {
		stubBuilder = new HermetJsonStubBuilder();
	}

	@Test
	public void test_getHermetServiceId_verifyItIsConstantForSameUrl() {
		String sessionId = HermetServiceManager.getServiceId(commonApiUrl);
		Assert.assertThat("Hermet session id is not null", sessionId, notNullValue());
		Assert.assertThat("Hermet session id should remain constant between tests",
				sessionId, is(mainServiceId));
	}

	@Test
	public void test_getActiveServices_verifyServiceId() throws IOException {
		String sessionId = HermetServiceManager.getServiceId(commonApiUrl);
		Response<List<HermetProxyData>> response = hermetApi.getActiveServices().execute();
		List<HermetProxyData> activeSessions = response.body();

		Assert.assertThat("Active service list exists", activeSessions, notNullValue());
		Assert.assertThat("Active service list is not empty", 0,
				is(lessThan(activeSessions.size())));
		HermetProxyData serviceData = activeSessions.stream()
				.filter(service -> service.getId().equals(sessionId))
				.findAny().orElse(null);
		Assert.assertThat("Active service list contains active service for common API", serviceData,
				notNullValue());
	}

	@Test
	public void test_sendAddStubRequest_verifyResponse() throws IOException {
		String serviceId = HermetServiceManager.getServiceId(commonApiUrl);
		stubBuilder.setResponse(HermetStubBuilder.ResponsePart.BODY,
				FileUtils.getResourceFile("hermet/internal/sample_login_response.json"));
		stubBuilder.addPredicate()
				.equals().path("/api/sessions").method("POST").end()
				.equals().query("some param", "some value").build();
		JsonObject actualStub = stubBuilder.build();

		Response<Void> responseFromAddingStub = hermetApi.addStub(serviceId, actualStub).execute();
		Assert.assertThat("Response has proper code", responseFromAddingStub.code(), equalTo(201));
		Assert.assertThat("Response has proper message",
				responseFromAddingStub.message(), equalTo("Created"));
		Assert.assertThat("Response has Location header",
				responseFromAddingStub.headers().get("Location"), notNullValue());
	}

	@Test
	public void test_stubLoginRequest_verifyResponseContainsData() throws IOException {
		stubBuilder.setResponse(HermetStubBuilder.ResponsePart.BODY,
				FileUtils.getResourceFile("hermet/login_response.json"));
		stubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = stubBuilder.build();
		String testToken = actualStub
				.get("response").getAsJsonObject()
				.get("body").getAsJsonObject()
				.get("token").getAsString();

		actions.addStub(commonApiUrl, actualStub);

		AuthAPI authAPI = new DreamTripsAPIClient().create(AuthAPI.class);
		LoginRequest loginRequest = new LoginRequest(new UserCredentials("user", "pass"));
		Response<Session> response = authAPI.login(loginRequest).execute();

		Assert.assertThat("Response contains token as in stub", response.body().token(), is(testToken));
	}

	@Test
	public void test_createStubInMockAuthenticationMode_verifyInterceptedRequest() throws IOException {
		UserSessionManager.setMockAuthenticationMode(true);
		stubBuilder.addPredicate().endsWith().path("/api/profile").method("GET").build();
		stubBuilder.setResponse(HermetStubBuilder.ResponsePart.BODY,
				FileUtils.getResourceFile("hermet/user_profile_response.json"));
		JsonObject stub = stubBuilder.build();

		String expectedUsername = stub.get("response").getAsJsonObject()
				.get("body").getAsJsonObject()
				.get("username").getAsString();

		actions.addStub(commonApiUrl, stub);

		DreamTripsAPI dreamTripsAPI = new DreamTripsAPIClient().create(DreamTripsAPI.class);
		Response<PrivateUserProfile> response = dreamTripsAPI.getCurrentUserProfile().execute();
		Assert.assertThat("Response body is not null", response.body(), notNullValue());
		Assert.assertThat("Response body contains valid data: username", response.body().username(),
				equalTo(expectedUsername) );
	}

	@Test
	public void test_stubTripDetails_verifyResponseCanBeParsedByModel() throws IOException {
		String tripUid = "platinum-recent";
		TripWithDetails expectedTrip = hermetSteps.createStubForPlatinumRecentTrip(tripUid);

		TripsAPISteps tripsAPISteps = new TripsAPISteps();
		TripWithDetails tripDetails = tripsAPISteps.getTripDetailsByItsUid(tripUid);
		Assert.assertThat("trips are equal", expectedTrip, equalTo(tripDetails));
	}

	@Test
	public void test_stubResponseWithHeader_verifyCreatedStubContainsHeader() throws IOException {
		String sampleUid = RandomStringUtils.randomAlphanumeric(5);
		stubBuilder.setResponse(HermetStubBuilder.ResponsePart.BODY,
				FileUtils.getResourceFile("hermet/login_response.json"));
		stubBuilder.addPredicate().equals().path("/api/"+sampleUid).method("POST").build();
		JsonObject actualStub = stubBuilder.build();

		actions.addStub(commonApiUrl, actualStub);

		List<HermetStub> stubs = actions.getStubsForMainService();
		for (HermetStub stub : stubs) {
			if(stub.getPredicates().toString().contains(sampleUid)) {
				JsonObject stubContent = stub.getResponse();
				JsonElement headers = stubContent.get("headers");
				Assert.assertThat("Created stub contains headers for response", headers, is(notNullValue()));

				JsonElement contentTypeHeader = headers.getAsJsonObject().get("Content-Type");
				Assert.assertThat("Created stub contains header Content-Type", contentTypeHeader, is(notNullValue()));

				String contentTypeHeaderValue = contentTypeHeader.getAsString();
				Assert.assertThat("Created stub contains header Content-Type with valid value",
						contentTypeHeaderValue, is("application/json"));
				return;
			}
		}
		throw new AssertionError("Stub with "+sampleUid+
						" in path was not found and could not be tested. Total number of stubs: "+stubs.size());
	}

	@Test
	public void test_stubResponseWithHeader_verifyResponseContainsHeader() throws IOException {
		stubBuilder.setResponse(HermetStubBuilder.ResponsePart.BODY,
				FileUtils.getResourceFile("hermet/login_response.json"));
		stubBuilder.addPredicate().equals().path("/api/sessions").method("POST").build();
		JsonObject actualStub = stubBuilder.build();

		actions.addStub(commonApiUrl, actualStub);

		AuthAPI authAPI = new DreamTripsAPIClient().create(AuthAPI.class);
		LoginRequest loginRequest = new LoginRequest(new UserCredentials("user", "pass"));
		Response<Session> response = authAPI.login(loginRequest).execute();

		Assert.assertThat("Response contains Content-Type header",
				response.headers().get("Content-Type"), is(notNullValue()));
		Assert.assertThat("Response contains Content-Type header",
				response.headers().get("Content-Type"), is("application/json"));
	}


	@Test(enabled = false)
	//
	//ENABLE WITH CAUTION! ALL SESSIONS WILL BE DELETED!
	//
	public void deleteAllSessions() throws IOException {
		actions.deleteAllServices();
		Response<List<HermetProxyData>> response = hermetApi.getActiveServices().execute();
		Assert.assertThat("Sessions are deleted", response.body() == null || response.body().isEmpty());
	}

	@AfterClass(alwaysRun = true)
	public void restoreDefaultAuthenticationMode() throws IOException {
		UserSessionManager.setMockAuthenticationMode(false);
	}

}
