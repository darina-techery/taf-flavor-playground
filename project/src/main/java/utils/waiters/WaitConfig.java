package utils.waiters;

import data.Configuration;

import java.time.Duration;

public final class WaitConfig {
	boolean failOnTimeout;
	Duration duration;
	Duration retryTimeout;

//	private static final int DEFAULT_ANDROID_TIMEOUT_SECONDS = 5;
		private static final int DEFAULT_ANDROID_TIMEOUT_SECONDS = 10;
//	private static final int DEFAULT_IOS_TIMEOUT_SECONDS = 5;
		private static final int DEFAULT_IOS_TIMEOUT_SECONDS = 30;

	private WaitConfig() {
		duration = Duration.ofSeconds(Configuration.isAndroid()
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

	public WaitConfig retryIn(Duration retryTimeout) {
		this.retryTimeout = retryTimeout;
		return this;
	}

	public static WaitConfig get() {
		return new WaitConfig();
	}

}


