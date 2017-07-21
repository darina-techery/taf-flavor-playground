package com.techery.dtat.utils.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface LogProvider {
	default Logger getLogger() {
		return LogManager.getLogger(this.getClass());
	}
}
