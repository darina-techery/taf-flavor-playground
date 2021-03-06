package com.techery.dtat.tests;

import com.techery.dtat.tests.base.BaseTestForLoggedInUserWithoutRestart;
import com.techery.dtat.data.Platform;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import com.techery.dtat.steps.BucketListSteps;
import com.techery.dtat.utils.annotations.SkipOn;
import com.techery.dtat.utils.log.LogProvider;
import com.techery.dtat.utils.runner.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;

public final class BucketListTests extends BaseTestForLoggedInUserWithoutRestart implements LogProvider {
	private BucketListSteps bucketListSteps = getStepsComponent().bucketListSteps();
	private List<String> createdBucketItems = new ArrayList<>();


	@AfterMethod
	public void deleteCreatedBucketItem() throws IOException {
		for (String bucketItemName : createdBucketItems) {
			bucketListSteps.deleteBucketItem(bucketItemName);
		}
	}

	@BeforeMethod
	public void openBucketListMenu() {
		bucketListSteps.openBucketListScreen();
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213564")
 	@Issue("https://techery.atlassian.net/browse/DTAUT-465")
	@SkipOn(platforms = {Platform.IPAD, Platform.IPHONE},
						jiraIssue = "https://techery.atlassian.net/browse/DTAUT-509",
						reason = "need to add ID to field for new bucket name on iOS")
 	@Test
	public void addNewBucketItem() throws IOException {
		String testName = getTestMethodName();
		String bucketItemName = bucketListSteps.getUniqueNameForBucketItem(testName);
		createdBucketItems.add(bucketItemName);
		MobileElement bucketItem = bucketListSteps.createNewBucketItemWithName(bucketItemName);
		Assert.assertThat("New BucketItem should be found in BucketLists", bucketItem,
				notNullValue());

	}

}

