package com.techery.dtat.actions;

import com.techery.dtat.ui.screens.AddTextScreen;
import com.techery.dtat.ui.screens.NewPostScreen;
import com.techery.dtat.utils.waiters.Waiter;

public abstract class NewPostActions extends BaseUiActions {
	NewPostScreen newPostScreen = new NewPostScreen();
	AddTextScreen addTextScreen = new AddTextScreen();

	protected Waiter waiter = new Waiter();

	public boolean isNewPostScreenShown() {
		return waiter.isDisplayed(newPostScreen.fldPostContent);
	}

	public String getTextFromPostContentArea() {
		return waiter.getText(newPostScreen.fldPostContent);
	}

	public abstract void addTextToPost(String text);

	public void savePost() {
		waiter.click(newPostScreen.btnCreatePost);
	}
}
