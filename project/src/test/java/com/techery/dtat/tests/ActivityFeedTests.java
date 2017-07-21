package com.techery.dtat.tests;

import com.techery.dtat.tests.base.BaseTestForLoggedInUserWithRestart;
import com.techery.dtat.data.Platform;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import com.techery.dtat.steps.ActivityFeedSteps;
import com.techery.dtat.utils.annotations.SkipOn;

public class ActivityFeedTests extends BaseTestForLoggedInUserWithRestart {
	private ActivityFeedSteps activityFeedSteps = getStepsComponent().activityFeedSteps();

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213686")
	@Issue("https://techery.atlassian.net/browse/DTAUT-499")
	@Test
	@SkipOn(platforms = {Platform.IPAD, Platform.IPHONE},
			jiraIssue = "https://techery.atlassian.net/browse/DTAUT-505",
			reason = "need to make text field hint visible for Appium")
	public void openShareNewPostScreenAndValidateItsState() {
		activityFeedSteps.openActivityFeedScreen();
		activityFeedSteps.pressSharePostButton();
		activityFeedSteps.assertNewPostScreenIsDisplayed();
		activityFeedSteps.assertExpectedTextDisplayedInPostContentArea("Say something, add #hashtags");
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213688")
	@Issue("https://techery.atlassian.net/browse/DTAUT-500")
	@Test
	public void addTextToNewPostAndValidateItOnPopup() {
		String postContent = "Text post " + getTestMethodName();
		activityFeedSteps.openActivityFeedScreen();
		activityFeedSteps.pressSharePostButton();
		activityFeedSteps.addTextToTheNewPost(postContent);
		activityFeedSteps.assertExpectedTextDisplayedInPostContentArea(postContent);
	}

}


