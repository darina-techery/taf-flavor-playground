package com.techery.dtat.steps;

import com.techery.dtat.actions.rest.SocialAPIActions;
import com.worldventures.dreamtrips.api.api_common.model.UniqueIdentifiable;
import com.worldventures.dreamtrips.api.feed.model.FeedItem;
import com.worldventures.dreamtrips.api.hashtags.model.HashtagsSearchResponse;
import org.apache.logging.log4j.LogManager;
import ru.yandex.qatools.allure.annotations.Step;
import com.techery.dtat.utils.annotations.UseActions;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SocialAPISteps {
	private final SocialAPIActions socialAPIActions;
	@UseActions
	public SocialAPISteps(SocialAPIActions socialAPIActions) {
		this.socialAPIActions = socialAPIActions;
	}

	@Step("Delete feed item")
	public void deleteFeedItem(FeedItem item) throws IOException {
		String uid = ((UniqueIdentifiable) item.entity()).uid();
		socialAPIActions.deleteFeedItemByUid(uid);
	}

	@Step("Delete all feed items from to-delete list and remove them from this list")
	public void deleteFeedItemsAndRemoveDeletedFromList(List<FeedItem> items) {
		if (items != null) {
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
	}

	@Step("Get all feed items with hashtags '{0}'")
	public List<FeedItem> getFeedItemsByHashTags(String hashtags) throws IOException {
		HashtagsSearchResponse response = socialAPIActions.searchFeedItemsByHashtags(hashtags);
		return socialAPIActions.getFeedItemsFromSearchResponse(response);
	}
}
