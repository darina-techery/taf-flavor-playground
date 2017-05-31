package rest.api.interceptors;

import actions.rest.AuthAPIActions;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import user.UserSessionManager;

import java.io.IOException;

public class AuthenticationInterceptor implements Interceptor {
	private static final String LOGIN_SERVICE_PATH = "/api/sessions";
	private AuthAPIActions restLoginActions;

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = chain.request().newBuilder();
		//add authentication token for all requests except login
		if (isTokenRequired(chain)) {
			String token = getToken();
			builder.addHeader("Authorization", "Token token=" + token);
		}
		final Request request = builder.build();
		return chain.proceed(request);
	}

	private boolean isTokenRequired(Chain chain) {
		return !chain.request().url().encodedPath().equals(LOGIN_SERVICE_PATH);
	}

	private String getToken() {
		if (UserSessionManager.getActiveUserToken() == null) {
			if (restLoginActions == null) {
				restLoginActions = new AuthAPIActions();
			}
			restLoginActions.authenticateUser(UserSessionManager.getActiveUser());
		}
		return UserSessionManager.getActiveUserToken();
	}

}
