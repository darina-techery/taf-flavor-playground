import base.BaseTestForLoggedInUserWithoutRestart;
import com.worldventures.dreamtrips.api.profile.model.UserProfile;
import data.ui.MenuItem;
import io.appium.java_client.MobileElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.MyProfileSteps;
import steps.NavigationSteps;
import steps.UserAPISteps;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MyProfileTests extends BaseTestForLoggedInUserWithoutRestart {

    UserAPISteps userAPISteps = getStepsComponent().userAPISteps();
    NavigationSteps navigationSteps = getStepsComponent().navigationSteps();
    MyProfileSteps myProfileSteps = getStepsComponent().myProfileSteps();

    UserProfile userProfile;
    File expectedAvatarFile;

    @BeforeClass
    public void openMyProfile() throws IOException {
        navigationSteps.openSectionFromMenu(MenuItem.MY_PROFILE);
        userProfile = userAPISteps.getCurrentUserProfile();
        expectedAvatarFile = FileUtils.getResourceFile("images/blue.png");
    }

    @Test
    public void checkUsername() throws IOException {
        String currentUsername = myProfileSteps.getUsername();
        String userFirstAndLastNameFromAPI = userProfile.firstName() + " " + userProfile.lastName();
        assertThat("Username differs from API response", currentUsername, equalTo(userFirstAndLastNameFromAPI));
    }

    @Test
    public void checkSubscriptions() throws IOException {
        String currentSubscription = myProfileSteps.getSubscription();
        String userSubscription = userAPISteps.getAccountType(userProfile.subscriptions());
        assertThat("User status differs from API response", currentSubscription, equalTo(userSubscription));
    }

    @Test
    public void checkUserAvatar() throws IOException {
        MobileElement actualAvatar = myProfileSteps.getUserAvatar();
        myProfileSteps.checkUserAvatar(actualAvatar, expectedAvatarFile);
    }
}
