package steps;

import actions.rest.SocialAPIActions;
import com.worldventures.dreamtrips.api.api_common.model.UniqueIdentifiable;
import com.worldventures.dreamtrips.api.feed.model.FeedItem;
import com.worldventures.dreamtrips.api.hashtags.model.HashtagsSearchResponse;
import org.apache.logging.log4j.LogManager;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;
import utils.exceptions.FailedTestException;
import utils.exceptions.FailedWaitAttemptException;
import utils.waiters.AnyWait;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.Duration;
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
	public List<FeedItem> getFeedItemsByHashtags(String hashtags) throws IOException {
		AnyWait<Void, HashtagsSearchResponse> waitUntilFeedItemsAreFetched = new AnyWait<>();
		waitUntilFeedItemsAreFetched.duration(Duration.ofMinutes(1));
		waitUntilFeedItemsAreFetched.calculate(() -> {
			try {
				return socialAPIActions.searchFeedItemsByHashtags(hashtags);
			} catch (SocketTimeoutException e) {
				throw new FailedWaitAttemptException("Search by hashtag service is not available, retry.");
			} catch (IOException e) {
				throw new FailedTestException("Failed to fetch feed items by hashtags '" + hashtags + "'", e);
			}
		});
		waitUntilFeedItemsAreFetched.go();
		if (!waitUntilFeedItemsAreFetched.isSuccess()) {
			Throwable lastError = waitUntilFeedItemsAreFetched.getLastError();
			throw new FailedTestException("Failed to fetch feed items by hashtags '" + hashtags + "'", lastError);
		}
		HashtagsSearchResponse response = waitUntilFeedItemsAreFetched.result();
		return socialAPIActions.getFeedItemsFromSearchResponse(response);
	}
}
