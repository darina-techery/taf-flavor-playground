package utils.exceptions;

import org.apache.logging.log4j.Logger;
import utils.log.CommonLogMessages;

import java.util.Set;
import java.util.stream.Collectors;

public interface IgnoresExceptions extends CommonLogMessages {
	Set<Class<? extends Throwable>> getIgnorableExceptions();

	default void addIgnorableException(Class<? extends Throwable> exceptionClass) {
		getIgnorableExceptions().add(exceptionClass);
	}

	default void addIgnorableExceptions(Set<Class<? extends Throwable>> exceptionClasses) {
		getIgnorableExceptions().addAll(exceptionClasses);
	}

	default boolean isIgnorable(Throwable t) {
		return getIgnorableExceptions().contains(t.getClass());
	}

	default void clearIgnorableExceptions() {
		getIgnorableExceptions().clear();
	}

	default void listIgnorableExceptions(Logger log) {
		log.trace("{}: Ignored exceptions classes: [{}]",
				this.getClass().getSimpleName(),
				getIgnorableExceptions().stream()
						.map(Class::getSimpleName)
						.collect(Collectors.joining(", ")));
	}
}
