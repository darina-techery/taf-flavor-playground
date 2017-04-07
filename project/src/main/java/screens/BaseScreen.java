package screens;

import driver.DriverListener;
import driver.DriverProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import utils.LogProvider;

abstract class BaseScreen implements DriverListener, LogProvider {

	protected final Logger log = getLogger();

	BaseScreen() {
        AppiumDriver<MobileElement> driver = DriverProvider.get();
        initPageElements(driver);
		subscribeToDriverUpdates();
	}

	@Override
    public void receiveDriverUpdate(AppiumDriver<MobileElement> driver) {
		log.debug(this.getClass().getSimpleName() + " received a driver update.");
		initPageElements(driver);
    }

    private void initPageElements(AppiumDriver<MobileElement> driver) {
	    log.debug("Initializing elements: [START]");
        FieldDecorator decorator = new AppiumFieldDecorator(driver);
        PageFactory.initElements(decorator, this);
        initDynamicFields(driver);
	    log.debug("Initializing elements: [ END ]");
    }

	private void initDynamicFields(AppiumDriver<MobileElement> driver) {
		//...
	}
}
