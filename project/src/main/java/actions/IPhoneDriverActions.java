package actions;

import driver.capabilities.IPhoneCapabilities;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.CMDUtils;
import utils.LogProvider;

public class IPhoneDriverActions extends DriverActions implements LogProvider {

	private final Logger log = getLogger();

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
		log.debug("Reinstall application for iOS");
		String bundleId = CMDUtils.getDreamTripBundleId();
		String appPath = (String) getDriver().getCapabilities().getCapability(MobileCapabilityType.APP);
		CMDUtils.reInstallAndLaunchIOS(bundleId, appPath);
		log.debug("Reboot driver: [START]");
		reInitDriver();
		log.debug("Reboot driver: [ END ]");
//		IsUserLoggedIn.setFalse();
	}

	@Override
	public void reInitDriver() {
		DesiredCapabilities defaultIphoneCapabilities = new IPhoneCapabilities().getCapabilities();
		reInitDriver(defaultIphoneCapabilities);
	}
}
