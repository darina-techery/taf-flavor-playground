package tests;

import driver.DriverProvider;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import steps.LoginSteps;
import utils.DelayMeter;
import utils.LogProvider;

import java.util.concurrent.TimeUnit;

public final class LoginTests extends BaseTestWithDriver implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();

	private final Logger log = getLogger();

//	@BeforeClass
//	void initSteps() {
//		log.debug("Init steps component [START]");
//		loginSteps = getStepsComponent().loginSteps();
//		log.debug("Init steps component [ END ]");
//	}

	@Test(invocationCount = 5)
	public void loginToApp() {
		loginSteps.login("65663904", "65663904");
	}

	@Test(invocationCount = 0)
	public void loginToApp2() {
		loginSteps.login("65663904", "65663904");
	}

	@AfterClass(alwaysRun = true)
	public void reportDuration() {
		DelayMeter.reportOperationDuration(DriverProvider.INIT_PAGES_OPERATION, TimeUnit.MILLISECONDS);
	}
}
