package actions;

import driver.HasDriver;
import utils.exceptions.NotImplementedException;

public abstract class BaseUiActions implements HasDriver {

	public void waitForScreen() {
		throw new NotImplementedException();
	}

}
