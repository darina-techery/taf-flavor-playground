package actions;

import ui.screens.AddTextScreen;
import ui.screens.NewPostScreen;
import utils.waiters.Waiter;

public class NewPostActions extends BaseUiActions {
	NewPostScreen newPostScreen = new NewPostScreen();
	AddTextScreen addTextScreen = new AddTextScreen();

	private Waiter waiter = new Waiter();

	public boolean isNewPostScreenShown() {
		return waiter.isDisplayed(newPostScreen.fldPostContent);
	}

	public String getTextFromPostContentArea() {
		return waiter.getText(newPostScreen.fldPostContent);
	}

	public void addTextToPost(String text) {
		waiter.click(newPostScreen.fldPostContent);
		waiter.setText(addTextScreen.fldText, text);
		waiter.click(addTextScreen.btnDone);
	}

	public void savePost() {
		waiter.click(newPostScreen.btnCreatePost);
	}
}
