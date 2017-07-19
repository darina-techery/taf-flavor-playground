package actions;

public class DroidNewPostActions extends NewPostActions {
	public void addTextToPost(String text) {
		waiter.click(newPostScreen.fldPostContent);
		waiter.setText(addTextScreen.fldText, text);
		waiter.click(addTextScreen.btnDone);
	}
}
