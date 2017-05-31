package rest.api.services;

import rest.api.payloads.login.request.LoginRequest;
import rest.api.payloads.login.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
	@POST("/api/sessions")
	Call<LoginResponse> login(@Body LoginRequest login);
}
