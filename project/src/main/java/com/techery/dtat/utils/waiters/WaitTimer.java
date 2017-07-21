package com.techery.dtat.utils.waiters;

import com.techery.dtat.data.Configuration;
import org.apache.logging.log4j.LogManager;

import java.time.Duration;

public class WaitTimer {
	private long startTime;
	Duration maxDuration;
	Duration retryInterval;

	//	private static final int DEFAULT_ANDROID_TIMEOUT_SECONDS = 5;
	private static final int DEFAULT_ANDROID_TIMEOUT_SECONDS = 10;
	//	private static final int DEFAULT_IOS_TIMEOUT_SECONDS = 5;
	private static final int DEFAULT_IOS_TIMEOUT_SECONDS = 30;

	private static final Duration DEFAULT_RETRY_TIMEOUT = Duration.ofMillis(200);

	public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(Configuration.isAndroid()
			? DEFAULT_ANDROID_TIMEOUT_SECONDS
			: DEFAULT_IOS_TIMEOUT_SECONDS);

	WaitTimer() {
		maxDuration = DEFAULT_TIMEOUT;
		retryInterval = DEFAULT_RETRY_TIMEOUT;
	}

	void start() {
		startTime = System.nanoTime();
	}

	long getTimeElapsed() {
		return System.nanoTime() - startTime;
	}

	long getTimeElapsedInMillis() {
		return Duration.ofNanos(getTimeElapsed()).toMillis();
	}

	long getMaxDurationInMillis(){
		return maxDuration.toMillis();
	}

	boolean isTimeout() {
		return getTimeElapsed() >= maxDuration.toNanos();
	}

	void pause() {
		if (!isTimeout()) {
			try {
				Thread.sleep(retryInterval.toMillis());
			} catch (InterruptedException e) {
				LogManager.getLogger().error("Failed to pause current thread for retry timeout.", e);
			}
		}
	}

}
