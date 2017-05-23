package driver.capabilities;

import data.Configuration;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class BaseAndroidCapabilities extends BaseCapabilities {
	@Override
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.UDID, Configuration.getParameters().device);
		capabilities.setCapability(MobileCapabilityType.APP, Configuration.getParameters().fullAppPath);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
		capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 0);

		//TODO: remove if fails
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");

		capabilities.setCapability("defaultCapability", true);
		capabilities.setCapability("CapabilityName", this.getClass().getName());
		return capabilities;
	}
}
