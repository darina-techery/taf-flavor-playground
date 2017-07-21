package com.techery.dtat.driver.capabilities;

import org.openqa.selenium.remote.DesiredCapabilities;

public class DroidTabletCapabilities extends BaseAndroidCapabilities {

	@Override
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = super.getCapabilities();
		capabilities.setCapability("avd","nexus9");

		return capabilities;
	}
}
