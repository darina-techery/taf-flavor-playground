package com.techery.dtat.driver.capabilities;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IPadCapabilities extends BaseIOSCapabilities {
	@Override
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = super.getCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.2");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad Air 2");
		capabilities.setCapability(MobileCapabilityType.ORIENTATION, ScreenOrientation.LANDSCAPE);
		capabilities.setCapability(MobileCapabilityType.ROTATABLE, true);
		return capabilities;
	}
}
