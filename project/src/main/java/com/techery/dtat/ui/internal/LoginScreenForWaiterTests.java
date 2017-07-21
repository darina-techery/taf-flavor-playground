package com.techery.dtat.ui.internal;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import com.techery.dtat.ui.BaseUiModule;

public class LoginScreenForWaiterTests extends BaseUiModule {
	@iOSFindBy(accessibility = "no_such_element_exists")
	@AndroidFindBy(id = "no_such_element_exists")
	public MobileElement invalidElement;

	public static final By VALID_LOGIN_LOCATOR_ANDROID = MobileBy.id("et_username");
	public static final By VALID_PASSWORD_LOCATOR_ANDROID = MobileBy.id("et_password");

	public static final By VALID_LOGIN_LOCATOR_IOS = MobileBy.AccessibilityId("USER_ID");
	public static final By VALID_PASSWORD_LOCATOR_IOS = MobileBy.AccessibilityId("PASSWORD");

	public static final By INVALID_LOCATOR = MobileBy.xpath("no_such_element_exists");

}
