package internal;

import actions.rest.SocialAPIActions;
import base.BaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class InternalAPITests extends BaseTest {
	private static final String HASH_TAG = "#AutoTestPost";

	@Test
	public void findAllDeleteByHashtag() throws IOException {
		new SocialAPIActions().deleteFeedItemsByHashTags(HASH_TAG);
	}
}
