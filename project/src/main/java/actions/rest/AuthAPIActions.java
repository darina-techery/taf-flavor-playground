package actions.rest;

import com.worldventures.dreamtrips.api.session.model.Session;
import rest.helpers.FailedResponseParser;
import rest.api.clients.DreamTripsAPIClient;
import rest.api.services.AuthAPI;
import rest.api.model.login.request.LoginRequest;
import retrofit2.Response;
import user.UserSessionManager;
import user.UserCredentials;
import utils.exceptions.FailedConfigurationException;
import utils.exceptions.FailedWaitAttemptException;
import utils.waiters.AnyWait;

import java.io.IOException;
import java.time.Duration;

public class AuthAPIActions {

	private final AuthAPI authAPI;

	public AuthAPIActions() {
		this.authAPI = new DreamTripsAPIClient().create(AuthAPI.class);
	}

	public Response<Session> authenticateUser(UserCredentials userCredentials) {
		final LoginRequest request = new LoginRequest(userCredentials);

		final AnyWait<Void, Response<Session>> loginOperation = new AnyWait<>();
		loginOperation.duration(Duration.ofMinutes(1));
		loginOperation.calculate(() -> {
			try {
				return authAPI.login(request).execute();
			} catch (IOException e) {
				throw new FailedWaitAttemptException("Failed to execute login request", e);
			}
		});
		loginOperation.until(Response::isSuccessful);
		final Response<Session> loginResponse = loginOperation.go();
		if (loginResponse == null || !loginResponse.isSuccessful()) {
			String message = new FailedResponseParser()
					.describeFailedResponse(loginResponse, "Login via Rest API");
			throw new FailedConfigurationException(message);
		}
		UserSessionManager.addApiSession(loginResponse.body());
		return loginResponse;
	}
}
