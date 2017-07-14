package steps;

import actions.ActivityFeedActions;
import actions.NavigationActions;
import actions.NewPostActions;
import com.worldventures.dreamtrips.api.profile.model.UserProfile;
import data.ui.MenuItem;
import io.appium.java_client.MobileElement;
import org.junit.Assume;
import ru.yandex.qatools.allure.annotations.Step;
import utils.DateTimeHelper;
import utils.FileUtils;
import utils.StringHelper;
import utils.annotations.UseActions;
import utils.runner.Assert;
import utils.ui.Screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ActivityFeedSteps {
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
	public void assertThatPostHasValidTitle(MobileElement postContainer, UserProfile authorProfile) {
		String expectedTitle = String.format("%s %s added a Post", authorProfile.firstName(), authorProfile.lastName());
		String actualTitle = activityFeedActions.getPostTitle(postContainer);
		Assert.assertThat("New post title should match provided one", actualTitle, is(expectedTitle));
	}

	@Step("Validate user avatar for a new post in Activity Feed")
	public void assertThatPostHasValidAvatar(MobileElement postContainer, UserProfile authorProfile) throws IOException {
		String avatarUrl = authorProfile.avatar().thumb();
		String filename = String.format("%s%s%s",
				//filename
				avatarUrl.substring(avatarUrl.lastIndexOf("/") + 1, avatarUrl.lastIndexOf(".")),
				//timestamp
				StringHelper.getTimestampSuffix(),
				//extension
				avatarUrl.substring(avatarUrl.lastIndexOf(".")));
		File avatar = FileUtils.downloadRemoteFile(avatarUrl, filename);
		BufferedImage expectedAvatar = ImageIO.read(avatar);
		BufferedImage actualAvatar = activityFeedActions.getPostAuthorAvatar(postContainer);
		if (!Screenshot.areImagesEqualByAverageColor(actualAvatar, expectedAvatar)) {
			File actualAvatarFile = new File("target/screenshots/actual_"+filename);
			ImageIO.write(actualAvatar, "jpeg", actualAvatarFile);
			Assert.assertThat(String.format("Avatar mismatch: expected image as in %s, but was as in %s",
					avatar.getPath(), actualAvatarFile.getPath()),
					false);
		}
	}

	@Step("Validate timestamp for a new post in Activity Feed")
	public void assertThatPostHasValidTimestamp(MobileElement postContainer, LocalDateTime expectedTimestamp,
	                                            Duration allowedDifference) {
		ZoneId deviceZoneId = DateTimeHelper.getDeviceZoneId();
		ZonedDateTime expectedZonedDateTime = expectedTimestamp.atZone(deviceZoneId);
		LocalDateTime actualDateTime = activityFeedActions.getPostTimestamp(postContainer);
		ZonedDateTime actualZonedDateTime = actualDateTime.atZone(deviceZoneId);
		Duration actualDifference = Duration.between(expectedZonedDateTime, actualZonedDateTime);
		Assert.assertThat(
				"Difference between actual post creating time and timestamp in post " +
						"should be less than allowed post creating delay",
				actualDifference, lessThan(allowedDifference));
	}

	@Step("Validate new text post details: userpic, date and time, text content, title")
	public void assertThatTextPostDetailsMatchExpected(MobileElement postContainer,
	                                                   String expectedContent,
	                                                   LocalDateTime timestamp,
	                                                   UserProfile authorProfile) throws IOException {
		List<String> differences = new ArrayList<>();
		//title
		String expectedTitle = String.format("%s added Post", authorProfile.username());
		String actualTitle = activityFeedActions.getPostTitle(postContainer);
		if (!expectedTitle.equals(actualTitle)) {
			differences.add(String.format("Title mismatch: expected [%s], but was [%s]", expectedTitle, actualTitle));
		}
		//userpic
		String avatarUrl = authorProfile.avatar().thumb();
		String filename = String.format("%s%s%s",
				//filename
				avatarUrl.substring(avatarUrl.lastIndexOf("/") + 1, avatarUrl.lastIndexOf(".")),
				//timestamp
				StringHelper.getTimestampSuffix(),
				//extension
				avatarUrl.substring(avatarUrl.lastIndexOf(".")));
		File avatar = FileUtils.downloadRemoteFile(avatarUrl, filename);
		BufferedImage expectedAvatar = ImageIO.read(avatar);
		BufferedImage actualAvatar = activityFeedActions.getPostAuthorAvatar(postContainer);
		if (!Screenshot.areImagesEqualByAverageColor(actualAvatar, expectedAvatar)) {
			File actualAvatarFile = new File("target/screenshots/actual_"+filename);
			ImageIO.write(actualAvatar, "jpeg", actualAvatarFile);
			differences.add(String.format("Avatar mismatch: expected image "+avatar.getPath()
					+", but was "+actualAvatarFile.getPath()));
		}
		//timestamp

	}

	public static void main(String[] args) {
		String avatarUrl = "http://s3.amazonaws.com/dreamtrips-test/avatars/143256/thumb/avatar.jpeg";
		String filename = String.format("%s%s%s",
				//filename
				avatarUrl.substring(avatarUrl.lastIndexOf("/") + 1, avatarUrl.lastIndexOf(".")),
				//timestamp
				StringHelper.getTimestampSuffix(),
				//extension
				avatarUrl.substring(avatarUrl.lastIndexOf(".")));
		System.out.println(filename);
	}

}
