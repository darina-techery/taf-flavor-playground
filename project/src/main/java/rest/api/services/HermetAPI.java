package rest.api.services;

import com.google.gson.JsonObject;
import rest.api.payloads.hermet.HermetProxyData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HermetAPI {
	@POST("/api/services")
	Call<Void> setupProxy(@Body HermetProxyData proxyData);

	@POST("/api/services/{proxyId}/stubs")
	Call<Void> addStub(@Path("proxyId") String proxyId, @Body String json);

	@POST("/api/services/{proxyId}/stubs")
	Call<Void> addStub(@Path("proxyId") String proxyId, @Body JsonObject json);

	@DELETE("/api/services/{proxyId}/stubs/{stubId}")
	Call<Void> removeStub(@Path("proxyId") String proxyId, @Path("stubId") String stubId);
}
