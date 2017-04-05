package archive;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public interface HasDriver<T extends AppiumDriver<MobileElement>> {
	T getDriver();
}
