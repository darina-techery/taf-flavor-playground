package actions.rest;

import rest.ResponseLogger;
import rest.api.services.AuthAPI;
import rest.api.payloads.login.request.LoginRequest;
import rest.api.payloads.login.response.LoginResponse;
import retrofit2.Response;
import user.UserSessionManager;
import user.UserCredentials;
import utils.exceptions.FailedConfigurationException;
import utils.exceptions.FailedWaitAttemptException;
import utils.waiters.AnyWait;

import java.io.IOException;
import java.time.Duration;

public class RestLoginActions {

	private final AuthAPI authAPI;

	public RestLoginActions(AuthAPI authAPI) {
		this.authAPI = authAPI;
	}

//	public boolean isUserLoggedIn(UserCredentials userCredentials) {
//		return ActiveUserProvider.getApiSessionTokens().isSessionCreated(userCredentials.username);
//	}

	public void authenticateUser(UserCredentials userCredentials) {
		LoginRequest request = new LoginRequest(userCredentials);

		AnyWait<Void, Response<LoginResponse>> loginOperation = new AnyWait<>();
		loginOperation.duration(Duration.ofMinutes(1));
		loginOperation.calculate(() -> {
			try {
				return authAPI.login(request).execute();
			} catch (IOException e) {
				throw new FailedWaitAttemptException("Failed to execute login request", e);
			}
		});
		loginOperation.until(Response::isSuccessful);
		Response<LoginResponse> loginResponse = loginOperation.go();
		if (loginResponse == null || !loginResponse.isSuccessful()) {
			String message = new ResponseLogger(loginResponse, "Login via Rest API")
					.describeFailedResponse();
			throw new FailedConfigurationException(message);
		}
		UserSessionManager.addApiSession(loginResponse.body());
	}
}
