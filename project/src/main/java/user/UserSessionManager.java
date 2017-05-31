package user;

import data.TestDataReader;
import rest.api.payloads.login.response.LoginResponse;
import utils.exceptions.FailedConfigurationException;

import javax.inject.Singleton;
import java.io.FileNotFoundException;

@Singleton
public class UserSessionManager {
	private UserCredentials activeUser;
	private UserCredentials defaultUser;
	private SessionTokenHolder sessionTokenHolder;

	private UserSessionManager() {
		try {
			TestDataReader<UserCredentials> userDataReader = new TestDataReader<>(
					UserCredentials.DATA_FILE_NAME, UserCredentials.class);
			this.defaultUser = userDataReader.read();
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
		private static UserSessionManager HOLDER = new UserSessionManager();
	}

	public static UserCredentials getActiveUser() {
		return ActiveUserHolder.HOLDER.getUser();
	}

	public static void setActiveUser(UserCredentials user) {
		ActiveUserHolder.HOLDER.setUser(user);
	}

	public static void resetUserData(){
		ActiveUserHolder.HOLDER.activeUser = null;
		ActiveUserHolder.HOLDER.sessionTokenHolder.clearSessions();
	}

	public static String getActiveUserToken() {
		return getUserToken(getActiveUser().username);
	}

	public static String getUserToken(String username) {
		return ActiveUserHolder.HOLDER.sessionTokenHolder.getToken(username);
	}

	public static String getActiveUserSsoToken() {
		return getActiveUserSsoToken(getActiveUser().username);
	}

	public static String getActiveUserSsoToken(String username) {
		return ActiveUserHolder.HOLDER.sessionTokenHolder.getSsoToken(username);
	}

	public static void addApiSession(LoginResponse loginResponse) {
		ActiveUserHolder.HOLDER.sessionTokenHolder.addSession(loginResponse);
	}

}
