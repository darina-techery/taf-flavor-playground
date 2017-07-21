package com.techery.dtat.actions;

public class IOSNewPostActions extends NewPostActions {
	public void addTextToPost(String text) {
		waiter.click(newPostScreen.fldPostContent);
		//do not use setText, as keyboard cannot be hidden here
		newPostScreen.fldPostContent.sendKeys(text);
		waiter.click(addTextScreen.btnDone);
	}
}
