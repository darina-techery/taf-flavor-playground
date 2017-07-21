package com.techery.dtat.tests.base;

import com.techery.dtat.actions.definitions.ActionsDefinition;
import com.techery.dtat.actions.definitions.DroidActionsDefinition;
import com.techery.dtat.actions.definitions.IOSActionsDefinition;
import com.techery.dtat.actions.rest.HermetProxyActions;
import com.techery.dtat.data.Configuration;
import com.techery.dtat.data.TestData;
import com.techery.dtat.data.TestDataReader;
import com.techery.dtat.driver.DriverListener;
import com.techery.dtat.user.UserCredentials;
import com.techery.dtat.utils.log.CommonLogMessages;
import com.techery.dtat.utils.log.LogProvider;
import dagger.ActionsModule;
import dagger.DaggerStepsComponent;
import dagger.StepsComponent;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public abstract class BaseTest implements LogProvider, DriverListener, CommonLogMessages {
	@TestData(file = UserCredentials.DATA_FILE_NAME, key = "default_user")
	protected UserCredentials defaultUser;

    private StepsComponent stepsComponent;
    private HermetProxyActions hermetProxyActions;
    private String testMethodName;

    public BaseTest() {
	    TestDataReader.readDataMembers(this);
    	initStepsComponent();
    	subscribeToDriverUpdates();
	    hermetProxyActions = new HermetProxyActions();
    }

	protected StepsComponent getStepsComponent() {
		return stepsComponent;
	}

	@BeforeSuite(alwaysRun = true)
	public void startHermetServiceIfNotPresent() {
		hermetProxyActions.startMainService();
	}

	@BeforeMethod(alwaysRun = true)
	public void setTestMethodName(Method method) {
    	testMethodName = method.getName();
	}

	protected String getTestMethodName() {
    	return testMethodName;
	}

    private void initStepsComponent(){
    	ActionsDefinition actionDefinitions;
    	switch (Configuration.getParameters().platform) {
		    case ANDROID_PHONE: case ANDROID_TABLET:
		    	actionDefinitions = new DroidActionsDefinition();
		    	break;
		    case IPHONE: case IPAD:
		    	actionDefinitions = new IOSActionsDefinition();
		    	break;
		    default:
		    	throw new RuntimeException("No action definitions created for "+Configuration.getParameters().platform);
	    }
	    stepsComponent = DaggerStepsComponent.builder()
			    .actionsModule(
					    new ActionsModule(actionDefinitions))
			    .build();
    }

    @AfterClass(alwaysRun = true)
    public void cleanupCreatedStubs(){
    	hermetProxyActions.deleteCreatedStubsForMainService();
    }

	@Override
	public void receiveDriverUpdate(AppiumDriver<MobileElement> driver) {
		initStepsComponent();
	}
}
