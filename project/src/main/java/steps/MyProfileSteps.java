package steps;

import actions.*;
import com.worldventures.dreamtrips.api.session.model.Subscription;
import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;
import utils.runner.Assert;
import utils.ui.Screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyProfileSteps {
    private final MyProfileActions myProfileActions;

    @UseActions
    public MyProfileSteps(MyProfileActions myProfileActions) {
        this.myProfileActions = myProfileActions;
    }

    @Step("Getting username")
    public String getUsername(){
        return myProfileActions.getUsername();
    }

    @Step("Getting user subscriptions")
    public String getSubscription(){
        return myProfileActions.getSubscriptions();
    }

    @Step("Getting user avatar")
    public MobileElement getUserAvatar(){
        return myProfileActions.getUserAvatar();
    }

    @Step("Verify user avatar")
    public void checkUserAvatar(MobileElement actualAvatar, File expectedAvatarFile) throws IOException {
        BufferedImage actualAvatarImage = myProfileActions.getImageAvatar(actualAvatar);
        BufferedImage expectedAvatarImage = ImageIO.read(expectedAvatarFile);
        if (!Screenshot.areImagesEqualByAverageColor(actualAvatarImage, expectedAvatarImage)) {
            File actualAvatarFile = new File("target/screenshots/actual_" + expectedAvatarFile.getName());
            ImageIO.write(actualAvatarImage, "jpeg", actualAvatarFile);
            Assert.assertThat(String.format("Avatar mismatch: expected image as in %s, but was as in %s",
                    expectedAvatarFile.getAbsolutePath(), actualAvatarFile.getAbsolutePath()),
                    false);
        }
    }
}
