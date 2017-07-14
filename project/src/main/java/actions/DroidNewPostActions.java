package actions;

import ui.screens.AddTextScreen;
import ui.screens.NewPostScreen;

public class DroidNewPostActions extends NewPostActions {
	NewPostScreen newPostScreen = new NewPostScreen();
	AddTextScreen addTextScreen = new AddTextScreen();

	public void addTextToPost(String text) {
		waiter.click(newPostScreen.fldPostContent);
		waiter.setText(addTextScreen.fldText, text);
		waiter.click(addTextScreen.btnDone);
	}
}
