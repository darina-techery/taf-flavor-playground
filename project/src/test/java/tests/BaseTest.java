package tests;

import actions.definitions.ActionsDefinition;
import actions.definitions.DroidActionsDefinition;
import actions.definitions.IPhoneActionsDefinition;
import dagger.*;
import data.Configuration;
import driver.DriverListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.Logger;
import utils.LogProvider;

abstract class BaseTest implements LogProvider, DriverListener {

    private StepsComponent stepsComponent;

    private Configuration configuration;

    protected Logger log = getLogger();

    BaseTest(){
    	initConfiguration();
    	initStepsComponent();
    	subscribeToDriverUpdates();
    }

	StepsComponent getStepsComponent() {
		return stepsComponent;
	}

	Configuration getConfiguration() {
    	return configuration;
	}

    private void initConfiguration(){
    	ConfigurationComponent component = DaggerConfigurationComponent
			    .create();
	    configuration = component.getConfiguration();
    }

    private void initStepsComponent(){
    	ActionsDefinition actionDefinitions;
    	switch (configuration.platformName) {
		    case ANDROID_PHONE:
		    	actionDefinitions = new DroidActionsDefinition();
		    	break;
		    case IPHONE:
		    	actionDefinitions = new IPhoneActionsDefinition();
		    	break;
		    case ANDROID_TABLET:
		    case IPAD:
		    default:
		    	throw new RuntimeException("No action definitions created for "+configuration.platformName);
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
