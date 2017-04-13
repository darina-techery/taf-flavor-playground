package steps;

import actions.BucketListActions;
import utils.annotations.UseActions;

public class BucketListSteps {
	private final BucketListActions bucketListActions;

	@UseActions
	public BucketListSteps(BucketListActions bucketListActions){
		this.bucketListActions = bucketListActions;
	}
}
