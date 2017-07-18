import base.BaseTestAfterLogin;
import data.Platform;
import io.appium.java_client.MobileElement;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import steps.BucketListSteps;
import steps.DriverSteps;
import steps.LoginSteps;
import steps.NavigationSteps;
import utils.annotations.SkipOn;
import utils.log.LogProvider;
import utils.runner.Assert;

import static org.hamcrest.core.IsNull.notNullValue;

public final class BucketListTests extends BaseTestAfterLogin implements LogProvider {
	private BucketListSteps bucketListSteps = getStepsComponent().bucketListSteps();

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213564")
 	@Issue("https://techery.atlassian.net/browse/DTAUT-465")
	@SkipOn(platforms = {Platform.IPAD, Platform.IPHONE},
						jiraIssue = "")
						//reason = "need to add ID to field with bucketName")
 	@Test
	public void addNewBucketList() {
		bucketListSteps.openBucketListScreen();
		MobileElement bucketList = bucketListSteps.createNewBucketListWithRandomName();
		Assert.assertThat("New BucketList not found in BucketLists", bucketList,
				notNullValue());
	}

}

