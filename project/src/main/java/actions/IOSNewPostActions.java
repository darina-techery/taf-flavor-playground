package actions;

import ui.screens.AddTextScreen;
import ui.screens.NewPostScreen;

public class IOSNewPostActions extends NewPostActions {
	NewPostScreen newPostScreen = new NewPostScreen();
	AddTextScreen addTextScreen = new AddTextScreen();

	public void addTextToPost(String text) {
		waiter.click(newPostScreen.fldPostContent);
		//do not use setText, as keyboard cannot be hidden here
//		waiter.sendKeys(newPostScreen.fldPostContent, text);
		newPostScreen.fldPostContent.sendKeys(text);
		waiter.click(addTextScreen.btnDone);
	}
}
