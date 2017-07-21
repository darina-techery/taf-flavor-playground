package com.techery.dtat.actions;

import com.techery.dtat.driver.HasDriver;
import com.techery.dtat.utils.exceptions.NotImplementedException;

public abstract class BaseUiActions implements HasDriver {

	public void waitForScreen() {
		throw new NotImplementedException();
	}

}
