package com.techery.dtat.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.techery.dtat.utils.DelayMeter;
import com.techery.dtat.utils.log.LogProvider;
import com.techery.dtat.utils.ui.Screenshot;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class DriverProvider implements LogProvider {

	public static final String INIT_PAGES_OPERATION = "Pages instantiation";

	private final Logger log = getLogger();
	private AppiumDriver<MobileElement> driver;

	private final Set<DriverListener> driverListeners = new HashSet<>();
	private final DriverBuilder driverBuilder;

	private DriverProvider() {
		this.driverBuilder = new DriverBuilder();
	}

	private static class DriverHolder {
		private static final DriverProvider INSTANCE = new DriverProvider();

		private static void setDriver(AppiumDriver<MobileElement> driver){
			INSTANCE.driver = driver;
			INSTANCE.sendDriverRestartNotification();
		}
	}

	public static AppiumDriver<MobileElement> get(){
		return DriverHolder.INSTANCE.getActiveDriver();
	}

	public static void restart() {
		restart(null);
	}

	public static void restart(DesiredCapabilities capabilities) {
		AppiumDriver<MobileElement> newDriver = DriverHolder.INSTANCE.driverBuilder.createDriver(capabilities);
		DriverHolder.setDriver(newDriver);
	}

	public static void stop() {
		if (DriverHolder.INSTANCE.driver != null) {
			DriverHolder.INSTANCE.driver.quit();
			DriverHolder.setDriver(null);
		}
	}

	public static void addDriverListener(DriverListener listener) {
		DriverHolder.INSTANCE.driverListeners.add(listener);
	}

	public static void removeDriverListeners() {
		//Existing listeners will not be used past teardown point
		DriverHolder.INSTANCE.driverListeners.clear();
	}

	public static int getAppiumPort() {
		return DriverHolder.INSTANCE.driverBuilder.getCurrentPort();
	}

	private AppiumDriver<MobileElement> getActiveDriver() {
		if (driver == null) {
			//No specific Capabilities required at first start
			driver = driverBuilder.createDriver(null);
			Screenshot.enableScreenshots();
		}
		return driver;
	}

	private void sendDriverRestartNotification(){
		log.debug("Notify all listeners about new driver: [START]");
		DelayMeter.startMeter(INIT_PAGES_OPERATION);
		driverListeners.forEach(driverListener -> driverListener.receiveDriverUpdate(driver));
		log.debug("Notify all listeners about new driver: [ END ]");
		DelayMeter.stopMeter(INIT_PAGES_OPERATION);
	}
}
