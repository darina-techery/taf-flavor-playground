package driver.capabilities;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;

public abstract class BaseAndroidCapabilities extends BaseCapabilities {
	@Override
	DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.UDID, getConfiguration().device);
		capabilities.setCapability(MobileCapabilityType.APP, configuration.appPath);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
		capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,
				Duration.ofSeconds(DEFAULT_TIME_OUT_IN_SECONDS).toMillis());
		capabilities.setCapability("defaultCapability", true);
		capabilities.setCapability("CapabilityName", this.getClass().getName());
		return capabilities;
	}
}
