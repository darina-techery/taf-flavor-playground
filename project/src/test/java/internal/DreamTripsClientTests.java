package internal;

import base.BaseTest;
import data.TestData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.api.clients.DreamTripsAPIClient;
import rest.api.payloads.login.request.LoginRequest;
import rest.api.payloads.login.response.LoginResponse;
import rest.api.payloads.login.response.UserProfile;
import rest.api.services.AuthAPI;
import rest.api.services.DreamTripsAPI;
import retrofit2.Response;
import user.UserCredentials;
import user.UserSessionManager;
import utils.runner.Assert;

import java.io.IOException;

import static org.hamcrest.core.Is.is;

public class DreamTripsClientTests extends BaseTest {

	DreamTripsAPIClient client;
	DreamTripsAPI apiService;

	@TestData(file = UserCredentials.DATA_FILE_NAME, key = "default_user")
	private UserCredentials defaultUser;

	@TestData(file = UserCredentials.DATA_FILE_NAME, key = "user_with_no_rds")
	private UserCredentials anotherUser;

	@BeforeClass
	public void setupClient() {
		client = new DreamTripsAPIClient();
		apiService = client.create(DreamTripsAPI.class);
	}

	@Test
	public void testDefaultUser() {
		UserCredentials userCredentials = UserSessionManager.getActiveUser();
		Assert.assertThat("Default user is active",
				userCredentials.username, is(defaultUser.username));
	}

	@Test
	public void testSetActiveUser() {
		UserSessionManager.setActiveUser(anotherUser);
		Assert.assertThat("Active user is changed",
				UserSessionManager.getActiveUser().username, is(anotherUser.username));
	}

	@Test
	public void testGetDefaultUserProfileRequestIsExecuted() throws IOException {
		Response<UserProfile> defaultUserProfileData = apiService.getUserProfile().execute();
		Assert.assertThat("Profile request is successful",
				defaultUserProfileData.isSuccessful());
	}

	@Test
	public void testGetDefaultUserProfileRequestContainsData() throws IOException {
		UserProfile profile = getActiveUserProfileFromService();
		String actualUsername = profile.getUsername();
		Assert.assertThat("Profile request returns valid username",
				actualUsername, is(UserSessionManager.getActiveUser().username));

	}

	@Test
	public void testGetAnotherUserProfileRequestContainsData() throws IOException {
		UserSessionManager.setActiveUser(anotherUser);
		UserProfile profile = getActiveUserProfileFromService();
		String actualUsername = profile.getUsername();
		Assert.assertThat("Profile request returns valid username for another user",
				actualUsername, is(anotherUser.username));
	}

	@Test
	public void testAuthenticationRequest() throws IOException {
		UserCredentials defaultUser = UserSessionManager.getActiveUser();
		AuthAPI authService = client.create(AuthAPI.class);
		LoginRequest request = new LoginRequest(defaultUser);
		Response<LoginResponse> defaultUserLoginResponse = authService.login(request).execute();
		Assert.assertThat("Login request is successful", defaultUserLoginResponse.isSuccessful());
	}

	@Test
	public void testAuthenticationTokenRemainsTheSameForOneUser() throws IOException {
		UserCredentials defaultUser = UserSessionManager.getActiveUser();
		AuthAPI authService = client.create(AuthAPI.class);
		LoginRequest request = new LoginRequest(defaultUser);
		Response<LoginResponse> defaultUserLoginResponse = authService.login(request).execute();
		String defaultUserToken = defaultUserLoginResponse.body().getToken();

		//send request requiring auth
		getActiveUserProfileFromService();
		String currentUserToken = UserSessionManager.getUserToken(defaultUser.username);
		Assert.assertThat("Authentication token remains the same between requests",
				currentUserToken, is(defaultUserToken));
	}

	private UserProfile getActiveUserProfileFromService() throws IOException {
		Response<UserProfile> defaultUserProfileData = apiService.getUserProfile().execute();
		return defaultUserProfileData.body();
	}
}
