package com.techery.dtat.rest.api.services;

import com.worldventures.dreamtrips.api.session.model.Session;
import com.techery.dtat.rest.api.model.login.request.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
	@POST("/api/sessions")
	Call<Session> login(@Body LoginRequest login);
}
