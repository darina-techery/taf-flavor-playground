package actions;

import driver.DriverProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class BaseActions {

	protected AppiumDriver<MobileElement> getDriver(){
		return DriverProvider.get();
	}



}
