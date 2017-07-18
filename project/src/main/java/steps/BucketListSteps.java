package steps;

import actions.BucketListActions;
import actions.NavigationActions;
import data.ui.MenuItem;
import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

public class BucketListSteps {
	private final BucketListActions bucketListActions;
	private final NavigationActions navigationActions;

	@UseActions
	public BucketListSteps(BucketListActions bucketListActions,
                           NavigationActions navigationActions){
		this.bucketListActions = bucketListActions;
		this.navigationActions = navigationActions;
	}

	@Step("Go to BucketList screen")
	public void openBucketListScreen(){
		navigationActions.selectMenuItem(MenuItem.BUCKET_LIST);
		bucketListActions.waitForScreen();
	}


	@Step("Create new Bucket List with random name")
	public MobileElement createNewBucketListWithRandomName() {
		String nameForBucketList = bucketListActions.generateNameForBucketList();
		bucketListActions.pressAddButton();
		bucketListActions.enterBucketListName(nameForBucketList);

		bucketListActions.pressDone();

		return bucketListActions.getBucketList(nameForBucketList,false);
	}
}
