package driver;

import com.sun.istack.internal.Nullable;
import dagger.DaggerConfigurationComponent;
import data.Configuration;
import driver.capabilities.BaseCapabilities;
import driver.capabilities.DroidPhoneCapabilities;
import driver.capabilities.IPhoneCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.DelayMeter;
import utils.LogProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Singleton
public class DriverProvider implements LogProvider {

	public static final String INIT_PAGES_OPERATION = "Pages instantiation";

	private final Logger log = getLogger();

	@Inject
	Configuration configuration;
	final private AppiumServiceProvider provider;
	private AppiumDriver<MobileElement> driver;
	private final Set<DriverListener> driverListeners = new HashSet<>();

	DriverProvider() {
		DaggerConfigurationComponent
				.create()
				.inject(this);
		this.provider = new AppiumServiceProvider(configuration);
	}

	public static AppiumDriver<MobileElement> get(){
		return DriverHolder.PROVIDER_INSTANCE.getActiveDriver();
	}

	public static void addDriverListener(DriverListener listener) {
		DriverHolder.PROVIDER_INSTANCE.driverListeners.add(listener);
	}

	public static void restart(DesiredCapabilities capabilities) {
		AppiumDriver<MobileElement> newDriver = DriverHolder.PROVIDER_INSTANCE.buildDriver(capabilities);
		DriverHolder.setDriver(newDriver);
	}

	public static void receiveTeardownNotification() {
		//Existing listeners will not be used past teardown point
		DriverHolder.PROVIDER_INSTANCE.driverListeners.clear();
	}

	private AppiumDriver<MobileElement> getActiveDriver() {
		if (driver == null) {
			//No specific Capabilities required at first start
			driver = buildDriver(null);
		}
		return driver;
	}

	private AppiumDriver<MobileElement> buildDriver(@Nullable DesiredCapabilities capabilities) {
		log.debug("Initializing driver: [START]");
		AppiumDriver<MobileElement> driver;
		final AppiumDriverLocalService service = provider.getService();
		switch (configuration.platformName) {
			case ANDROID_PHONE:
				DesiredCapabilities desiredCapabilities = capabilities == null ?
						new DroidPhoneCapabilities().getCapabilities() : capabilities;
				driver = new AndroidDriver<>(service, desiredCapabilities);
				driver.rotate(ScreenOrientation.PORTRAIT);
				break;
			case IPHONE:
				desiredCapabilities = capabilities == null ?
						new IPhoneCapabilities().getCapabilities() : capabilities;
				driver = new IOSDriver<>(service, desiredCapabilities);
				driver.rotate(ScreenOrientation.PORTRAIT);
				break;
			default:
				throw new RuntimeException("No driver properties specified for " + configuration.platformName);
		}
		driver.manage().timeouts().implicitlyWait(BaseCapabilities.DEFAULT_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS);
		log.debug("Initializing driver: [ END ]");
		return driver;
	}

	private void sendDriverRestartNotification(){
		log.debug("Notify all listeners about new driver: [START]");
		DelayMeter.startMeter(INIT_PAGES_OPERATION);
		driverListeners.forEach(driverListener -> driverListener.receiveDriverUpdate(driver));
		log.debug("Notify all listeners about new driver: [ END ]");
		DelayMeter.stopMeter(INIT_PAGES_OPERATION);
	}



	private static class DriverHolder {
		private static final DriverProvider PROVIDER_INSTANCE = new DriverProvider();

		private static void setDriver(AppiumDriver<MobileElement> driver){
			PROVIDER_INSTANCE.driver = driver;
			PROVIDER_INSTANCE.sendDriverRestartNotification();
		}
	}
}
