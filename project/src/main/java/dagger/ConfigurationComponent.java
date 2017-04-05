package dagger;

import actions.BaseActions;
import actions.DriverActions;
import actions.DroidPhoneDriverActions;
import actions.IPhoneDriverActions;
import data.Configuration;
import driver.DriverProvider;
import driver.capabilities.BaseCapabilities;
import javax.inject.Singleton;

@Component(modules = ConfigurationModule.class)
@Singleton
public interface ConfigurationComponent {
	Configuration getConfiguration();

	void inject(BaseCapabilities capabilitiesClass);

	void inject (DriverProvider provider);

	void inject(DroidPhoneDriverActions driverActions);

	void inject(IPhoneDriverActions driverActions);
}
