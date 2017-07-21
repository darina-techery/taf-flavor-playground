package com.techery.dtat.user;

import com.worldventures.dreamtrips.api.session.model.Session;
import com.techery.dtat.data.TestDataReader;
import com.techery.dtat.utils.exceptions.FailedConfigurationException;

import javax.inject.Singleton;
import java.io.FileNotFoundException;

@Singleton
public class UserSessionManager {
	private UserCredentials activeUser;
	private final UserCredentials defaultUser;
	private final SessionTokenHolder sessionTokenHolder;
	private boolean debugMode = false;
	private static final String MOCK_TOKEN = "test-token";

	private UserSessionManager() {
		try {
			TestDataReader<UserCredentials> userDataReader = new TestDataReader<>(
					UserCredentials.DATA_FILE_NAME, UserCredentials.class);
			this.defaultUser = userDataReader.readByKey("default_user");
		} catch (FileNotFoundException e) {
			throw new FailedConfigurationException(
					"Cannot read config file with user credentials: "+UserCredentials.DATA_FILE_NAME, e);
		}
		sessionTokenHolder = new SessionTokenHolder();
	}

	private void setUser(UserCredentials user) {
		activeUser = user;
	}

	private UserCredentials getUser() {
		return this.activeUser == null ? defaultUser : activeUser;
	}

	private static class ActiveUserHolder {
		private static final UserSessionManager INSTANCE = new UserSessionManager();
	}

	public static UserCredentials getActiveUser() {
		return ActiveUserHolder.INSTANCE.getUser();
	}

	public static void setActiveUser(UserCredentials user) {
		ActiveUserHolder.INSTANCE.setUser(user);
	}

	public static void resetUserData(){
		ActiveUserHolder.INSTANCE.activeUser = null;
		ActiveUserHolder.INSTANCE.sessionTokenHolder.clearSessions();
	}

	/**
	 * This method turns on "no authentication required" mode to speed up test execution
	 * if all requests are stubbed using Hermet.
	 *
	 * @param debugMode if true, authentication token will not be fetched for the user.
	 *                 Mock token will be used instead. Caution: non-stubbed DT services
	 *               will not work with fake token.
	 */
	public static void setMockAuthenticationMode(boolean debugMode) {
		ActiveUserHolder.INSTANCE.debugMode = debugMode;
	}

	public static String getActiveUserToken() {
		return getUserToken(getActiveUser().getUsername());
	}

	public static String getUserToken(String username) {
		return ActiveUserHolder.INSTANCE.debugMode
				? MOCK_TOKEN
				: ActiveUserHolder.INSTANCE.sessionTokenHolder.getToken(username);
	}

	public static String getActiveUserSsoToken() {
		return getActiveUserSsoToken(getActiveUser().getUsername());
	}

	public static String getActiveUserSsoToken(String username) {
		return ActiveUserHolder.INSTANCE.sessionTokenHolder.getSsoToken(username);
	}

	public static void addApiSession(Session loginResponse) {
		ActiveUserHolder.INSTANCE.sessionTokenHolder.addSession(loginResponse);
	}

}
