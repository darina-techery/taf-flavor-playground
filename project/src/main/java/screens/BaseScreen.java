package screens;

import driver.DriverListener;
import driver.DriverProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

abstract class BaseScreen implements DriverListener {

	BaseScreen() {
        System.out.println("Base page constructor");
        AppiumDriver<MobileElement> driver = DriverProvider.get();
        initPageElements(driver);
		subscribeToDriverUpdates();
    }

	@Override
	public void subscribeToDriverUpdates() {
		DriverProvider.addDriverListener(this);
	}

	@Override
    public void receiveDriverUpdate(AppiumDriver<MobileElement> driver) {
		System.out.println(this.getClass().getName() + " received a driver update");
		initPageElements(driver);
    }

    private void initPageElements(AppiumDriver<MobileElement> driver) {
        FieldDecorator decorator = new AppiumFieldDecorator(driver);
        PageFactory.initElements(decorator, this);
        initDynamicFields(driver);
    }

	private void initDynamicFields(AppiumDriver<MobileElement> driver) {
		//...
	}
}
