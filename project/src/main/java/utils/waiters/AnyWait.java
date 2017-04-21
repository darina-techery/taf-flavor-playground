package utils.waiters;

import driver.HasDriver;

public class AnyWait<T, R> extends BaseWait<T, R>
		implements HasDriver {

	@Override
	protected String describeTestableObject() {
		return "operation with timeout";
	}

	@Override
	protected void setDefaultPrecondition() {
		when(()-> true);
	}

}
