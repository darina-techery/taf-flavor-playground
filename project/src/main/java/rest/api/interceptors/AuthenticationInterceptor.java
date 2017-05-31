package rest.api.interceptors;

import actions.rest.RestLoginActions;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rest.api.services.AuthAPI;
import rest.api.clients.DreamTripsAPIClient;
import user.UserSessionManager;

import java.io.IOException;

public class AuthenticationInterceptor implements Interceptor {
	private static final String LOGIN_SERVICE_PATH = "/api/sessions";
	private RestLoginActions restLoginActions;

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = chain.request().newBuilder();
		//add authentication token for all requests except login
		if (isTokenRequired(chain)) {
			if (UserSessionManager.getActiveUserToken() == null) {
				initRestLoginActions();
				restLoginActions.authenticateUser(UserSessionManager.getActiveUser());
			}
			String token = UserSessionManager.getActiveUserToken();
			builder.addHeader("Authorization", "Token token=" + token);
		}
		final Request request = builder.build();
		return chain.proceed(request);
	}

	private boolean isTokenRequired(Chain chain) {
		return !chain.request().url().encodedPath().equals(LOGIN_SERVICE_PATH);
	}

	private void initRestLoginActions() {
		if (restLoginActions == null) {
			AuthAPI authAPI = new DreamTripsAPIClient().create(AuthAPI.class);
			restLoginActions = new RestLoginActions(authAPI);
		}
	}
}
