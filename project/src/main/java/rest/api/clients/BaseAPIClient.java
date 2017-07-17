package rest.api.clients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseAPIClient {
	public Retrofit client;
	protected abstract void initClient();
	protected abstract GsonConverterFactory getConverterFactory();
	public <T> T create(Class<T> apiService) {
		if (client == null) {
			initClient();
		}
		return client.create(apiService);
	}
}
