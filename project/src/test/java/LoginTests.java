import base.BaseTestWithRestart;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Test;
import rest.api.clients.UploadAPIClient;
import rest.api.services.DreamTripsAPI;
import retrofit2.Call;
import retrofit2.Response;
import steps.DriverSteps;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.FileUtils;
import utils.log.LogProvider;

import java.io.*;
import java.net.URLConnection;

public final class LoginTests extends BaseTestWithRestart implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();
	private DriverSteps driverSteps = getStepsComponent().driverSteps();

	@Test
	public void loginToAppAsFirstTimeUser() {
		driverSteps.resetApplication();
		loginSteps.loginWithValidCredentials(defaultUser);
		navigationSteps.assertLandingPageLoaded();
	}

	@Test
	public void loginToApp() {
		loginSteps.loginIfRequired(defaultUser);
		navigationSteps.assertLandingPageLoaded();
	}

	@Test
	public void mytestavatar() throws IOException {
		File avatarFile = FileUtils.getResourceFile("images/blue.png");
		UploadAPIClient client = new UploadAPIClient();
		DreamTripsAPI uploadService = client.create(DreamTripsAPI.class);
		InputStream is = new BufferedInputStream(new FileInputStream(avatarFile));
		String contentType = URLConnection.guessContentTypeFromStream(is);
		is.close();
		MediaType fileMediaType = MediaType.parse(contentType);
		RequestBody requestFile = RequestBody.create(fileMediaType, avatarFile);
		// MultipartBody.Part is used to send also the actual file name
		MultipartBody.Part body =
//				MultipartBody.Part.createFormData("picture", avatarFile.getName(), requestFile);
				MultipartBody.Part.createFormData("avatar", avatarFile.getName(), requestFile);
		// add another part within the multipart request
		String descriptionString = "hello, this is description speaking";
		RequestBody description =
				RequestBody.create(
						okhttp3.MultipartBody.FORM, descriptionString);
		// finally, execute the request
		Call<ResponseBody> call = uploadService.uploadAvatar(body);
		Response<ResponseBody> response = call.execute();
		loginSteps.loginIfRequired(defaultUser);
		LogManager.getLogger().info("yoohoo");
//		call.enqueue(new Callback<ResponseBody>() {
//			@Override
//			public void onResponse(Call<ResponseBody> call,
//			                       Response<ResponseBody> response) {
//				LogManager.getLogger().info("Upload", "success");
//			}
//
//			@Override
//			public void onFailure(Call<ResponseBody> call, Throwable t) {
//				LogManager.getLogger().error("Upload error:", t.getMessage());
//			}
//		});
	}
}

