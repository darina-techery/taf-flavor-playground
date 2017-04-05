package tests;

import actions.BaseActions;
import actions.definitions.ActionDefinitions;
import actions.definitions.DroidActionDefinitions;
import actions.definitions.IPhoneActionDefinitions;
import dagger.*;
import data.Configuration;

abstract class BaseTest {

    private StepsComponent stepsComponent;

    private Configuration configuration;

    BaseTest(){
    	initConfiguration();
    	initStepsComponent();
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
    	ActionDefinitions actionDefinitions;
    	switch (configuration.platformName) {
		    case ANDROID_PHONE:
		    	actionDefinitions = new DroidActionDefinitions();
		    	break;
		    case IPHONE:
		    	actionDefinitions = new IPhoneActionDefinitions();
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

}
