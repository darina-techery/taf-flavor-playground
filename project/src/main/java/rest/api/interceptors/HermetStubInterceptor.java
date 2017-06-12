package rest.api.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import rest.api.hermet.HermetServiceManager;

import java.io.IOException;

public class HermetStubInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		final Request request = chain.request();
		final Response response = chain.proceed(request);
		if (isRequestCreatingStub(request)) {
			addStubToCreatedStubsList(response);

		}
		return response;
	}

	private boolean isRequestCreatingStub(Request request) {
		return request.method().equals("POST") && request.url().encodedPath().contains("stubs");
	}

	private void addStubToCreatedStubsList(Response response) throws IOException {
		LogManager.getLogger().debug("Message: " + response.message());
		if (response.body() != null) {
			LogManager.getLogger().debug("Body: " + response.body().string());
		}
		LogManager.getLogger().debug("Headers: " + response.headers().toString());
		HermetServiceManager.addStubFromResponse(response);
	}


}
