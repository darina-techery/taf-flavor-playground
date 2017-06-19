package rest.api.clients;

import com.google.gson.Gson;
import com.worldventures.dreamtrips.api.api_common.converter.GsonProvider;
import rest.api.hermet.HermetProxyDataFactory;
import rest.api.interceptors.AuthenticationInterceptor;
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
				.addInterceptor(new AuthenticationInterceptor());
		client = builder.build();
	}

	@Override
	protected GsonConverterFactory getConverterFactory() {
		GsonProvider gsonProvider = new GsonProvider();
		Gson gson = gsonProvider.provideGson();
		return GsonConverterFactory.create(gson);
	}
}

