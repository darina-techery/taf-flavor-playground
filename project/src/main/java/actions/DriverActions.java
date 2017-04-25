package actions;

import com.google.common.base.Preconditions;
import data.Configuration;
import driver.DriverProvider;
import driver.HasDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nonnull;
import java.util.Map;

public abstract class DriverActions implements HasDriver {

	public void rotateScreen(ScreenOrientation orientation) {
		getDriver().rotate(orientation);
		//delay for animation
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			throw new RuntimeException("Failed to rotate screen to "+orientation);
		}
	}

	public void resetApp(){
		getDriver().resetApp();
	}

	public ScreenOrientation getScreenOrientation(){
		return getDriver().getOrientation();
	}

	public void closeApp() {
		getDriver().closeApp();
	}

	public void hideKeyboard() {
		try {
			getDriver().hideKeyboard();
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to hide keyboard: ", e);
		}
	}

	public MobileElement waitFor(By by) {
		return (MobileElement) new WebDriverWait(getDriver(), 50)
				.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public abstract void declineAlert(By by);

	public abstract void resetApplication();

	public Map<String, String> extractAppStrings(@Nonnull String locale) {
		Preconditions.checkNotNull(locale);
		String lang = getLanguage(locale);
		return getDriver().getAppStringMap(lang);
	}

	protected String getLanguage(String locale) {
		String lang = "";
		switch (locale) {
			case "zh-sg":
				lang = Configuration.isAndroid() ? "zh-rCN" : "zh-Hans";
				break;
			case "zh-hk":
				lang = Configuration.isAndroid() ? "zh-rHK" : "zh-Hant";
				break;
			default:
				lang = locale;
		}
		return lang;
	}

	public void reInitDriver() {
		DriverProvider.restart();
	}

	public void reInitDriver(DesiredCapabilities capabilities) {
		DriverProvider.restart(capabilities);
	}
}
