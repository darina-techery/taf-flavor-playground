package rest.api.clients;

import data.Configuration;
import rest.api.interceptors.HermetStubInterceptor;

public class HermetAPIClient extends BaseAPIClient{
	@Override
	protected void initClient() {
		client = new RetrofitBuilder()
				.setBaseUrl(Configuration.getParameters().stubUrl)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.addInterceptor(new HermetStubInterceptor())
				.build();
	}

}
