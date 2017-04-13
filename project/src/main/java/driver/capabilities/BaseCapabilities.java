package driver.capabilities;

import dagger.DaggerConfigurationComponent;
import data.Configuration;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.inject.Inject;

public abstract class BaseCapabilities {
	@Inject
	Configuration configuration;

	public BaseCapabilities(){
		DaggerConfigurationComponent
				.create()
				.inject(this);
	}

	public static int DEFAULT_TIME_OUT_IN_SECONDS = 30;

	public Configuration getConfiguration(){
		return configuration;
	}

	public abstract DesiredCapabilities getCapabilities();
}
