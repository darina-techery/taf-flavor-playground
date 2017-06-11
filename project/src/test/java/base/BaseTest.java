package base;

import actions.definitions.*;
import dagger.ActionsModule;
import dagger.DaggerStepsComponent;
import dagger.StepsComponent;
import data.Configuration;
import data.TestData;
import data.TestDataReader;
import driver.DriverListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import rest.api.hermet.HermetServiceManager;
import user.UserCredentials;
import user.UserSessionManager;
import utils.log.LogProvider;

public abstract class BaseTest implements LogProvider, DriverListener {

	@TestData(file = UserCredentials.DATA_FILE_NAME, key = "default_user")
	protected UserCredentials defaultUser;

    private StepsComponent stepsComponent;

    public BaseTest() {
	    TestDataReader.readDataMembers(this);
    	initStepsComponent();
    	subscribeToDriverUpdates();
    }

	protected StepsComponent getStepsComponent() {
		return stepsComponent;
	}

	@BeforeSuite
	public void setupHermetProxy() {
    	HermetServiceManager.initService(Configuration.getParameters().apiURL);
	}

	@AfterMethod
	public void resetActiveUserCredentials() {
		UserSessionManager.resetUserData();
	}

    private void initStepsComponent(){
    	ActionsDefinition actionDefinitions;
    	switch (Configuration.getParameters().platform) {
		    case ANDROID_PHONE:
		    	actionDefinitions = new DroidActionsDefinition();
		    	break;
		    case IPHONE:
		    	actionDefinitions = new IPhoneActionsDefinition();
		    	break;
		    case ANDROID_TABLET:
				actionDefinitions = new DroidTabletActionsDefinition();
				break;
		    case IPAD:
				actionDefinitions = new IPadActionsDefinition();
				break;
		    default:
		    	throw new RuntimeException("No action definitions created for "+Configuration.getParameters().platform);
	    }
	    stepsComponent = DaggerStepsComponent.builder()
			    .actionsModule(
					    new ActionsModule(actionDefinitions))
			    .build();
    }

	@Override
	public void receiveDriverUpdate(AppiumDriver<MobileElement> driver) {
		initStepsComponent();
	}
}
