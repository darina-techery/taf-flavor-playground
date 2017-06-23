package actions;

import ru.yandex.qatools.allure.annotations.Step;
import ui.screens.DreamTripsDetailsScreen;
import utils.exceptions.FailedTestException;
import utils.ui.SwipeHelper;
import utils.waiters.AnyWait;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class DreamTripDetailsActions extends BaseUiActions {
	protected DreamTripsDetailsScreen dreamTripsDetailsScreen = new DreamTripsDetailsScreen();
	protected abstract void addVisibleTextsFromTripDetails(Set<String> collectedTexts);

	@Step("Get all trip descriptions from Trip Details screen")
	public Set<String> getAllTextsFromTripDetails() {
		final Set<String> textsFromTripDetails = new LinkedHashSet<>();
		AnyWait<Void, LinkedHashSet> activityWait = new AnyWait<>();
		activityWait.execute(()->{
			SwipeHelper.scrollDown();
			addVisibleTextsFromTripDetails(textsFromTripDetails);
		});
		activityWait.until(() -> dreamTripsDetailsScreen.btnPostComment.isDisplayed());
		activityWait.addIgnorableException(org.openqa.selenium.NoSuchElementException.class);
		activityWait.describe("Wait until all texts collected from Trip Details");
		activityWait.duration(Duration.ofMinutes(5));
		activityWait.getLogger();
		activityWait.go();
		if (!activityWait.isSuccess()) {
			throw new FailedTestException("FAILED to scroll Details page down until Post Comment button is displayed. " +
					"Swipe might be a problem.");
		}
		return textsFromTripDetails;
	}
}
