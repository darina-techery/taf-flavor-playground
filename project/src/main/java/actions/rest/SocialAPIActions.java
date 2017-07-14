package actions.rest;

import com.worldventures.dreamtrips.api.api_common.model.UniqueIdentifiable;
import com.worldventures.dreamtrips.api.feed.model.FeedItem;
import com.worldventures.dreamtrips.api.hashtags.model.HashtagsSearchResponse;
import org.junit.Assume;
import rest.api.clients.DreamTripsAPIClient;
import rest.api.services.DreamTripsAPI;
import retrofit2.Response;
import utils.DateTimeHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class SocialAPIActions {
	private final DreamTripsAPI dreamTripsAPI;
	public SocialAPIActions() {
		dreamTripsAPI = new DreamTripsAPIClient().create(DreamTripsAPI.class);
	}

	public HashtagsSearchResponse searchFeedItemsByHashtags(String hashTags) throws IOException {
		String before = DateTimeHelper.getCurrentZonedDateTime();
		Response<HashtagsSearchResponse> response = dreamTripsAPI.getPostsByHashTag(before, hashTags).execute();
		Assume.assumeThat("Requesting feed items by hashtag ["+hashTags+"] was successful.",
				response.isSuccessful(), is(true));
		return response.body();
	}

	public List<FeedItem> getFeedItemsFromSearchResponse(HashtagsSearchResponse response) {
		List<FeedItem> items = new ArrayList<>();
		response.data().forEach(wrapper -> items.addAll(wrapper.items()));
		return items;
	}

	public void deleteFeedItemByUid(String uid) throws IOException {
		dreamTripsAPI.deletePost(uid).execute();
	}

	public void deleteFeedItemsByHashTags(String hashTags) throws IOException {
		HashtagsSearchResponse response = searchFeedItemsByHashtags(hashTags);
		List<FeedItem> itemsInResponse = getFeedItemsFromSearchResponse(response);
		for (FeedItem feedItem : itemsInResponse) {
			String uid = ((UniqueIdentifiable) feedItem.entity()).uid();
			deleteFeedItemByUid(uid);
		}
		response = searchFeedItemsByHashtags(hashTags);
		Assume.assumeThat("All items by tag ["+hashTags+"] were deleted,", response.data(), is(empty()));
	}
}
