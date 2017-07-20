package actions.rest;

import com.worldventures.dreamtrips.api.bucketlist.model.BucketItemSimple;
import rest.api.clients.DreamTripsAPIClient;
import rest.api.services.DreamTripsAPI;
import user.UserCredentials;
import user.UserSessionManager;

import java.io.IOException;
import java.util.List;

public class BucketListAPIActions {
	private final DreamTripsAPI dreamTripsAPI;
	private final UserAPIActions userAPIActions;

	public BucketListAPIActions() {
		dreamTripsAPI = new DreamTripsAPIClient().create(DreamTripsAPI.class);
		userAPIActions = new UserAPIActions();
	}

	UserCredentials userCredentials = UserSessionManager.getActiveUser();

	public List<BucketItemSimple> getListOfBucketItemsList(Integer userId) throws IOException {
		return dreamTripsAPI.getUserBucketLists(userId).execute().body();
	}

	public String getUidFromBucketItemListsWithSpecificName(String bucketName) throws IOException {
		return getBucketItemFromBucketItemListsWithSpecificName(bucketName).name();
	}

	public BucketItemSimple getBucketItemFromBucketItemListsWithSpecificName(String bucketName) throws IOException {
		for(BucketItemSimple bucketItem : getListOfBucketItemsList(userAPIActions.getCurrentUserId())){
			if (bucketItem.name().equals(bucketName)) {
				return bucketItem;
			}
		}
		return null;
	}

	public int deleteBucketListWithUid(String uid) throws IOException {
		return dreamTripsAPI.deleteBucketItem(uid).execute().code();
	}
}
