package data;

import javax.inject.Singleton;
import java.io.File;

@Singleton
public class Configuration {
	public final Platform platformName;
	public final String device;
	public final String appPath;
	public final String apiURL;
	public final boolean isCIRun;

	public Configuration(){
		platformName = readPlatformName();
		device = "51B46B7C-8415-4517-9B04-21A2045B20A5";
//		device = "emulator-5554";
		appPath = getAbsolutePathToApp();
		apiURL = "http://dtapp-qa.worldventures.biz";
		isCIRun = false;
	}

	private Platform readPlatformName(){
		return Platform.IPHONE;
//		return Platform.ANDROID_PHONE;
	}

	public boolean isAndroidPhone() {
		return platformName.equals(Platform.ANDROID_PHONE);
	}

	public boolean isAndroidTablet() {
		return platformName.equals(Platform.ANDROID_TABLET);
	}

	public boolean isIPhone() {
		return platformName.equals(Platform.IPHONE);
	}

	public boolean isIPad() {
		return platformName.equals(Platform.IPAD);
	}

	public boolean isIOS() { return isIPad() || isIPhone();}

	public boolean isAndroid() { return isAndroidPhone() || isAndroidTablet(); }

	public boolean isPhone(){
		return isAndroidPhone() || isIPhone();
	}

	public boolean isTablet(){
		return isAndroidTablet() || isIPad();
	}

	private String getAbsolutePathToApp(){
		String applicationFileName = isIOS() ? "DreamTrip.app" : "DreamTrips.apk";
		File fullPath = new File("./apps", applicationFileName);
		return fullPath.getAbsolutePath();
	}

	public enum Platform {
		ANDROID_PHONE,
		ANDROID_TABLET,
		IPHONE,
		IPAD
	}
}
