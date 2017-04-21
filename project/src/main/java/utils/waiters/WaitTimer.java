package utils.waiters;

import org.apache.logging.log4j.LogManager;

import java.time.Duration;

class WaitTimer {
	private long startTime;
	Duration maxDuration;
	Duration retryInterval;

	private static final Duration DEFAULT_RETRY_TIMEOUT = Duration.ofMillis(200);
	WaitTimer(Duration duration) {
		maxDuration = duration;
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
