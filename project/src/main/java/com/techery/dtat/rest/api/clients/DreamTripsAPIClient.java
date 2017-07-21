package com.techery.dtat.rest.api.clients;

import com.google.gson.Gson;
import com.worldventures.dreamtrips.api.api_common.converter.GsonProvider;
import com.techery.dtat.rest.api.hermet.HermetProxyDataFactory;
import com.techery.dtat.rest.api.interceptors.AuthenticationInterceptor;
import com.techery.dtat.rest.api.interceptors.RetryInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class DreamTripsAPIClient extends BaseAPIClient {
	@Override
	protected void initClient() {
		String targetUrl = "http://" + new HermetProxyDataFactory().getCommonProxyData().getProxyHost();
		GsonConverterFactory gsonConverterFactory = getConverterFactory();
		RetrofitBuilder builder = new RetrofitBuilder()
				.setBaseUrl(targetUrl)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.addHeaders(RetrofitBuilder.DT_HEADERS)
				.addGsonConverterFactory(gsonConverterFactory)
				.addInterceptor(new AuthenticationInterceptor())
				.addInterceptor(new RetryInterceptor());
		client = builder.build();
	}

	@Override
	protected GsonConverterFactory getConverterFactory() {
		GsonProvider gsonProvider = new GsonProvider();
		Gson gson = gsonProvider.provideGson();
		return GsonConverterFactory.create(gson);
	}
}

