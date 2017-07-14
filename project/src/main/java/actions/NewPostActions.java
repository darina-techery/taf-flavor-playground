package actions;

import ui.screens.AddTextScreen;
import ui.screens.NewPostScreen;
import utils.waiters.Waiter;

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
