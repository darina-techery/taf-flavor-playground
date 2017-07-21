package actions;

import io.appium.java_client.MobileElement;
import ui.screens.MyProfileScreen;
import utils.runner.Assert;
import utils.ui.Screenshot;
import utils.waiters.Waiter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;

public abstract class MyProfileActions extends BaseUiActions {

    MyProfileScreen myProfileScreen = new MyProfileScreen();

    private Waiter baseWait = new Waiter();

    @Override
    public void waitForScreen() {
        boolean isUserNameDisplayed = baseWait.waitDisplayed(myProfileScreen.txtUserName);
        Assert.assertThat("Username is present on the screen", isUserNameDisplayed);
    }

    public boolean waitUntilMyProfileScreenGone() {
        return new Waiter(Duration.ofMinutes(1)).waitAbsent(myProfileScreen.txtUserName);
    }

    public boolean isScreenActive() {
        return baseWait.isDisplayed(myProfileScreen.txtUserName);
    }

    public String getUsername(){
        return myProfileScreen.txtUserName.getText();
    }

    public String getSubscriptions(){
        return myProfileScreen.txtUserSubscriptions.getText();
    }

    public MobileElement getUserAvatar(){
        return myProfileScreen.picUserAvatar;
    }

    public BufferedImage getImageAvatar(MobileElement avatar) throws IOException {
        return Screenshot.makeScreenshotOfElement(avatar);
    }
}