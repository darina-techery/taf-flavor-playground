package tests;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.LoginSteps;
import utils.LogProvider;

public final class LoginTests extends BaseTestWithDriver implements LogProvider {

	LoginSteps loginSteps;

	final Logger log = getLogger();

	@BeforeClass
	void initSteps() {
		log.debug("Init steps component [START]");
		loginSteps = getStepsComponent().loginSteps();
		log.debug("Init steps component [ END ]");
	}

	@Test(invocationCount = 50)
	public void loginToApp() {
		loginSteps.login("65663904", "65663904");
	}

	@Test(invocationCount = 0)
	public void loginToApp2() {
		loginSteps.login("65663904", "65663904");
	}
}
