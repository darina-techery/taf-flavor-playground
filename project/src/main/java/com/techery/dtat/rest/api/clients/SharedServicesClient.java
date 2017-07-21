package com.techery.dtat.rest.api.clients;

import com.google.gson.Gson;
import com.worldventures.dreamtrips.api.api_common.converter.GsonProvider;
import retrofit2.converter.gson.GsonConverterFactory;

public class SharedServicesClient extends BaseAPIClient {
	public static final String SHARED_SERVICES_URL = "https://sharedservices.qa-worldventures.biz";
	@Override
	protected void initClient() {
		GsonConverterFactory gsonConverterFactory = getConverterFactory();
		client = new RetrofitBuilder()
				.setBaseUrl(SHARED_SERVICES_URL)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.addGsonConverterFactory(gsonConverterFactory)
				.build();
	}

	@Override
	protected GsonConverterFactory getConverterFactory() {
		GsonProvider gsonProvider = new GsonProvider();
		Gson gson = gsonProvider.provideGson();
		return GsonConverterFactory.create(gson);
	}
}
