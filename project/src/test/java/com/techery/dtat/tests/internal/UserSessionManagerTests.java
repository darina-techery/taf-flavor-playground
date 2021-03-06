package com.techery.dtat.tests.internal;

import com.techery.dtat.actions.rest.UserAPIActions;
import com.techery.dtat.tests.base.BaseTest;
import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import com.worldventures.dreamtrips.api.profile.model.UserProfile;
import com.worldventures.dreamtrips.api.session.model.Session;
import com.techery.dtat.data.TestData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.techery.dtat.rest.api.clients.DreamTripsAPIClient;
import com.techery.dtat.rest.api.model.login.request.LoginRequest;
import com.techery.dtat.rest.api.services.AuthAPI;
import com.techery.dtat.rest.api.services.DreamTripsAPI;
import retrofit2.Response;
import com.techery.dtat.user.UserCredentials;
import com.techery.dtat.user.UserSessionManager;
import com.techery.dtat.utils.runner.Assert;

import java.io.IOException;

import static org.hamcrest.core.Is.is;

public class UserSessionManagerTests extends BaseTest {

	DreamTripsAPIClient client;
	DreamTripsAPI apiService;

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
				userCredentials.getUsername(), is(defaultUser.getUsername()));
	}

	@Test
	public void testSetActiveUser() {
		UserSessionManager.setActiveUser(anotherUser);
		Assert.assertThat("Active user is changed",
				UserSessionManager.getActiveUser().getUsername(), is(anotherUser.getUsername()));
	}

	@Test
	public void testGetDefaultUserProfileRequestIsExecuted() throws IOException {
		Response<PrivateUserProfile> defaultUserProfileData = apiService.getCurrentUserProfile().execute();
		Assert.assertThat("Profile request is successful",
				defaultUserProfileData.isSuccessful());
	}

	@Test
	public void testGetDefaultUserProfileRequestContainsData() throws IOException {
		UserProfile profile = getActiveUserProfileFromService();
		String actualUsername = profile.username();
		Assert.assertThat("Profile request returns valid username",
				actualUsername, is(UserSessionManager.getActiveUser().getUsername()));

	}

	@Test
	public void testGetAnotherUserProfileRequestContainsData() throws IOException {
		UserSessionManager.setActiveUser(anotherUser);
		UserProfile profile = getActiveUserProfileFromService();
		String actualUsername = profile.username();
		Assert.assertThat("Profile request returns valid username for another user",
				actualUsername, is(anotherUser.getUsername()));
	}

	@Test
	public void testAuthenticationRequest() throws IOException {
		AuthAPI authService = client.create(AuthAPI.class);
		LoginRequest request = new LoginRequest(defaultUser);
		Response<Session> defaultUserLoginResponse = authService.login(request).execute();
		Assert.assertThat("Login request is successful", defaultUserLoginResponse.isSuccessful());
	}

	@Test
	public void testAuthenticationTokenRemainsTheSameForOneUser() throws IOException {
		UserAPIActions restLoginActions = new UserAPIActions();
		Session response = restLoginActions.authenticateUser(defaultUser).body();
		String defaultUserToken = response.token();

		//send request requiring auth
		getActiveUserProfileFromService();
		String currentUserToken = UserSessionManager.getUserToken(defaultUser.getUsername());
		Assert.assertThat("Authentication token remains the same between requests",
				currentUserToken, is(defaultUserToken));
	}

	private UserProfile getActiveUserProfileFromService() throws IOException {
		Response<PrivateUserProfile> defaultUserProfileData = apiService.getCurrentUserProfile().execute();
		return defaultUserProfileData.body();
	}
}
