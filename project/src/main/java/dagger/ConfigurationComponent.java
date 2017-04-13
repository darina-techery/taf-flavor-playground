package dagger;

import actions.DroidDriverActions;
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

	void inject(DroidDriverActions driverActions);

	void inject(IPhoneDriverActions driverActions);
}
