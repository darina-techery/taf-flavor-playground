package actions;

import org.openqa.selenium.remote.RemoteWebElement;
import ru.yandex.qatools.allure.annotations.Step;
import ui.screens.DreamTripsDetailsScreen;
import ui.screens.DreamTripsScreen;
import utils.exceptions.FailedTestException;
import utils.ui.SwipeHelper;
import utils.waiters.AnyWait;
import utils.waiters.Waiter;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class DroidDreamTripsActions extends DreamTripsActions {
    DreamTripsScreen dreamTripsScreen = new DreamTripsScreen();
    DreamTripsDetailsScreen dreamTripsDetailsScreen = new DreamTripsDetailsScreen();

    LinkedHashSet<String> textsFromTripDetails = new LinkedHashSet<>();

    @Override
    @Step("Search trip by name ''{0}''")
    public void searchTrip(String tripName) {
        DreamTripsScreen dreamTripsScreen = new DreamTripsScreen();
        Waiter.click(dreamTripsScreen.btnSearch);
        Waiter.setText(dreamTripsScreen.fldSearch, tripName);
    }

    @Step("Open first trip in list")
    public void openFirstTripInList() {
        Waiter.click(dreamTripsScreen.dreamTripFirstItem);
        Waiter.isDisplayed(dreamTripsDetailsScreen.imgPicOfTrip);
    }

    @Step("Get all texts from TripDetails")
    public LinkedHashSet<String> getAllExpandableTexts() {
		AnyWait<Void, LinkedHashSet> activityWait = new AnyWait<>();
		activityWait.execute(()->{
		    SwipeHelper.scrollDown();
            GetTextsFromDetails();
		});
		activityWait.until(() -> dreamTripsDetailsScreen.btnPostComment.isDisplayed());
        activityWait.addIgnorableException(org.openqa.selenium.NoSuchElementException.class);
		activityWait.describe("Wait until all texts collected from Trip Details");
        activityWait.duration(Duration.ofMinutes(5));
        activityWait.getLogger();
		activityWait.go();
		if (!activityWait.isSuccess()) {
			throw new FailedTestException("FAILED to wait will POST button. Maybe there is problem with swipe");
		}
		return textsFromTripDetails;
    }

    @Step("Get all text from visible expandable text elements")
    private LinkedHashSet<String> GetTextsFromDetails() {
        try {
            textsFromTripDetails.addAll(dreamTripsDetailsScreen.listLongDescriptionOfTrip.stream()
                    .map(RemoteWebElement::getText)
                    .collect(Collectors.toList()));
        } catch (org.openqa.selenium.NoSuchElementException e){  }
        return textsFromTripDetails;
    }

}
