package com.techery.dtat.ui.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import com.techery.dtat.ui.BaseUiModule;

public class AddTextScreen extends BaseUiModule {
	@AndroidFindBy(id = "action_done")
	@iOSFindBy(accessibility = "Done")
	public MobileElement btnDone;

	@AndroidFindBy(id = "description")
	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name='Done']/../../XCUIElementTypeTextView")
	public MobileElement fldText;
}
