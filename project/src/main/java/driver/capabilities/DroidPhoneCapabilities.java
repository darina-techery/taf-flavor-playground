package driver.capabilities;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DroidPhoneCapabilities extends BaseAndroidCapabilities {

	@Override
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = super.getCapabilities();
		capabilities.setCapability(CapabilityType.VERSION, "6.0");
		capabilities.setCapability(MobileCapabilityType.ORIENTATION, ScreenOrientation.PORTRAIT);
		return capabilities;
	}
}
