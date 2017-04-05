package actions;

import driver.capabilities.IPhoneCapabilities;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.CMDUtils;

public class IPhoneDriverActions extends DriverActions {

	public void declineAlert(By closeButtonLocator){
		try {
			waitFor(closeButtonLocator).isDisplayed();
			waitFor(closeButtonLocator).tap(1,5);
			waitFor(closeButtonLocator).tap(1,5);
			waitFor(closeButtonLocator).tap(1,5);
		} catch (Exception e) {/* Alerts already were handled */ }
	}

	@Override
	public void resetApplication() {
//		getDriver().resetApp();
		System.out.println("Reinstall application for iOS");
		String bundleId = CMDUtils.getDreamTripBundleId();
		String appPath = (String) getDriver().getCapabilities().getCapability(MobileCapabilityType.APP);
		CMDUtils.reInstallAndLaunchIOS(bundleId, appPath);
		System.out.println("Reboot driver");
		reInitDriver();
		System.out.println("Reboot driver: done.");
//		IsUserLoggedIn.setFalse();
	}

	@Override
	public void reInitDriver() {
		DesiredCapabilities defaultIphoneCapabilities = new IPhoneCapabilities().getCapabilities();
		reInitDriver(defaultIphoneCapabilities);
	}
}
