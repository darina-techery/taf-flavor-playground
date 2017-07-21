package ui.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import ui.BaseUiModule;

public class MyProfileScreen extends BaseUiModule {

    @AndroidFindBy(id = "user_name")
    public MobileElement txtUserName;

    @AndroidFindBy(id = "user_status")
    public MobileElement txtUserSubscriptions;

    @AndroidFindBy(id = "user_photo")
    public MobileElement picUserAvatar;
}
