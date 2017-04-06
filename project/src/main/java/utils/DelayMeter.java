package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class DelayMeter {
	private static final Logger log = LogManager.getLogger(DelayMeter.class);

	public static void startMeter(String operationName) {
		if (!DelaysMap.MAP_INSTANCE.containsKey(operationName)) {
			DelaysMap.MAP_INSTANCE.put(operationName, new Stopwatch());
		}
		DelaysMap.MAP_INSTANCE.get(operationName).start();
	}

	public static void stopMeter(String operationName) {
		if (!DelaysMap.MAP_INSTANCE.containsKey(operationName)) {
			throw new NoSuchElementException("Cannot get duration of ["
					+ operationName
					+ "]: method startMeter(\""
					+ operationName
					+ "\") was not invoked");
		}
		DelaysMap.MAP_INSTANCE.get(operationName).stop();
	}

	public static long getOperationDurationNanos(String operationName) {
		return DelaysMap.MAP_INSTANCE.get(operationName).duration;
	}

	public static void reportOperationDuration(String operationName) {
		log.info(String.format("Operations [%s] took %d ns in total.",
				operationName, getOperationDurationNanos(operationName)));
	}

	public static void reportOperationDuration(String operationName, TimeUnit timeUnit) {
		long result = timeUnit.convert(getOperationDurationNanos(operationName), TimeUnit.NANOSECONDS);
		log.info(String.format("Operations [%s] took %d %s in total.", operationName, result, timeUnit));
	}

	private static class DelaysMap extends HashMap<String, Stopwatch> {
		private static final DelaysMap MAP_INSTANCE = new DelaysMap();
	}

}

class Stopwatch {
	private long start = 0;
	long duration = 0;

	void start() {
		start = System.nanoTime();
	}

	void stop() {
		duration += System.nanoTime() - start;
	}
}
