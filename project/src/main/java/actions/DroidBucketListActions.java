package actions;

import data.Configuration;
import org.openqa.selenium.Dimension;
import ru.yandex.qatools.allure.annotations.Step;


public class DroidBucketListActions extends BucketListActions {

	@Override
	@Step("Press DONE button on keyboard")
	public void pressDone() {
		if (Configuration.isAndroidPhone()) {
			Dimension size = getDriver().manage().window().getSize();
			int x = size.getWidth() - 20;
			int y = size.getHeight() - 20;
			getDriver().tap(1, x, y, 100);
		} else {
			Dimension size = getDriver().manage().window().getSize();
			int x = size.getWidth() - 50;
			int y = size.getHeight() / 2 + size.getHeight() / 5;
			getDriver().tap(1, x, y, 100);
		}
	}

}
