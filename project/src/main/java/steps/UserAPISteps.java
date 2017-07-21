package steps;

import actions.rest.UserAPIActions;
import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import com.worldventures.dreamtrips.api.profile.model.UserProfile;
import com.worldventures.dreamtrips.api.session.model.Subscription;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

	@Step("Upload provided avatar for user if current one is different")
	public void uploadAvatarIfCurrentIsDifferent(UserProfile userProfile, File avatarFile) throws IOException {
		if (!userProfile.avatar().thumb().endsWith(avatarFile.getName())) {
			uploadAvatar(avatarFile);
		}
	}

    @Step("Verify account type")
    public String getAccountType(List<Subscription> subscriptions) throws IOException {
        if (subscriptions.contains(Subscription.DTP)) {
            return "Platinum member";
        } else if (subscriptions.contains(Subscription.DTG)) {
            return "Gold member";
        } else if (subscriptions.contains(Subscription.DTS)) {
            return "Silver member";
        } else {
            return "";
        }
    }
}
