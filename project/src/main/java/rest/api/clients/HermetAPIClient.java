package rest.api.clients;

import data.Configuration;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class HermetAPIClient extends BaseAPIClient{
	@Override
	protected void initClient() {
		client = new RetrofitBuilder()
				.setBaseUrl(Configuration.getParameters().stubUrl)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.addInterceptor(new HermetStubInterceptor())
				.build();
	}

	class HermetStubInterceptor implements Interceptor {
		@Override
		public Response intercept(Chain chain) throws IOException {
			final Request request = chain.request();
			final Response response = chain.proceed(request);
			LogManager.getLogger().debug("Message: " + response.message());
			if (response.body() != null) {
				LogManager.getLogger().debug("Body: " + response.body().string());
			}
			LogManager.getLogger().debug("Headers: " + response.headers().toString());
			return response;
		}
	}
}
