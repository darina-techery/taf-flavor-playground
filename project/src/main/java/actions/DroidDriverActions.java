package actions;

import data.Configuration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.ScreenOrientation;

import javax.inject.Inject;

public class DroidDriverActions extends DriverActions {

	@Inject
	Configuration configuration;

	@Override
	public void resetApplication() {
		getDriver().resetApp();
		setDefaultScreenOrientation();
	}

	private void setDefaultScreenOrientation() {
		if(getDriver() != null) {
			AppiumDriver driver = getDriver();
			ScreenOrientation defaultOrientation = ScreenOrientation.valueOf((String) driver.getCapabilities()
					.getCapability(MobileCapabilityType.ORIENTATION));
			if (driver.getOrientation() != defaultOrientation) {
				rotateScreen(defaultOrientation);
			}
		}
	}
}
