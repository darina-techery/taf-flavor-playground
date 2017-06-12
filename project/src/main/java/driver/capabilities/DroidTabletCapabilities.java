package driver.capabilities;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DroidTabletCapabilities extends BaseAndroidCapabilities {

	@Override
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = super.getCapabilities();
		capabilities.setCapability("avd","nexus9");

		return capabilities;
	}
}
