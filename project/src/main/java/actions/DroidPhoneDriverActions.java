package actions;

import data.Configuration;
import driver.capabilities.DroidPhoneCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.inject.Inject;

public class DroidPhoneDriverActions extends DriverActions {

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

	@Override
	public void reInitDriver() {
//		System.out.println("Reinit default Android driver");
		DesiredCapabilities capabilities = new DroidPhoneCapabilities().getCapabilities();
		reInitDriver(capabilities);
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
