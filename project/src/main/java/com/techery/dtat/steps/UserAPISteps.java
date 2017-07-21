package com.techery.dtat.steps;

import com.techery.dtat.actions.rest.UserAPIActions;
import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import com.worldventures.dreamtrips.api.profile.model.UserProfile;
import ru.yandex.qatools.allure.annotations.Step;
import com.techery.dtat.utils.annotations.UseActions;

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

	@Step("Upload provided avatar for user if current one is different")
	public void uploadAvatarIfCurrentIsDifferent(UserProfile userProfile, File avatarFile) throws IOException {
		if (!userProfile.avatar().thumb().endsWith(avatarFile.getName())) {
			uploadAvatar(avatarFile);
		}
	}
}
