package actions;

import driver.DriverProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class BaseActions {

//	public BaseActions(){
//		initScreens();
//	}
//
//	private void initScreens() {
//		Class actionsClass = this.getClass();
//		while (!actionsClass.equals(BaseActions.class)) {
//			for (Field f : actionsClass.getDeclaredFields()) {
//				if (f.getAnnotation(Page.class) != null) {
//					Class pageClass = f.getType();
//
//				}
//			}
//			actionsClass = actionsClass.getSuperclass();
//		}
//	}

	protected AppiumDriver<MobileElement> getDriver(){
		return DriverProvider.get();
	}



}
