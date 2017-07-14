import base.BaseTestAfterLogin;
import com.worldventures.dreamtrips.api.feed.model.FeedItem;
import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import data.Platform;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import steps.ActivityFeedSteps;
import steps.SocialAPISteps;
import utils.StringHelper;
import utils.annotations.SkipOn;
import utils.runner.Assert;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;

public class ActivityFeedTests extends BaseTestAfterLogin {
	ActivityFeedSteps activityFeedSteps = getStepsComponent().activityFeedSteps();
	SocialAPISteps socialAPISteps = getStepsComponent().socialAPISteps();

	private static final String DEFAULT_HASH_TAG = "#AutoTestPost";
	private List<FeedItem> createdFeedItems = new ArrayList<>();
	private PrivateUserProfile currentUserProfile;

	@BeforeClass
	public void getTestUserProfile() throws IOException {
		currentUserProfile = socialAPISteps.getCurrentUserProfile();
	}

	@AfterMethod(alwaysRun = true)
	public void deleteCreatedFeedItems() {
		socialAPISteps.deleteFeedItemsAndRemoveDeletedFromList(createdFeedItems);
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213686")
	@Issue("https://techery.atlassian.net/browse/DTAUT-499")
	@Test
	@SkipOn(platforms = {Platform.IPAD, Platform.IPHONE},
			jiraIssue = "https://techery.atlassian.net/browse/DTAUT-505",
			reason = "asked Max to make text field hint visible for Appium")
	public void openShareNewPostScreenAndValidateItsState(){
		activityFeedSteps.openActivityFeedScreen();
		activityFeedSteps.pressSharePostButton();
		activityFeedSteps.assertNewPostScreenIsDisplayed();
		activityFeedSteps.assertExpectedTextDisplayedInPostContentArea("Say something, add #hashtags");
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213688")
	@Issue("https://techery.atlassian.net/browse/DTAUT-500")
	@Test
	public void addTextToNewPostAndValidateItOnPopup(){
		String hashTags = getHashTagsWithMethodNameAndTimestamp();
		String postContent = "Text post " + hashTags;
		activityFeedSteps.openActivityFeedScreen();
		activityFeedSteps.pressSharePostButton();
		activityFeedSteps.addTextToTheNewPost(postContent);
		activityFeedSteps.assertExpectedTextDisplayedInPostContentArea(postContent);
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213556")
	@Issue("https://techery.atlassian.net/browse/DTAUT-434")
	@Test
	public void createNewTextPostAndValidateItsTextContent() throws IOException {
		String hashTags = getHashTagsWithMethodNameAndTimestamp();
		String postContent = "Text post " + hashTags;
		activityFeedSteps.openActivityFeedScreen();
		activityFeedSteps.createNewTextPost(postContent);
		addCreatedFeedItemToCleanupList(hashTags);

		MobileElement newPostContainer = activityFeedSteps.findNewPostByText(postContent);
		Assert.assertThat("New post with text ["+postContent+"] was not found in Activity Feed", newPostContainer,
				notNullValue());
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213556")
	@Issue("https://techery.atlassian.net/browse/DTAUT-434")
	@Test
	@SkipOn(platforms = {Platform.IPHONE, Platform.IPAD},
			jiraIssue = "https://worldventures.atlassian.net/browse/SOCIAL-1039",
			reason = "[iOS] post title in Activity Feed mismatch: 'added Post' instead of 'added a Post'")
	public void createNewTextPostAndValidateItsTitleInFeed() throws IOException {
		String hashTags = getHashTagsWithMethodNameAndTimestamp();
		String postContent = "Text post " + hashTags;
		activityFeedSteps.openActivityFeedScreen();
		activityFeedSteps.createNewTextPost(postContent);
		addCreatedFeedItemToCleanupList(hashTags);

		MobileElement newPostContainer = activityFeedSteps.findNewPostByText(postContent);
		activityFeedSteps.assertThatPostHasValidTitle(newPostContainer, currentUserProfile);
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213556")
	@Issue("https://techery.atlassian.net/browse/DTAUT-434")
	@Test
	public void createNewTextPostAndValidateItsAuthorAvatar() throws IOException {
		String hashTags = getHashTagsWithMethodNameAndTimestamp();
		String postContent = "Text post " + hashTags;
		activityFeedSteps.openActivityFeedScreen();

		activityFeedSteps.createNewTextPost(postContent);
		addCreatedFeedItemToCleanupList(hashTags);

		MobileElement newPostContainer = activityFeedSteps.findNewPostByText(postContent);
		activityFeedSteps.assertThatPostHasValidAvatar(newPostContainer, currentUserProfile);
	}

	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213556")
	@Issue("https://techery.atlassian.net/browse/DTAUT-434")
	@Test
	public void createNewTextPostAndValidateItsDateAndTime() throws IOException {
		String hashTags = getHashTagsWithMethodNameAndTimestamp();
		String postContent = "Text post " + hashTags;
		activityFeedSteps.openActivityFeedScreen();

		LocalDateTime timeWhenCreatingPost = LocalDateTime.now();
		activityFeedSteps.createNewTextPost(postContent);
		addCreatedFeedItemToCleanupList(hashTags);

		MobileElement newPostContainer = activityFeedSteps.findNewPostByText(postContent);
		Duration allowedDelayWhenCreatingPost = Duration.ofMinutes(1);
		activityFeedSteps.assertThatPostHasValidTimestamp(newPostContainer, timeWhenCreatingPost,
				allowedDelayWhenCreatingPost);
	}

	private String getHashTagsWithMethodNameAndTimestamp() {
		return String.format("%s #%s #%s", DEFAULT_HASH_TAG, getTestMethodName(), StringHelper.getTimestampSuffix());
	}

	private void addCreatedFeedItemToCleanupList(String hashtags) throws IOException {
		List<FeedItem> itemsByHashtags = socialAPISteps.getFeedItemsByHashtags(hashtags);
		createdFeedItems.addAll(itemsByHashtags);
	}

}


