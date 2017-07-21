package com.techery.dtat.utils.ui;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.data.Platform;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import com.techery.dtat.utils.waiters.Waiter;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Finder {
	private Map<Platform, By> locators = new HashMap<>();
	private Waiter elementWaiter = new Waiter(Duration.ofSeconds(1));

	public Finder android(By locator) {
		locators.put(Platform.ANDROID_PHONE, locator);
		locators.put(Platform.ANDROID_TABLET, locator);
		return this;
	}

	public Finder ios(By locator) {
		locators.put(Platform.IPHONE, locator);
		locators.put(Platform.IPAD, locator);
		return this;
	}

	public Finder androidPhone(By locator) {
		locators.put(Platform.ANDROID_PHONE, locator);
		return this;
	}

	public Finder androidTablet(By locator) {
		locators.put(Platform.ANDROID_TABLET, locator);
		return this;
	}

	public Finder iPhone(By locator) {
		locators.put(Platform.IPHONE, locator);
		return this;
	}

	public Finder iPad(By locator) {
		locators.put(Platform.IPAD, locator);
		return this;
	}

	public By getBy() {
		By locator = locators.get(Configuration.getParameters().platform);
		if (locator == null) {
			throw new NullPointerException("Locator for platform "+Configuration.getParameters().platform
					+ " not specified.");
		}
		return locator;
	}

	public MobileElement find() {
		By locator = getBy();
		return elementWaiter.find(locator);
	}

	public List<MobileElement> findAll() {
		By locator = getBy();
		return elementWaiter.findAll(locator);
	}

	public MobileElement find(MobileElement parent) {
		By locator = getBy();
		return elementWaiter.find(locator, parent);
	}

	public List<MobileElement> findAll(MobileElement parent) {
		By locator = getBy();
		return elementWaiter.findAll(locator, parent);
	}
}
