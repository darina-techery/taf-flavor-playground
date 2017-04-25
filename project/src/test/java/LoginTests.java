import base.BaseTestWithDriver;
import data.TestData;
import data.TestDataReader;
import data.structures.User;
import driver.DriverProvider;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.DelayMeter;
import utils.log.LogProvider;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public final class LoginTests extends BaseTestWithDriver implements LogProvider {

	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();

	private final Logger log = getLogger();

	@TestData(file = "user_credentials.json", key = "default_user")
	User defaultUser;


//	@Test
//	public void testInitUser() throws FileNotFoundException, IllegalAccessException {
//		for (Field f : this.getClass().getDeclaredFields()) {
//			if (f.isAnnotationPresent(TestData.class)) {
//				Class testDataClass = f.getType();
//				TestData dataSource = f.getAnnotation(TestData.class);
//				String filename = dataSource.file();
//				String key = dataSource.key();
//
//				JsonReader reader = new JsonReader(new FileReader(TestDataReader.TEST_DATA_FOLDER + "/" + filename));
//				Gson gson = new GsonBuilder().create();
//				if (key.isEmpty()) {
//					Object obj = gson.fromJson(reader, testDataClass);
//					f.set(this, obj);
//				} else {
//					Map<String, LinkedTreeMap> rawTypeResult = gson.fromJson(reader, LinkedTreeMap.class);
//					JsonObject jsonObject = gson.toJsonTree(rawTypeResult.get(key)).getAsJsonObject();
//					f.set(this, gson.fromJson(jsonObject, testDataClass));
//				}
//			}
//		}
//		log.info(defaultUser.username);
//	}
//
//	@BeforeClass
//	public void readUserData() throws FileNotFoundException {
//		defaultUser = new TestDataReader<>(User.DATA_FILE_NAME, User.class).readByKey("default_user");
//	}

	@BeforeClass
	public void readUserData_2() throws FileNotFoundException, IllegalAccessException {
		TestDataReader.readDataMembers(this);
	}

	@Test(invocationCount = 1)
	public void loginToApp() {
		loginSteps.login(defaultUser.username, defaultUser.password);
		navigationSteps.assertLandingPageLoaded();
	}

	@AfterClass(alwaysRun = true)
	public void reportDuration() {
		DelayMeter.reportOperationDuration(DriverProvider.INIT_PAGES_OPERATION, TimeUnit.MILLISECONDS);
	}
}

