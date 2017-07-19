package steps;

import actions.ActivityFeedActions;
import actions.NavigationActions;
import actions.NewPostActions;
import data.ui.MenuItem;
import io.appium.java_client.MobileElement;
import org.junit.Assume;
import ru.yandex.qatools.allure.annotations.Step;
import utils.StringHelper;
import utils.annotations.UseActions;
import utils.exceptions.FailedTestException;
import utils.runner.Assert;
import utils.ui.Screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ActivityFeedSteps {
	public static final String DEFAULT_HASH_TAG = "#AutoTestPost";
	private final NavigationActions navigationActions;
	private final ActivityFeedActions activityFeedActions;
	private final NewPostActions newPostActions;

	@UseActions
	public ActivityFeedSteps(NavigationActions navigationActions,
	                         ActivityFeedActions activityFeedActions,
	                         NewPostActions newPostActions) {
		this.navigationActions = navigationActions;
		this.activityFeedActions = activityFeedActions;
		this.newPostActions = newPostActions;
	}

	@Step("Go to Activity Feed screen")
	public void openActivityFeedScreen() {
		navigationActions.selectMenuItem(MenuItem.ACTIVITY_FEED);
	}

	@Step("Press button to share new post")
	public void pressSharePostButton() {
		activityFeedActions.pressSharePostButton();
	}

	@Step("Assert that Share New Post screen is displayed with proper layout")
	public void assertNewPostScreenIsDisplayed() {
		Assert.assertThat("Share New Post screen is displayed", newPostActions.isNewPostScreenShown());
	}

	@Step("Add some text to the new post")
	public void addTextToTheNewPost(String text) {
		newPostActions.addTextToPost(text);
	}

	@Step("Assert that expected text is displayed in post content area on Share New Post screen")
	public void assertExpectedTextDisplayedInPostContentArea(String expectedText) {
		Assert.assertThat("Post content area contains proper placeholder",
				newPostActions.getTextFromPostContentArea(), is(expectedText));
	}

	@Step("Save created post")
	public void saveNewPost(){
		newPostActions.savePost();
	}

	@Step("Create new post with text '{0}'")
	public void createNewTextPost(String text) {
		pressSharePostButton();
		addTextToTheNewPost(text);
		saveNewPost();
	}

	@Step("Find new post with text '{0}'")
	public MobileElement findNewPostByText(String text) {
		MobileElement postContainer = activityFeedActions.getPostContainerByText(text, false);
		Assume.assumeThat("Newly created post with text ["+text+"] should be found.", postContainer, notNullValue());
		return postContainer;
	}

	@Step("Validate title for a post in Activity Feed")
	public void assertThatPostHasValidTitle(MobileElement postContainer, String expectedTitle) {
		String actualTitle = activityFeedActions.getPostTitle(postContainer);
		Assert.assertThat("New post title should match provided one", actualTitle, is(expectedTitle));
	}

	@Step("Validate user avatar for a new post in Activity Feed")
	public void assertThatPostHasValidAvatar(MobileElement postContainer, File expectedAvatarFile) {
		try {
			BufferedImage actualAvatar = activityFeedActions.getPostAuthorAvatar(postContainer);
			BufferedImage expectedAvatar = ImageIO.read(expectedAvatarFile);
			if (!Screenshot.areImagesEqualByAverageColor(actualAvatar, expectedAvatar)) {
				File actualAvatarFile = new File("target/screenshots/actual_" + expectedAvatarFile.getName());
				ImageIO.write(actualAvatar, "jpeg", actualAvatarFile);
				Assert.assertThat(String.format("Avatar mismatch: expected image as in %s, but was as in %s",
						expectedAvatarFile.getAbsolutePath(), actualAvatarFile.getAbsolutePath()),
						false);
			}
		}
		catch (IOException e) {
			throw new FailedTestException("Could not validate user avatar", e);
		}
	}

	@Step("Validate timestamp for a new post in Activity Feed")
	public void assertThatPostHasValidTimestamp(MobileElement postContainer, LocalDateTime expectedDateTime,
	                                            Duration allowedDifference) {
		LocalDateTime actualDateTime = activityFeedActions.getPostTimestamp(postContainer);
		Duration actualDifference = Duration.between(actualDateTime, expectedDateTime);
		Assert.assertThat(
				"Difference between actual post creating time and timestamp in post " +
						"should be less than allowed post creating delay",
				actualDifference, lessThan(allowedDifference));
	}

	@Step("Generate hash tag for test post based on current time and test name: '{0}'")
	public String getHashTagBasedOnTimeAndTestName(String testName) {
		return String.format("%s_%s_%s", DEFAULT_HASH_TAG, testName,
				StringHelper.getTimestampSuffix());
	}

}
