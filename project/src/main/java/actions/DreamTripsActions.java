package actions;

import java.util.LinkedHashSet;

public abstract class DreamTripsActions extends BaseUiActions {

    public abstract void searchTrip(String tripName);

    public abstract void openFirstTripInList();

    public abstract LinkedHashSet<String> getAllExpandableTexts();
}
