import base.BaseTestForLoggedInUserWithoutRestart;
import data.Platform;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
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
	private List<String> createdBucketItems = new ArrayList<>();


	@AfterTest
	public void deleteCreatedBucketLists() throws IOException {
		for (String bucketItemName : createdBucketItems) {
			bucketListSteps.deleteBucketList(bucketItemName);
		}
	}

	@BeforeTest
	public void openBucketListMenu() {
		bucketListSteps.openBucketListScreen();
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213564")
 	@Issue("https://techery.atlassian.net/browse/DTAUT-465")
	@SkipOn(platforms = {Platform.IPAD, Platform.IPHONE},
						jiraIssue = "https://techery.atlassian.net/browse/DTAUT-509",
						reason = "need to add ID to field for new bucket name on iOS")
 	@Test
	public void addNewBucketList() throws IOException {
		String bucketItemName = bucketListSteps.getRandomNameForBucketList();
		createdBucketItems.add(bucketItemName);
		MobileElement bucketList = bucketListSteps.createNewBucketListWithName(bucketItemName);
		Assert.assertThat("New BucketList should be found in BucketLists", bucketList,
				notNullValue());

	}

}

