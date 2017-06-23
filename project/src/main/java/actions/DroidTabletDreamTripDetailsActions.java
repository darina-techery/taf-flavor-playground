package actions;

import ru.yandex.qatools.allure.annotations.Step;
import utils.waiters.Waiter;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DroidTabletDreamTripDetailsActions extends DreamTripDetailsActions {
	@Step("Get all text from visible expandable text elements")
	protected void addVisibleTextsFromTripDetails(Set<String> collectedTexts) {
		collectedTexts.addAll(dreamTripsDetailsScreen.listLongDescriptionOfTrip.stream()
				.map(Waiter::getText)
				.filter(Objects::nonNull)
				.collect(Collectors.toList()));
	}
}
