package com.techery.dtat.ui.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import com.techery.dtat.ui.BaseUiModule;
import com.techery.dtat.utils.ui.Finder;

import java.util.List;

public class ActivityFeedScreen extends BaseUiModule {
	@AndroidFindBy(id = "share_post")
	@iOSFindBy(accessibility = "sharePost")
	public MobileElement btnCreatePost;

	@AndroidFindBy(id = "item_holder")
	@iOSFindBy(accessibility = "feedCell")
	public List<MobileElement> feedPostContainers;

	@iOSFindBy(accessibility = "feed search icon")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Search by hashtag']")
	public MobileElement btnSearch;

	@iOSFindBy(accessibility = "filter icon")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Filter']")
	public MobileElement btnFilter;

	@AndroidFindBy(accessibility = "More options")
	public MobileElement btnMore;

	@AndroidFindBy(id = "action_friend_requests")
	@iOSFindBy(accessibility = "friends icon")
	public MobileElement btnFriends;

	@AndroidFindBy(id = "action_unread_conversation")
	@iOSFindBy(accessibility = "messengerTabIcon")
	public MobileElement btnMessenger;

	public static final Finder POST_AVATAR_FINDER = new Finder()
		.android(By.id("feed_header_avatar"))
		.ios(MobileBy.AccessibilityId("avatarButton"));
	public MobileElement getPostAvatar(MobileElement postContainer) {
		return POST_AVATAR_FINDER.find(postContainer);
	}

	public static final Finder POST_TITLE_FINDER = new Finder()
		.android(By.id("feed_header_text"))
		.ios(MobileBy.AccessibilityId("actionLabel"));
	public MobileElement getLblPostTitle(MobileElement postContainer) {
		return POST_TITLE_FINDER.find(postContainer);
	}

	public static final Finder POST_LOCATION_FINDER = new Finder()
		.android(By.id("feed_header_location"))
		.ios(MobileBy.AccessibilityId("locationLabel"));
	public MobileElement getLblPostLocation(MobileElement postContainer) {
		return POST_LOCATION_FINDER.find(postContainer);
	}

	public static By ANDROID_POST_DATE_TIME_LOCATOR = By.id("feed_header_date");
	public static By IOS_POST_DATE_LOCATOR = MobileBy.AccessibilityId("dateLabel");
	public static By IOS_POST_TIME_LOCATOR = MobileBy.AccessibilityId("timeLabel");

	public static final Finder POST_TEXT_AREA_FINDER = new Finder()
		.android(By.id("post"))
		.ios(MobileBy.AccessibilityId("hashtagsLabel"));
	public MobileElement getPostTextArea(MobileElement postContainer) {
		return POST_TEXT_AREA_FINDER.find(postContainer);
	}

	public static final Finder POST_BTN_LIKE_FINDER = new Finder()
			.android(By.id("likes"))
			.ios(MobileBy.AccessibilityId("likeFeedButton"));
	public MobileElement getBtnLike(MobileElement postContainer) {
		return POST_BTN_LIKE_FINDER.find(postContainer);
	}

	public static final Finder POST_LBL_LIKES_COUNT_FINDER = new Finder()
			.android(By.id("likes_count"))
			.ios(MobileBy.AccessibilityId("likesCountButton"));
	public MobileElement getLblLikesCount(MobileElement postContainer) {
		return POST_LBL_LIKES_COUNT_FINDER.find(postContainer);
	}

	public static final Finder POST_BTN_COMMENT_FINDER = new Finder()
			.android(By.id("comments"))
			.ios(MobileBy.AccessibilityId("commentFeedButton"));
	public MobileElement getBtnComment(MobileElement postContainer) {
		return POST_BTN_COMMENT_FINDER.find(postContainer);
	}

	public static final Finder POST_BTN_MORE_FINDER = new Finder()
			.android(By.id("more"))
			.ios(MobileBy.AccessibilityId("moreFeedButton"));
	public MobileElement getBtnMore(MobileElement postContainer) {
		return POST_BTN_MORE_FINDER.find(postContainer);
	}

	public static final Finder POST_IMAGE_VIEW_FINDER = new Finder()
			.android(By.id("imageViewCover"))
			.ios(MobileBy.AccessibilityId("coverPhotoButton"));
	public MobileElement getImageView(MobileElement postContainer) {
		return POST_IMAGE_VIEW_FINDER.find(postContainer);
	}


	public static final Finder POST_IMAGE_TITLE_FINDER = new Finder()
			.android(By.id("textViewName"))
			.ios(MobileBy.AccessibilityId("bucketListNameLabel"));
	public MobileElement getLblImageTitle(MobileElement postContainer) {
		return POST_IMAGE_TITLE_FINDER.find(postContainer);
	}

	public static final Finder POST_IMAGE_DATE_FINDER = new Finder()
			.android(By.id("textViewDate"))
			.ios(null);
	public MobileElement getLblImageDate(MobileElement postContainer) {
		return POST_IMAGE_DATE_FINDER.find(postContainer);
	}

	public static final Finder POST_IMAGE_PLACE_FINDER = new Finder()
			.android(By.id("textViewPlace"))
			.ios(null);
	public MobileElement getLblImagePlace(MobileElement postContainer) {
		return POST_IMAGE_PLACE_FINDER.find(postContainer);
	}


}
