package actions;

import data.Configuration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

import javax.inject.Inject;

public class DroidPhoneDriverActions extends BaseDriverActions {

	@Inject
	Configuration configuration;

	@Override
	public void declineAlert(By by) {
		throw new NotImplementedException("Method not implemented");
	}

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
