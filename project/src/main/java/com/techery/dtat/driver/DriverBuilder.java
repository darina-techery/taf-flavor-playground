package com.techery.dtat.driver;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.driver.capabilities.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.techery.dtat.utils.exceptions.NotImplementedException;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

class DriverBuilder {
	private final AppiumDriverLocalService service;
	DriverBuilder() {
		AppiumServiceProvider appiumServiceProvider = new AppiumServiceProvider();
		service = appiumServiceProvider.getService();
	}

	int getCurrentPort() {
		return service.getUrl().getPort();
	}

	AppiumDriver<MobileElement> createDriver(@Nullable DesiredCapabilities capabilities) {
		if (capabilities == null) {
			capabilities = getDefaultCapabilities();
		}
		AppiumDriver<MobileElement> driver = initDriver(service, capabilities);
		setupDriver(driver);
		return driver;
	}

	private DesiredCapabilities getDefaultCapabilities() {
		BaseCapabilities capabilities;
		switch (Configuration.getParameters().platform) {
			case ANDROID_PHONE:
				capabilities = new DroidPhoneCapabilities();
				break;
			case IPHONE:
				capabilities = new IPhoneCapabilities();
				break;
			case ANDROID_TABLET:
				capabilities = new DroidTabletCapabilities();
				break;
			case IPAD:
				capabilities = new IPadCapabilities();
				break;
			default:
				throw new NotImplementedException("No default capabilities created for this platform yet.");
		}
		return capabilities.getCapabilities();
	}

	private AppiumDriver<MobileElement> initDriver(AppiumDriverLocalService service,
	                                               DesiredCapabilities capabilities) {
		AppiumDriver<MobileElement> driver;
		if (Configuration.isAndroid()) {
			driver = new AndroidDriver<>(service, capabilities);
		} else {
			driver = new IOSDriver<>(service, capabilities);
		}
		return driver;
	}

	private void setupDriver(AppiumDriver<MobileElement> driver) {
		driver.manage().timeouts().implicitlyWait(BaseCapabilities.DEFAULT_TIME_OUT_IN_SECONDS,
				TimeUnit.SECONDS);
	}
}
