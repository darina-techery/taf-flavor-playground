package com.techery.dtat.driver.capabilities;

import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class BaseCapabilities {
	public static final int DEFAULT_TIME_OUT_IN_SECONDS = 30;

	public abstract DesiredCapabilities getCapabilities();
}
