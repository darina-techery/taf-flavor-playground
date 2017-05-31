package rest.api.clients;

import retrofit2.Retrofit;

public abstract class BaseAPIClient {
	protected Retrofit client;
	protected abstract void initClient();
	public <T> T create(Class<T> apiService) {
		if (client == null) {
			initClient();
		}
		return client.create(apiService);
	}
}
