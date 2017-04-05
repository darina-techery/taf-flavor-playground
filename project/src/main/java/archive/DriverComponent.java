package archive;

import dagger.Component;
import driver.DriverModule;

import javax.inject.Singleton;

@Component(modules = {DriverModule.class})
@Singleton
public interface DriverComponent {
//	AppiumDriver<MobileElement> getDriver();

}
