package rest.api.services;

import rest.api.payloads.login.response.UserProfile;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DreamTripsAPI {
	@GET("/api/profile")
	Call<UserProfile> getUserProfile();
}
