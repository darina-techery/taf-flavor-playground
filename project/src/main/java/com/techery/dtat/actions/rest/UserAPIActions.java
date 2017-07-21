package com.techery.dtat.actions.rest;

import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import com.worldventures.dreamtrips.api.session.model.Session;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assume;
import com.techery.dtat.rest.api.clients.DreamTripsAPIClient;
import com.techery.dtat.rest.api.clients.UploadAPIClient;
import com.techery.dtat.rest.api.model.login.request.LoginRequest;
import com.techery.dtat.rest.api.services.AuthAPI;
import com.techery.dtat.rest.api.services.DreamTripsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.techery.dtat.user.UserCredentials;
import com.techery.dtat.user.UserSessionManager;
import com.techery.dtat.utils.FileUtils;
import com.techery.dtat.utils.exceptions.FailedConfigurationException;
import com.techery.dtat.utils.exceptions.FailedWaitAttemptException;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;

public class UserAPIActions {

	private final AuthAPI authAPI;
	private final DreamTripsAPI dreamTripsAPI;

	private Logger log = LogManager.getLogger();

	public UserAPIActions() {
		DreamTripsAPIClient client = new DreamTripsAPIClient();
		this.authAPI = client.create(AuthAPI.class);
		this.dreamTripsAPI = client.create(DreamTripsAPI.class);
	}

	public Response<Session> authenticateUser(UserCredentials userCredentials) {
		final LoginRequest request = new LoginRequest(userCredentials);
		Response<Session> loginResponse;
		try {
			loginResponse = authAPI.login(request).execute();
		} catch (IOException e) {
			throw new FailedWaitAttemptException("Failed to execute login request", e);
		}
		UserSessionManager.addApiSession(loginResponse.body());
		return loginResponse;
	}

	public void authenticateUserInBackground(UserCredentials userCredentials) {
		final LoginRequest request = new LoginRequest(userCredentials);
		authAPI.login(request).enqueue(new Callback<Session>() {
			@Override
			public void onResponse(Call<Session> call, Response<Session> response) {
				UserSessionManager.addApiSession(response.body());
			}
			@Override
			public void onFailure(Call<Session> call, Throwable t) {
				throw new FailedConfigurationException("Failed to execute login request", t);
			}
		});
	}

	public PrivateUserProfile getCurrentUserProfile() throws IOException {
		Response<PrivateUserProfile> response = dreamTripsAPI.getCurrentUserProfile().execute();
		Assume.assumeThat("User profile request should be successful", response.code(), is(200));
		return response.body();
	}

	public Integer getCurrentUserId() throws IOException {
		return getCurrentUserProfile().id();
	}

	public void uploadUserAvatar(File avatarFile) throws IOException {
		UploadAPIClient client = new UploadAPIClient();
		DreamTripsAPI uploadService = client.create(DreamTripsAPI.class);

		MediaType fileMediaType = FileUtils.getMediaType(avatarFile);
		RequestBody requestFile = RequestBody.create(fileMediaType, avatarFile);
		MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", avatarFile.getName(), requestFile);
		Call<ResponseBody> call = uploadService.uploadAvatar(body);
		call.execute();
	}

	public void acceptTermsAndConditionsInBackground() {
		dreamTripsAPI.acceptTermsAndConditions().enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				log.debug("Accepted Terms & Conditions for current user (if not accepted yet)");
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				throw new FailedConfigurationException("Failed to accept terms and conditions for current user: ", t);
			}
		});
	}
}
