package utils.ui;

import data.Configuration;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import utils.waiters.AnyWait;
import utils.waiters.WaitTimer;
import utils.waiters.Waiter;

import java.time.Duration;
import java.util.function.BooleanSupplier;

import static io.appium.java_client.SwipeElementDirection.DOWN;
import static io.appium.java_client.SwipeElementDirection.UP;

public class Swipe {
	private final By DEFAULT_ANDROID_CONTAINER = By.xpath("//*");
	private final By DEFAULT_IOS_CONTAINER = MobileBy.className("XCUIElementTypeWindow");
	private final SwipeElementDirection DEFAULT_SWIPE_DIRECTION = UP;
	private static final int UNDEFINED_OFFSET = -1;

	private SwipeElementDirection swipeDirection;
	private MobileElement targetElement;
	private By targetElementBy;

	private MobileElement container;
	private By containerBy;

	private double offsetFromContainerBorderRatio = 0.1;
	private int offsetFromContainerBorder = UNDEFINED_OFFSET;
	private int swipeDurationInMillis = 1000;

	private BooleanSupplier stopSwipeCondition;
	private Duration timeout = WaitTimer.DEFAULT_TIMEOUT;

	public Swipe direction(SwipeElementDirection direction) {
		this.swipeDirection = direction;
		return this;
	}

	public Swipe timeout(Duration timeout) {
		if (timeout != null) {
			this.timeout = timeout;
		}
		return this;
	}

	public Swipe container(By containerBy) {
		if (containerBy != null) {
			this.containerBy = containerBy;
		}
		return this;
	}

	public Swipe container(MobileElement container) {
		if (container != null) {
			this.container = container;
		}
		return this;
	}

	public Swipe until(BooleanSupplier stopSwipeCondition) {
		this.stopSwipeCondition = stopSwipeCondition;
		return this;
	}

	public Swipe toElement(By targetElementBy) {
		this.targetElementBy = targetElementBy;
		return this;
	}

	public Swipe toElement(MobileElement targetElement) {
		this.targetElement = targetElement;
		return this;
	}

	public Swipe withOffsetFromBorderRatio(double offsetRatio) {
		this.offsetFromContainerBorderRatio = offsetRatio;
		return this;
	}

	public void swipe() {
		final boolean needToLocateElement = targetElement != null || targetElementBy != null;
		initDirection();

		AnyWait<Void, Void> scrollOperation = new AnyWait<>();
		scrollOperation.addIgnorableException(NoSuchElementException.class);
		scrollOperation.duration(timeout);
		scrollOperation.when(() -> {
			initContainer();
			initOffsetFromContainerBorder();
			return true;
		});
		scrollOperation.execute(() -> {
			if (!needToLocateElement || !isElementPresent()) {
				container.swipe(swipeDirection, offsetFromContainerBorder, offsetFromContainerBorder, swipeDurationInMillis);
			}
		});
		scrollOperation.until(() -> {
			boolean stopFlag = true;
			if (needToLocateElement) {
				stopFlag = stopFlag && isElementPresent();
			}
			if (stopSwipeCondition != null ) {
				stopFlag = stopFlag && stopSwipeCondition.getAsBoolean();
			}
			return stopFlag;
		});

		String containerDescription;
		if (container != null) {
			containerDescription = ElementDescriber.describe(container);
			if (containerDescription == null) {
				containerDescription = ElementDescriber.DEFAULT_ELEMENT_DESCRIPTION;
			}
		} else if (containerBy != null) {
			containerDescription = containerBy.toString();
		} else {
			containerDescription = "whole screen area";
		}
		StringBuilder description = new StringBuilder().append("Swipe in [")
				.append(containerDescription).append("]");
		if (needToLocateElement) {
			description.append(" to locate element");
		}
		if (stopSwipeCondition != null) {
			description.append(" until condition");
		}
		scrollOperation.describe(description.toString());
		scrollOperation.go();
	}

	private boolean isElementPresent() {
		return (targetElement != null)
			? Waiter.waitDisplayed(targetElement, Duration.ofSeconds(2))
			: Waiter.waitDisplayed(targetElementBy, Duration.ofSeconds(2));
	}

	private void initContainer() {
		if (this.container == null) {
			if (this.containerBy == null) {
				containerBy = Configuration.isAndroid() ? DEFAULT_ANDROID_CONTAINER : DEFAULT_IOS_CONTAINER;
			}
			container = Waiter.find(containerBy);
		}
	}

	private void initDirection() {
		if (this.swipeDirection == null) {
			swipeDirection = DEFAULT_SWIPE_DIRECTION;
		}
	}

	private void initOffsetFromContainerBorder() {
		if (offsetFromContainerBorder == UNDEFINED_OFFSET) {
			int fullDistance = (swipeDirection.equals(DOWN) || swipeDirection.equals(UP))
					? container.getSize().getHeight()
					: container.getSize().getWidth();
			offsetFromContainerBorder = (int) (fullDistance * offsetFromContainerBorderRatio);
		}
	}
}
