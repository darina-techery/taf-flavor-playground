package com.techery.dtat.driver.capabilities;

import com.techery.dtat.data.Configuration;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class BaseAndroidCapabilities extends BaseCapabilities {
	@Override
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.APP, Configuration.getParameters().fullAppPath);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
		capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 0);
		capabilities.setCapability(CapabilityType.VERSION, "6.0");
		capabilities.setCapability(MobileCapabilityType.ORIENTATION, ScreenOrientation.PORTRAIT);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
		capabilities.setCapability("defaultCapability", true);
		capabilities.setCapability("CapabilityName", this.getClass().getName());
		return capabilities;
	}
}
