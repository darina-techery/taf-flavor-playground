package steps;

import actions.BucketListActions;
import actions.NavigationActions;
import actions.rest.BucketListAPIActions;
import data.ui.MenuItem;
import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

import java.io.IOException;

public class BucketListSteps {
	private final BucketListActions bucketListActions;
	private final NavigationActions navigationActions;
	private final BucketListAPIActions bucketListAPIActions;

	@UseActions
	public BucketListSteps(BucketListActions bucketListActions,
                           NavigationActions navigationActions,
						   BucketListAPIActions bucketListAPIActions){
		this.bucketListActions = bucketListActions;
		this.navigationActions = navigationActions;
		this.bucketListAPIActions = bucketListAPIActions;
	}

	@Step("Go to BucketList screen")
	public void openBucketListScreen(){
		navigationActions.selectMenuItem(MenuItem.BUCKET_LIST);
		bucketListActions.waitForScreen();
	}


	@Step("Create new Bucket List with name {0}")
	public MobileElement createNewBucketListWithName(String bucketListName) {
		bucketListActions.pressAddButton();
		bucketListActions.enterBucketListName(bucketListName);
		bucketListActions.pressDone();
		return bucketListActions.getBucketList(bucketListName,false);
	}

	public String getRandomNameForBucketList(String methodName){
		return bucketListActions.generateNameForBucketList(methodName);
	}

	@Step("Delete Bucket List with name {0}")
	public void deleteBucketList(String bucketListName) throws IOException {
		String bucketListUid = bucketListAPIActions.getUidFromBucketItemListsWithSpecificName(bucketListName);
		bucketListAPIActions.deleteBucketListWithUid(bucketListUid);
	}
}
