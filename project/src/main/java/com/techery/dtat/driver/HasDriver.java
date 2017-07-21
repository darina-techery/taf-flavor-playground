package com.techery.dtat.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public interface HasDriver {
	default AppiumDriver<MobileElement> getDriver() {
		return DriverProvider.get();
	}
}
