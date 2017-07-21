package com.techery.dtat.utils;

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
