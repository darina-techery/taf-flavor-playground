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
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Singleton
public class DriverProvider {

	@Inject
	Configuration configuration;
	final private AppiumServiceProvider provider;
	private AppiumDriver<MobileElement> driver;
	private final Set<DriverListener> driverListeners = new HashSet<>();

	DriverProvider() {
		DaggerConfigurationComponent
				.create()
				.inject(this);
		System.out.println(this.toString());
		this.provider = new AppiumServiceProvider(configuration);
	}

	private AppiumDriver<MobileElement> getDriver() {
		if (driver == null) {
			System.out.println("Init a driver");
			//No specific Capabilities required at first start
			driver = buildDriver(null);
		}
		return driver;
	}

	private AppiumDriver<MobileElement> buildDriver(@Nullable DesiredCapabilities capabilities) {
		AppiumDriver<MobileElement> driver;
		final AppiumDriverLocalService service = provider.getService();
		System.out.println("Service: " + service);
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
		System.out.println("Created driver instance: "+ driver.toString());
		return driver;
	}

	private void sendDriverRestartNotification(){
		driverListeners.forEach(driverListener -> driverListener.receiveDriverUpdate(driver));
	}

	public static AppiumDriver<MobileElement> get(){
		AppiumDriver<MobileElement> driver = DriverHolder.PROVIDER_INSTANCE.getDriver();
		System.out.println("Requested driver instance: " + driver.toString());
		return driver;
	}

	public static void addDriverListener(DriverListener listener) {
		DriverHolder.PROVIDER_INSTANCE.driverListeners.add(listener);
	}

	public static void restart(DesiredCapabilities capabilities) {
		AppiumDriver<MobileElement> newDriver = DriverHolder.PROVIDER_INSTANCE.buildDriver(capabilities);
		DriverHolder.setDriver(newDriver);

	}

	private static class DriverHolder {
		private static final DriverProvider PROVIDER_INSTANCE = new DriverProvider();
//		private static final AppiumDriver<MobileElement> DRIVER_INSTANCE = PROVIDER_INSTANCE.getDriver();

		private static void setDriver(AppiumDriver<MobileElement> driver){
			PROVIDER_INSTANCE.driver = driver;
			PROVIDER_INSTANCE.sendDriverRestartNotification();
//			DRIVER_INSTANCE = driver;
			System.out.println("Driver instance updated: " + driver.toString());
		}
	}
}
