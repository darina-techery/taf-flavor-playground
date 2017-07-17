package actions;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import ru.yandex.qatools.allure.annotations.Step;
import ui.screens.BucketListScreen;
import utils.waiters.AnyWait;
import utils.waiters.Waiter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class BucketListActions extends BaseUiActions {
	BucketListScreen bucketListScreen = new BucketListScreen();

	@Override
	public void waitForScreen() {
		new Waiter(Duration.ofSeconds(30)).waitDisplayed(bucketListScreen.fldPageTitle);
	}

	public void pressAddButton() {
		Waiter waiter = new Waiter();
		waiter.click(bucketListScreen.btnAddNewItem);
	}

	@Step("Enter Bucket List name ''{0}''")
	public void enterBucketListName(String name) {
		MobileElement element = bucketListScreen.fldCreateItemInput;
		element.sendKeys(name);
		element.click();
	}

	public String generateNameForBucketList(){
		String nameOfBucketList = "BucketListForAutotests";

		LocalDateTime time = LocalDateTime.now();
		return nameOfBucketList + "_" + time.format(java.time.format.DateTimeFormatter.ofPattern("hh.mm.ss.SSS"));
	}

	@Step("Press DONE button on keyboard")
	public void pressDone() {
		Dimension size = getDriver().manage().window().getSize();
		int x = size.getWidth() - 20;
		int y = size.getHeight() - 20;
		getDriver().tap(1, x, y, 100);
		//close search
		//getDriver().navigate().back();
	}

	@Step("Find just created bucket list with name - ''{0}''")
	public MobileElement getBucketList(String bucketListName, boolean scrollIfNotFound) {

		final Waiter waiter = new Waiter();
		final AnyWait<String, MobileElement> finder = new AnyWait<>();
		finder.duration(Duration.ofSeconds(30));
		finder.with(bucketListName);
		finder.when(() -> bucketListScreen.bucketListTextEntries.size() > 0);
		finder.calculate(text -> getBucketListByText(bucketListName));
		if (scrollIfNotFound) {
			finder.until(element -> waiter.scrollDownIfElementIsNull.apply(element));
		} else {
			finder.until(Objects::nonNull);
		}
		finder.addIgnorableException(StaleElementReferenceException.class);
		finder.addIgnorableException(NoSuchElementException.class);
		finder.describe("Scroll down until element with text [" + bucketListName + "] is found");
		MobileElement result = finder.go();

		return result;
	}

	public MobileElement getBucketListByText(String bucketListName) {

		for (MobileElement elementOfBucketListTexts : bucketListScreen.bucketListTextEntries) {
			if (elementOfBucketListTexts != null && elementOfBucketListTexts.getText().trim().equals(bucketListName)) {
				return elementOfBucketListTexts;
			}
		}
 		return null;
	}

}