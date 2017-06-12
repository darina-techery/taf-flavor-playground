package driver.capabilities;

import org.openqa.selenium.remote.DesiredCapabilities;

public class DroidPhoneCapabilities extends BaseAndroidCapabilities {

	@Override
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = super.getCapabilities();
		capabilities.setCapability("avd","nexus5");

		return capabilities;
	}
}
