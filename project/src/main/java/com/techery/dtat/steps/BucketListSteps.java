package com.techery.dtat.steps;

import com.techery.dtat.actions.BucketListActions;
import com.techery.dtat.actions.NavigationActions;
import com.techery.dtat.actions.rest.BucketListAPIActions;
import com.techery.dtat.data.ui.MenuItem;
import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Step;
import com.techery.dtat.utils.annotations.UseActions;

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


	@Step("Create new Bucket Item with name {0}")
	public MobileElement createNewBucketItemWithName(String bucketListName) {
		bucketListActions.pressAddButton();
		bucketListActions.enterBucketItemName(bucketListName);
		bucketListActions.pressDone();
		return bucketListActions.getBucketItem(bucketListName,false);
	}

	public String getUniqueNameForBucketItem(String methodName){
		return bucketListActions.generateNameForBucketItem(methodName);
	}

	@Step("Delete Bucket Item with name {0}")
	public void deleteBucketItem(String bucketListName) throws IOException {
		String bucketItemUid = bucketListAPIActions.getUidFromBucketItemByName(bucketListName);
		bucketListAPIActions.deleteBucketItemWithUid(bucketItemUid);
	}
}
