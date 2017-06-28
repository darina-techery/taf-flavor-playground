package ui;

import driver.DriverListener;
import driver.DriverProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import utils.log.LogProvider;

public abstract class BaseUiModule implements DriverListener, LogProvider {

	private final Logger log = getLogger();

	public BaseUiModule() {
        AppiumDriver<MobileElement> driver = DriverProvider.get();
        initPageElements(driver);
		subscribeToDriverUpdates();
	}

	@Override
    public void receiveDriverUpdate(AppiumDriver<MobileElement> driver) {
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
