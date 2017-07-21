package com.techery.dtat.rest.api.clients;

import com.google.gson.Gson;
import com.techery.dtat.rest.api.hermet.HermetProxyDataFactory;
import com.techery.dtat.rest.api.interceptors.HermetInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class HermetAPIClient extends BaseAPIClient {
	@Override
	protected void initClient() {
		GsonConverterFactory defaultConverterFactory = getConverterFactory();
		String targetUrl = "http://" + new HermetProxyDataFactory().getCommonProxyData().getStubsHost();
		client = new RetrofitBuilder()
				.setBaseUrl(targetUrl)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.addGsonConverterFactory(defaultConverterFactory)
				.addInterceptor(new HermetInterceptor())
				.build();
	}

	@Override
	protected GsonConverterFactory getConverterFactory() {
		final Gson gson = new Gson();
		return GsonConverterFactory.create(gson);
	}
}
