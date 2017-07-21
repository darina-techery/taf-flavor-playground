package com.techery.dtat.actions;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.NoSuchElementException;
import com.techery.dtat.ui.screens.ActivityFeedScreen;
import com.techery.dtat.utils.ui.Screenshot;
import com.techery.dtat.utils.waiters.AnyWait;
import com.techery.dtat.utils.waiters.Waiter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

public abstract class ActivityFeedActions extends BaseUiActions {
	ActivityFeedScreen activityFeedScreen = new ActivityFeedScreen();

	public MobileElement getPostContainerByText(String expectedText, boolean scrollIfNotFound) {
		final Waiter waiter = new Waiter();
		final AnyWait<String, MobileElement> finder = new AnyWait<>();
		finder.duration(Duration.ofSeconds(30));
		finder.with(expectedText);
		finder.when(() -> activityFeedScreen.feedPostContainers.size() > 0);
		finder.calculate(text -> findPostContainerByText.apply(text));
		if (scrollIfNotFound) {
			finder.until(element -> waiter.scrollDownIfElementIsNull.apply(element));
		} else {
			finder.until(Objects::nonNull);
		}
		finder.addIgnorableException(NoSuchElementException.class);
		finder.describe("Scroll down until element with text ["+expectedText+"] is found");
		MobileElement result = finder.go();
		return result;
	}

	private Function<String, MobileElement> findPostContainerByText = text -> {
		for (MobileElement container : activityFeedScreen.feedPostContainers) {
			MobileElement textField = activityFeedScreen.getPostTextArea(container);
			if (textField != null && textField.getText().trim().equals(text)) {
				return container;
			}
		}
		return null;
	};

	public void pressSharePostButton(){
		new Waiter().click(activityFeedScreen.btnCreatePost);
	}

	public String getPostTitle(MobileElement postContainer) {
		return new Waiter().getText(ActivityFeedScreen.POST_TITLE_FINDER.getBy(), postContainer);
	}

	public abstract LocalDateTime getPostTimestamp(MobileElement postContainer);

	public BufferedImage getPostAuthorAvatar(MobileElement postContainer) throws IOException {
		MobileElement avatar = activityFeedScreen.getPostAvatar(postContainer);
		return Screenshot.makeScreenshotOfElement(avatar);
	}
}
