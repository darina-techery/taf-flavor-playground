package base;

import actions.definitions.ActionsDefinition;
import actions.definitions.DroidActionsDefinition;
import actions.definitions.IOSActionsDefinition;
import actions.rest.HermetProxyActions;
import dagger.ActionsModule;
import dagger.DaggerStepsComponent;
import dagger.StepsComponent;
import data.Configuration;
import data.TestData;
import data.TestDataReader;
import driver.DriverListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import user.UserCredentials;
import user.UserSessionManager;
import utils.log.CommonLogMessages;
import utils.log.LogProvider;

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

	@BeforeClass
	public void startHermetServiceIfNotPresent() {
		hermetProxyActions.startMainService();
	}

	@BeforeMethod
	public void setTestMethodName(Method method) {
    	testMethodName = method.getName();
	}

	protected String getTestMethodName() {
    	return testMethodName;
	}

	@AfterMethod
	public void resetActiveUserCredentials() {
		UserSessionManager.resetUserData();
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

    @AfterClass
    public void cleanupCreatedStubs(){
    	hermetProxyActions.deleteCreatedStubsForMainService();
    }

	@Override
	public void receiveDriverUpdate(AppiumDriver<MobileElement> driver) {
		initStepsComponent();
	}
}
