package rest.api.services;

import rest.api.payloads.hermet.HermetProxyData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HermetAPI {
	@POST("/api/services")
	Call<Void> setupProxy(@Body HermetProxyData proxyData);

	@POST("/api/services/{proxyId}/stubs")
	Call<Void> addStub(@Path("proxyId") String proxyId, @Body String json);
}
