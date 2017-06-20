package user;

import com.worldventures.dreamtrips.api.session.model.Session;

import java.util.HashSet;
import java.util.Set;

public class SessionTokenHolder {

	private final Set<SessionData> userSessions = new HashSet<>();

	public void addSession(Session loginResponse) {
		userSessions.removeIf(
				sessionData -> sessionData.username.equals(loginResponse.user().username()));
		userSessions.add(new SessionData(loginResponse));
	}

	public String getToken(String username) {
		SessionData data = getSessionByName(username);
		return data == null ? null : data.token;
	}

	public String getSsoToken(String username) {
		SessionData data = getSessionByName(username);
		return data == null ? null : data.ssoToken;
	}

	public boolean isSessionCreated(String username) {
		SessionData data = getSessionByName(username);
		return data != null;
	}

	public void clearSessions() {
		userSessions.clear();
	}

	private SessionData getSessionByName(String username) {
		return userSessions.stream()
				.filter(sessionData -> sessionData.username.equals(username))
				.findAny().orElse(null);
	}

	class SessionData {
		private String username;
		private String token;
		private String ssoToken;
		SessionData(Session loginResponse) {
			this.username = loginResponse.user().username();
			this.token = loginResponse.token();
			this.ssoToken = loginResponse.ssoToken();
		}
	}
}
