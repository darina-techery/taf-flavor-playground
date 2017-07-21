package com.techery.dtat.tests;

import com.techery.dtat.tests.base.BaseTestForLoggedInUserWithoutRestart;
import com.worldventures.dreamtrips.api.feed.model.FeedItem;
import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import com.techery.dtat.data.Platform;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import com.techery.dtat.steps.ActivityFeedSteps;
import com.techery.dtat.steps.SocialAPISteps;
import com.techery.dtat.steps.UserAPISteps;
import com.techery.dtat.utils.DateTimeHelper;
import com.techery.dtat.utils.FileUtils;
import com.techery.dtat.utils.annotations.SkipOn;
import com.techery.dtat.utils.runner.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;

public class ActivityFeedTestsForNewPost extends BaseTestForLoggedInUserWithoutRestart {
	private ActivityFeedSteps activityFeedSteps = getStepsComponent().activityFeedSteps();
	private SocialAPISteps socialAPISteps = getStepsComponent().socialAPISteps();
	private UserAPISteps userAPISteps = getStepsComponent().userAPISteps();

	private List<FeedItem> createdFeedItems;
	private PrivateUserProfile defaultUserProfile;
	private File userAvatarFile;
	private MobileElement newPostContainer;
	private LocalDateTime createdAt;
	private String postContent;

	@BeforeClass
	public void createNewPost() throws IOException {
		defaultUserProfile = userAPISteps.getCurrentUserProfile();
		userAvatarFile = FileUtils.getResourceFile("images/blue.png");
		userAPISteps.uploadAvatarIfCurrentIsDifferent(defaultUserProfile, userAvatarFile);
		String testName = this.getClass().getSimpleName();
		String hashTag = activityFeedSteps.getHashTagBasedOnTimeAndTestName(testName);
		postContent = "Text post " + hashTag;

		activityFeedSteps.openActivityFeedScreen();
		activityFeedSteps.createNewTextPost(postContent);
		createdAt = DateTimeHelper.getDeviceTime();

		createdFeedItems = socialAPISteps.getFeedItemsByHashTags(hashTag);
		newPostContainer = activityFeedSteps.findNewPostByText(postContent);
	}

	@AfterClass(alwaysRun = true)
	public void deleteCreatedFeedItems() {
		socialAPISteps.deleteFeedItemsAndRemoveDeletedFromList(createdFeedItems);
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213556")
	@Issue("https://techery.atlassian.net/browse/DTAUT-434")
	@Test
	public void checkNewPostTextContent() throws IOException {
		Assert.assertThat("New post with text [" + postContent + "] should be found on top of Activity Feed",
				newPostContainer, notNullValue());
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213556")
	@Issue("https://techery.atlassian.net/browse/DTAUT-434")
	@Test
	@SkipOn(platforms = {Platform.IPHONE, Platform.IPAD},
			jiraIssue = "https://worldventures.atlassian.net/browse/SOCIAL-1039",
			reason = "[iOS] post title in Activity Feed mismatch: 'added Post' instead of 'added a Post'")
	public void checkNewPostTitle() throws IOException {
		String expectedTitle = String.format("%s %s added a Post", defaultUserProfile.firstName(),
				defaultUserProfile.lastName());
		activityFeedSteps.assertThatPostHasValidTitle(newPostContainer, expectedTitle);
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213556")
	@Issue("https://techery.atlassian.net/browse/DTAUT-434")
	@Test
	@SkipOn(platforms = {Platform.IPAD},
			jiraIssue = "https://techery.atlassian.net/browse/DTAUT-508",
			reason = "framework defect: invalid screen area is captured on screenshot")
	public void checkNewPostAuthorAvatar() throws IOException {
		activityFeedSteps.assertThatPostHasValidAvatar(newPostContainer, userAvatarFile);
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213556")
	@Issue("https://techery.atlassian.net/browse/DTAUT-434")
	@Test
	@SkipOn(platforms = {Platform.ANDROID_PHONE, Platform.ANDROID_TABLET},
			jiraIssue = "https://worldventures.atlassian.net/browse/SOCIAL-1036",
			reason = "[Android] Timestamp does not contain year value")
	public void checkNewPostTimestamp() throws IOException {
		Duration allowedDelayWhenCreatingPost = Duration.ofMinutes(1);
		activityFeedSteps.assertThatPostHasValidTimestamp(newPostContainer, createdAt,
				allowedDelayWhenCreatingPost);
	}

}


