package driver.capabilities;

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class BaseIOSCapabilities extends BaseCapabilities {
	@Override
	DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.APP, configuration.appPath);
		capabilities.setCapability(MobileCapabilityType.UDID, getConfiguration().device);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 0);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
		capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, false);
		capabilities.setCapability(IOSMobileCapabilityType.WAIT_FOR_APP_SCRIPT, "true");
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
		capabilities.setCapability("showXcodeLog", true);
		capabilities.setCapability("defaultCapability", false);
		capabilities.setCapability("CapabilityName", this.getClass().getName());
		return capabilities;
	}
}
