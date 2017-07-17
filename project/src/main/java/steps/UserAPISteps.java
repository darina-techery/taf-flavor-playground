package steps;

import actions.rest.UserAPIActions;
import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

import java.io.File;
import java.io.IOException;

public class UserAPISteps {
	private final UserAPIActions userAPIActions;

	@UseActions
	public UserAPISteps(UserAPIActions userAPIActions) {
		this.userAPIActions = userAPIActions;
	}

	@Step("Get current user profile via API")
	public PrivateUserProfile getCurrentUserProfile() throws IOException {
		return userAPIActions.getCurrentUserProfile();
	}

	@Step("Upload new avatar for current user")
	public void uploadAvatar(File avatarFile) throws IOException {
		userAPIActions.uploadUserAvatar(avatarFile);
	}
}
