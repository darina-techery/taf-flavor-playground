package driver.capabilities;

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IPadCapabilities extends BaseIOSCapabilities {
	@Override
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = super.getCapabilities();

//		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.2");
//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad Air 2");
//		capabilities.setCapability(MobileCapabilityType.ORIENTATION, ScreenOrientation.LANDSCAPE);

		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad Air 2");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.2");
		capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
		capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
		capabilities.setCapability(MobileCapabilityType.ROTATABLE, true);
		capabilities.setCapability(MobileCapabilityType.ORIENTATION, ScreenOrientation.LANDSCAPE);
		capabilities.setCapability(IOSMobileCapabilityType.WAIT_FOR_APP_SCRIPT, "true");
		capabilities.setCapability("automationName","XCUITest");
		capabilities.setCapability("CapabilityName", this.getClass().getName());
		capabilities.setCapability("defaultCapability", false);

		return capabilities;
	}
}
