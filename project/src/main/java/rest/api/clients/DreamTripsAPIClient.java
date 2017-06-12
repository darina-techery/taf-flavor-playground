package rest.api.clients;

import rest.api.hermet.HermetProxyDataFactory;
import rest.api.interceptors.AuthenticationInterceptor;

public class DreamTripsAPIClient extends BaseAPIClient {
	@Override
	protected void initClient() {
//		String targetUrl = new HermetProxyDataFactory().getCommonProxyData().getTargetUrl();
		String targetUrl = "http://" + new HermetProxyDataFactory().getCommonProxyData().getProxyHost();
		RetrofitBuilder builder = new RetrofitBuilder()
				.setBaseUrl(targetUrl)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.addHeaders(RetrofitBuilder.DT_HEADERS)
				.addGsonConverterFactory(new NullOrEmptyGsonConverterFactory())
				.addInterceptor(new AuthenticationInterceptor());
		client = builder.build();
	}
}

