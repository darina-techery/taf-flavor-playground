package base;

import actions.definitions.ActionsDefinition;
import actions.definitions.DroidActionsDefinition;
import actions.definitions.IPhoneActionsDefinition;
import dagger.*;
import data.Configuration;
import driver.DriverListener;
import ie.corballis.fixtures.annotation.FixtureAnnotations;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import utils.exceptions.FailedConfigurationException;
import utils.log.LogProvider;

public abstract class BaseTest implements LogProvider, DriverListener {

    private StepsComponent stepsComponent;

    public BaseTest() {
    	initFixtures();
    	initStepsComponent();
    	subscribeToDriverUpdates();
    }

	private void initFixtures() {
		try {
			FixtureAnnotations.initFixtures(this);
		} catch (Exception e) {
			throw new FailedConfigurationException(e, "Failed to read JSON fixtures with test data");
		}
	}

	protected StepsComponent getStepsComponent() {
		return stepsComponent;
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
		    case IPAD:
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
