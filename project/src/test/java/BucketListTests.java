import base.BaseTestForLoggedInUserWithoutRestart;
import data.Platform;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import steps.BucketListSteps;
import utils.annotations.SkipOn;
import utils.log.LogProvider;
import utils.runner.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;

public final class BucketListTests extends BaseTestForLoggedInUserWithoutRestart implements LogProvider {
	private BucketListSteps bucketListSteps = getStepsComponent().bucketListSteps();
	private List<String> createdBucketLists = new ArrayList<>();


	@AfterClass
	public void deleteCreatedBucketLists() throws IOException {
		for (String bucketListName : createdBucketLists) {
			bucketListSteps.deleteBucketList(bucketListName);
		}
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213564")
 	@Issue("https://techery.atlassian.net/browse/DTAUT-465")
	@SkipOn(platforms = {Platform.IPAD, Platform.IPHONE},
						jiraIssue = "")
						//reason = "need to add ID to field with bucketName")
 	@Test
	public void addNewBucketList() throws IOException {
		bucketListSteps.openBucketListScreen();
		String bucketListName = bucketListSteps.getRandomNameForBucketList();
		createdBucketLists.add(bucketListName);
		MobileElement bucketList = bucketListSteps.createNewBucketListWithName(bucketListName);
		Assert.assertThat("New BucketList not found in BucketLists", bucketList,
				notNullValue());

	}

}

