package com.techery.dtat.ui.components;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

public class DiscardChangesAlert extends Alert {
	@AndroidFindBy(id = "confirm_button")
	@iOSFindBy(accessibility = "Yes")
	public MobileElement btnYes;

	@AndroidFindBy(id = "cancel_button")
	@iOSFindBy(accessibility = "No")
	public MobileElement btnNo;
}
