package actions.rest;

import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import com.worldventures.dreamtrips.api.session.model.Session;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assume;
import rest.api.clients.DreamTripsAPIClient;
import rest.api.clients.UploadAPIClient;
import rest.api.model.login.request.LoginRequest;
import rest.api.services.AuthAPI;
import rest.api.services.DreamTripsAPI;
import rest.helpers.FailedResponseParser;
import retrofit2.Call;
import retrofit2.Response;
import user.UserCredentials;
import user.UserSessionManager;
import utils.FileUtils;
import utils.exceptions.FailedConfigurationException;
import utils.exceptions.FailedWaitAttemptException;
import utils.waiters.AnyWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

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

		final AnyWait<Void, Response<Session>> loginOperation = new AnyWait<>();
		loginOperation.duration(Duration.ofMinutes(1));
		loginOperation.calculate(() -> {
			try {
				return authAPI.login(request).execute();
			} catch (IOException e) {
				throw new FailedWaitAttemptException("Failed to execute login request", e);
			}
		});
		loginOperation.until(Response::isSuccessful);
		final Response<Session> loginResponse = loginOperation.go();
		if (loginResponse == null || !loginResponse.isSuccessful()) {
			String message = new FailedResponseParser()
					.describeFailedResponse(loginResponse, "Login via Rest API");
			throw new FailedConfigurationException(message);
		}
		UserSessionManager.addApiSession(loginResponse.body());
		return loginResponse;
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
}
