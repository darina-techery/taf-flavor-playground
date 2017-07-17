package steps;

import actions.BucketListActions;
import actions.DreamTripDetailsActions;
import actions.DreamTripsActions;
import actions.NavigationActions;
import com.worldventures.dreamtrips.api.trip.model.TripDates;
import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import data.Configuration;
import data.ui.MenuItem;
import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;
import utils.runner.Assert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

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
