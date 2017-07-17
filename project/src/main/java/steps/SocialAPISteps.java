package steps;

import actions.rest.SocialAPIActions;
import actions.rest.UserAPIActions;
import com.worldventures.dreamtrips.api.api_common.model.UniqueIdentifiable;
import com.worldventures.dreamtrips.api.feed.model.FeedItem;
import com.worldventures.dreamtrips.api.hashtags.model.HashtagsSearchResponse;
import org.apache.logging.log4j.LogManager;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SocialAPISteps {
	private final SocialAPIActions socialAPIActions;
	private final UserAPIActions userAPIActions;
	@UseActions
	public SocialAPISteps(SocialAPIActions socialAPIActions, UserAPIActions userAPIActions) {
		this.socialAPIActions = socialAPIActions;
		this.userAPIActions = userAPIActions;
	}

	@Step("Delete feed item")
	public void deleteFeedItem(FeedItem item) throws IOException {
		String uid = ((UniqueIdentifiable) item.entity()).uid();
		socialAPIActions.deleteFeedItemByUid(uid);
	}

	@Step("Delete all feed items from to-delete list and remove them from this list")
	public void deleteFeedItemsAndRemoveDeletedFromList(Set<FeedItem> items) {
		Iterator<FeedItem> i = items.iterator();
		while (i.hasNext()) {
			FeedItem itemToDelete = i.next();
			try {
				deleteFeedItem(itemToDelete);
				i.remove();
			} catch (IOException e) {
				LogManager.getLogger().error("Failed to delete feed item.", e);
			}
		}
	}

	@Step("Get all feed items with hashtags '{0}'")
	public List<FeedItem> getFeedItemsByHashtags(String hashtags) throws IOException {
		HashtagsSearchResponse response = socialAPIActions.searchFeedItemsByHashtags(hashtags);
		return socialAPIActions.getFeedItemsFromSearchResponse(response);
	}
}
