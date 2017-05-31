package rest.api.clients;

import data.Configuration;
import rest.api.interceptors.AuthenticationInterceptor;

public class DreamTripsAPIClient extends BaseAPIClient {
	@Override
	protected void initClient() {
		RetrofitBuilder builder = new RetrofitBuilder()
				.setBaseUrl(Configuration.getParameters().apiURL)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.addHeaders(RetrofitBuilder.DT_HEADERS)
				.addInterceptor(new AuthenticationInterceptor());
		client = builder.build();
	}
}

