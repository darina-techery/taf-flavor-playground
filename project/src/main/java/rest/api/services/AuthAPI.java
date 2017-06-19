package rest.api.services;

import com.worldventures.dreamtrips.api.session.model.Session;
import rest.api.model.login.request.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
	@POST("/api/sessions")
	Call<Session> login(@Body LoginRequest login);
}
