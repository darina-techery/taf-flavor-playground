package rest.api.services;

import com.google.gson.JsonObject;
import rest.api.payloads.hermet.HermetProxyData;
import rest.api.payloads.hermet.response.HermetStub;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface HermetAPI {

	String SERVICE_ID = "serviceId";
	String STUB_ID = "stubId";

	//Proxy services

	@POST("/api/services")
	Call<Void> addService(@Body HermetProxyData proxyData);

	@GET("/api/services")
	Call<List<HermetProxyData>> getActiveServices();

	@DELETE("/api/services/{"+ SERVICE_ID +"}")
	Call<Void> deleteService(@Path(SERVICE_ID) String serviceId);


	//Proxy stubs by service

	@POST("/api/services/{"+ SERVICE_ID +"}/stubs")
	Call<Void> addStub(@Path(SERVICE_ID) String serviceId, @Body JsonObject json);

	@GET("/api/services/{"+ SERVICE_ID +"}/stubs")
	Call<List<HermetStub>> getStubsForService(@Path(SERVICE_ID) String serviceId);

	@DELETE("/api/services/{"+ SERVICE_ID +"}/stubs/{"+STUB_ID+"}")
	Call<Void> deleteStub(@Path(SERVICE_ID) String serviceId, @Path(STUB_ID) String stubId);
}
