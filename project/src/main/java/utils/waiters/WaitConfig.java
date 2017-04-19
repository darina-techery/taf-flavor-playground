package utils.waiters;

import driver.DriverProvider;

import java.time.Duration;

public final class WaitConfig {
	boolean failOnTimeout;
	Duration duration;

	private static final int DEFAULT_ANDROID_TIMEOUT_SECONDS = 5;
	//	private static final int DEFAULT_ANDROID_TIMEOUT_SECONDS = 10;
	private static final int DEFAULT_IOS_TIMEOUT_SECONDS = 5;
	//	private static final int DEFAULT_IOS_TIMEOUT_SECONDS = 30;

	private WaitConfig() {
		duration = Duration.ofSeconds(DriverProvider.isAndroid()
				? DEFAULT_ANDROID_TIMEOUT_SECONDS
				: DEFAULT_IOS_TIMEOUT_SECONDS);
		failOnTimeout = false;
	}

	public WaitConfig failOnTimeout(boolean failOnTimeout) {
		this.failOnTimeout = failOnTimeout;
		return this;
	}

	public WaitConfig duration(Duration maxDuration) {
		this.duration = maxDuration;
		return this;
	}

	public static WaitConfig get() {
		return new WaitConfig();
	}

}


