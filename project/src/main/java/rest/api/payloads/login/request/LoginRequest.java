package rest.api.payloads.login.request;

import user.UserCredentials;

public class LoginRequest {
	private String username;
	private String password;

	public LoginRequest(String login, String password) {
		this.username = login;
		this.password = password;
	}

	public LoginRequest(UserCredentials user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String login) {
		this.username = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}