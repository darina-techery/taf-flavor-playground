package internal;

import actions.rest.SocialAPIActions;
import actions.rest.UserAPIActions;
import base.BaseTest;
import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import org.testng.annotations.Test;
import steps.DriverSteps;
import steps.LoginSteps;
import user.UserSessionManager;
import utils.exceptions.FailedTestException;
import utils.runner.Assert;
import utils.waiters.AnyWait;

import java.io.IOException;
import java.util.Objects;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class InternalAPITests extends BaseTest {
	private static final String HASH_TAG = "#AutoTestPost";

	@Test
	public void findAllDeleteByHashtag() throws IOException {
		new SocialAPIActions().deleteFeedItemsByHashTags(HASH_TAG);
	}

	@Test
	public void testThatUserCanAuthenticateInBackgroundAndThenLogin() {
		DriverSteps driverSteps = getStepsComponent().driverSteps();
		LoginSteps loginSteps = getStepsComponent().loginSteps();
		UserAPIActions apiActions = new UserAPIActions();
		try {
			apiActions.authenticateUserInBackground(defaultUser);
			//pause to let the token be retrieved before login in UI
			String token = new AnyWait<Void, String>()
					.calculate(UserSessionManager::getActiveUserToken)
					.until(Objects::nonNull)
					.go();
			Assert.assertThat("user token is ready", token, notNullValue());

			loginSteps.submitCredentials(defaultUser);
			PrivateUserProfile userProfile = apiActions.getCurrentUserProfile();
			Assert.assertThat("user token remains the same after we logged in to the application",
					UserSessionManager.getActiveUserToken(), is(token));
		} catch (IOException e) {
			throw new FailedTestException("Sending second API request after authentication failed", e);
		} finally {
			driverSteps.shutdownApplication();
		}
	}
}
