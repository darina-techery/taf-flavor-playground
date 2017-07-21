package com.techery.dtat.ui.components;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebElement;

@iOSFindBy(className = "XCUIElementTypeCell")
public class DreamTripsListItem extends Widget {
	protected DreamTripsListItem(WebElement element) {
		super(element);
	}

	@iOSFindBy(accessibility = "tripDetails_tripLocation")
	public MobileElement location;

	@iOSFindBy(accessibility = "tripDetails_addToBLButton")
	public MobileElement addToBucketListButton;

	@iOSFindBy(accessibility = "tripDetails_likeButton")
	public MobileElement likeButton;

	@iOSFindBy(accessibility = "tripDetails_tripPrice")
	public MobileElement price;

	@iOSFindBy(accessibility = "tripDetails_tripPoints")
	public MobileElement tripPoints;

	@iOSFindBy(accessibility = "tripDetails_tripName")
	public MobileElement tripName;

	@iOSFindBy(accessibility = "tripDetails_tripDates")
	public MobileElement dates;
}
