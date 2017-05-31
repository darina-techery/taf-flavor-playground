package user;

import data.TestDataReader;
import rest.api.payloads.login.response.LoginResponse;
import utils.exceptions.FailedConfigurationException;

import javax.inject.Singleton;
import java.io.FileNotFoundException;

@Singleton
public class UserSessionManager {
	private UserCredentials activeUser;
	private final UserCredentials defaultUser;
	private final SessionTokenHolder sessionTokenHolder;

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

	public static String getActiveUserToken() {
		return getUserToken(getActiveUser().username);
	}

	public static String getUserToken(String username) {
		return ActiveUserHolder.INSTANCE.sessionTokenHolder.getToken(username);
	}

	public static String getActiveUserSsoToken() {
		return getActiveUserSsoToken(getActiveUser().username);
	}

	public static String getActiveUserSsoToken(String username) {
		return ActiveUserHolder.INSTANCE.sessionTokenHolder.getSsoToken(username);
	}

	public static void addApiSession(LoginResponse loginResponse) {
		ActiveUserHolder.INSTANCE.sessionTokenHolder.addSession(loginResponse);
	}

}
