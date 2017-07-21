package com.techery.dtat.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public interface DriverListener {
	void receiveDriverUpdate(AppiumDriver<MobileElement> driver);
	default void subscribeToDriverUpdates() {
		DriverProvider.addDriverListener(this);
	}
}
