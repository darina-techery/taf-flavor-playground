package actions;

import driver.HasDriver;
import utils.exceptions.NotImplementedException;

public abstract class BaseActions implements HasDriver {

	public void waitForScreen() {
		throw new NotImplementedException();
	}

}
