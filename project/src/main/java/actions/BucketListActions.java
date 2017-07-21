package actions;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import ru.yandex.qatools.allure.annotations.Step;
import ui.screens.BucketListScreen;
import utils.StringHelper;
import utils.waiters.AnyWait;
import utils.waiters.Waiter;

import java.time.Duration;
import java.util.Objects;

public abstract class BucketListActions extends BaseUiActions {
	BucketListScreen bucketListScreen = new BucketListScreen();

	@Override
	public void waitForScreen() {
		new Waiter(Duration.ofSeconds(30)).waitDisplayed(bucketListScreen.fldPageTitle);
	}

	public void pressAddButton() {
		Waiter waiter = new Waiter();
		waiter.click(bucketListScreen.btnAddNewItem);
	}

	@Step("Enter Bucket Item name ''{0}''")
	public void enterBucketItemName(String name) {
		MobileElement element = bucketListScreen.fldNewBucketItemName;
		element.click();
		element.sendKeys(name);
		element.click();
	}

	public String generateNameForBucketItem(String testName){
		return testName + StringHelper.getTimestampSuffix();
	}

	@Step("Press DONE button on keyboard")
	public void pressDone() {}

	@Step("Find just created bucket item with name - ''{0}''")
	public MobileElement getBucketItem(String bucketListName, boolean scrollIfNotFound) {

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
