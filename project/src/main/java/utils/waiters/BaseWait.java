package utils.waiters;

import org.apache.logging.log4j.Logger;
import utils.exceptions.FailedTestException;
import utils.exceptions.IgnoresExceptions;
import utils.log.CommonLogMessages;
import utils.log.LogProvider;
import utils.ui.Screenshot;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;
import java.util.function.*;

abstract class BaseWait<T, R> implements IgnoresExceptions, LogProvider {
	protected Logger log = getLogger();
	private WaitTimer timer;
	private WaitLogger waitLogger;

	private Set<Class<? extends Throwable>> ignoredExceptions;

	protected T testableObject;

	protected WaitConfig waitConfig;

	private R result;

	private final String waitClassName;

//	private boolean failOnTimeout = false;
	private Boolean success;

	private static final String OUTPUT_TEMPLATE = "\n\t- %1$-9s [%2$s]";

	BaseWait() {
		ignoredExceptions = new HashSet<>();
		waitLogger = new WaitLogger();
		waitClassName = this.getClass().getSimpleName();
	}

	public BaseWait<T, R> describe(String actionDescription) {
		this.actionDescription = actionDescription;
		return this;
	}

	//Preconditions
	protected Predicate<T> preconditionWithTestableObject;

	protected BooleanSupplier preconditionWithoutTestableObject;

	//Operations
	private Supplier<R> operationWithReturnWithoutTestableObject;

	private Runnable operationWithoutReturnWithoutTestableObject;

	private Function<T, R> operationWithReturnWithTestableObject;

	private Consumer<T> operationWithoutReturnWithTestableObject;

	//Post-conditions
	private Predicate<R> postconditionWithResult;

	private BooleanSupplier postconditionWithoutResult;

	public BaseWait<T, R> with(T testableObject) {
		this.testableObject = testableObject;
		return this;
	}

	public BaseWait<T, R> config(WaitConfig config) {
		this.waitConfig = config;
		return this;
	}

	public BaseWait<T, R> when(Predicate<T> whenCondition) {
		this.preconditionWithTestableObject = whenCondition;
		return this;
	}

	public BaseWait<T, R> when(BooleanSupplier whenCondition) {
		this.preconditionWithoutTestableObject = whenCondition;
		return this;
	}

	public BaseWait<T, R> calculate(Supplier<R> operation) {
		this.operationWithReturnWithoutTestableObject = operation;
		return this;
	}

	public BaseWait<T, R> execute(Runnable operation) {
		this.operationWithoutReturnWithoutTestableObject = operation;
		return this;
	}

	public BaseWait<T, R> calculate(Function<T, R> operation) {
		this.operationWithReturnWithTestableObject = operation;
		return this;
	}

	public BaseWait<T, R> execute(Consumer<T> operation) {
		this.operationWithoutReturnWithTestableObject = operation;
		return this;
	}

	public BaseWait<T, R> until(Predicate<R> untilCondition) {
		this.postconditionWithResult = untilCondition;
		return this;
	}

	public BaseWait<T, R> until(BooleanSupplier untilCondition) {
		this.postconditionWithoutResult = untilCondition;
		return this;
	}

	public BaseWait<T, R> withInterval(Duration retryInterval) {
		timer.retryInterval = retryInterval;
		return this;
	}

	protected abstract String describeTestableObject();

	@Override
	public Set<Class<? extends Throwable>> getIgnorableExceptions() {
		return ignoredExceptions;
	}

	@Nullable
	public R go() {
		new WaitValidator().validateConditions();
		if (waitConfig == null) {
			waitConfig = WaitConfig.get();
		}
		timer = new WaitTimer(waitConfig.duration);

		result = null;
		success = false;
		timer.start();
		waitLogger.logStart();

		while (!success && !timer.isTimeout()) {
			result = runOperationsChain();
			if (success) {
				waitLogger.logSuccess();
			} else {
				waitLogger.logRetry();
				timer.pause();
			}
		}
		if (!success) {
			waitLogger.logFailure();
		}
		return result();
	}

	@Nullable
	public R result() {
		//return null explicitly to avoid NPE
		return Objects.isNull(result) ? null : result;
	}

	public Boolean isSuccess() {
		if (success == null) {
			throw new RuntimeException("Success flag was not initialized. Call go() to calculate predefined instructions.");
		}
		return success;
	}

	public R prepare(T testableObject) {
		with(testableObject);
		setDefaultPrecondition();
		calculate(() -> (R) testableObject);
		return this.go();
	}

	protected abstract void setDefaultPrecondition();

	@Nullable
	private R runOperationsChain() {
		R result = null;
		success = true;

		try {
			//Test preconditions
			if (preconditionWithTestableObject != null) {
				success = preconditionWithTestableObject.test(testableObject);
			} else if (preconditionWithoutTestableObject != null) {
				success = preconditionWithoutTestableObject.getAsBoolean();
			}
			if (!success) {
				return null;
			}

			//Execute operations
			if (operationWithoutReturnWithoutTestableObject != null) {
				operationWithoutReturnWithoutTestableObject.run();
			} else if (operationWithoutReturnWithTestableObject != null) {
				operationWithoutReturnWithTestableObject.accept(testableObject);
			} else if (operationWithReturnWithoutTestableObject != null) {
				result = operationWithReturnWithoutTestableObject.get();
			} else if (operationWithReturnWithTestableObject != null) {
				result = operationWithReturnWithTestableObject.apply(testableObject);
			}

			//Test post-conditions
			if (postconditionWithResult == null && postconditionWithoutResult == null) {
				success = true;
			} else if (postconditionWithoutResult != null) {
				success = postconditionWithoutResult.getAsBoolean();
			} else {
				success = postconditionWithResult.test(result);
			}
		}
		catch(Throwable t) {
			if (!isIgnorable(t)) {
				log.error("Caught an exception not listed in ignored exceptions classes: [{}]", t.getClass());
				listIgnorableExceptions(log);
				throw t;
			}
			success = false;
		}
		return result;
	}


	String actionDescription;

	void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	private String describe() {
		String description = waitClassName + String.format(OUTPUT_TEMPLATE, "Object:", describeTestableObject());
		String placeWhereWaitWasCalled = describeWaitCallPosition();
		if (!placeWhereWaitWasCalled.isEmpty()) {
			description += String.format(OUTPUT_TEMPLATE, "From:", placeWhereWaitWasCalled);
		}
		if (actionDescription != null) {
			description += String.format(OUTPUT_TEMPLATE, "Action: ", actionDescription);
		}
		return description;
	}

	private String describeWaitCallPosition(){
		String description = "";
		StackTraceElement placeWhereWaitWasCalled = getWaitCallPositionInStackTrace();
		if (placeWhereWaitWasCalled != null){
			String clickableLink = getFileName(placeWhereWaitWasCalled);
			String methodName = getMethodName(placeWhereWaitWasCalled);
			description = String.format("Method [%s] in %s", methodName, clickableLink);
		}
		return description;
	}

	private StackTraceElement getWaitCallPositionInStackTrace(){
		StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
		List<Class> classNamesToIgnoreInStackTrace = Arrays.asList(
				WaitLogger.class, WaitDescriber.class,
				DefaultWaitSettings.class, WaitValidator.class,
				Waiter.class);
		StackTraceElement placeWhereWaitWasCalled = null;
		for (int i=1; i<traceElements.length; i++) {
			try {
				Class activeClass = Class.forName(traceElements[i].getClassName());
				if (!classNamesToIgnoreInStackTrace.contains(activeClass)
						&& !BaseWait.class.isAssignableFrom(activeClass)) {
					placeWhereWaitWasCalled = traceElements[i];
					break;
				}
			} catch (ClassNotFoundException ignored) {
			}
		}
		return placeWhereWaitWasCalled;
	}

	private class WaitLogger {
		private void logStart() {
			log.debug("Start " + describe());
		}

		private void logRetry() {
			log.debug(String.format("( %d / %d ms passed ) Retry: %s",
					timer.getTimeElapsedInMillis(),
					timer.getMaxDurationInMillis(),
					describe()));
		}

		private void logSuccess() {
			log.debug(String.format("Success in %d ms: %s",
					timer.getTimeElapsedInMillis(),
					describe()));
		}

		private void logWarning() {
			StackTraceElement placeWhereWaitWasCalled = getWaitCallPositionInStackTrace();
			String failedMethodName = getMethodName(placeWhereWaitWasCalled);
			log.warn(String.format("For %s: [%s] failed to succeed in %d ms.",
					describe(),
					waitClassName,
					timer.getTimeElapsedInMillis()));
			Screenshot.getScreenshotOnTimeout(waitClassName + "_" + failedMethodName);
		}

		private void logFailure() {
			logWarning();
			if (waitConfig.failOnTimeout) {
				String errorMessage = waitClassName
						+ " failed"
						+ (actionDescription == null ? "" : " to [" + actionDescription + "]")
						+ " in " + describeWaitCallPosition();
				throw new FailedTestException(errorMessage);
			}
		}
	}

	private class WaitValidator {
		private int totalConditionsProvided = 0;
		private void validateConditions() {
			checkConditionsByType("precondition",
					preconditionWithoutTestableObject, preconditionWithTestableObject);
			checkConditionsByType("operation",
					operationWithoutReturnWithoutTestableObject, operationWithoutReturnWithTestableObject,
					operationWithReturnWithoutTestableObject, operationWithReturnWithTestableObject);
			checkConditionsByType("post-condition",
					postconditionWithoutResult, postconditionWithoutResult);
			if (totalConditionsProvided == 0) {
				throw new IllegalArgumentException("No pre- or post-conditions or operations was provided for "
						+waitClassName);
			}
			checkIfTestableObjectIsProvided();
			checkIfResultIsProvidedForPostcondition();
		}

		private void checkConditionsByType(String conditionType, Object... conditions) {
			int counter = 0;
			for (Object condition : conditions) {
				if (condition != null) {
					counter++;
				}
			}
			totalConditionsProvided += counter;
			if (counter > 1) {
				throw new IllegalArgumentException("More than 1 condition was specified as " + conditionType + ".");
			}
		}

		private void checkIfTestableObjectIsProvided() {
			boolean noTestableObject = testableObject == null;
			if (noTestableObject
					&& (preconditionWithTestableObject != null
					|| operationWithoutReturnWithTestableObject != null
					|| operationWithReturnWithTestableObject != null)) {
				throw new IllegalArgumentException("No testable object was provided for " + waitClassName
						+ ". Use precondition/operation without input parameter or provide a testable object "
						+ "using method with(...)");

			}
		}

		private void checkIfResultIsProvidedForPostcondition() {
			boolean noResultExpected = operationWithoutReturnWithoutTestableObject != null
					|| operationWithoutReturnWithTestableObject != null;
			if (noResultExpected && postconditionWithResult != null) {
				throw new IllegalArgumentException("Post-condition checks operation result, but operation returns none."
						+ "Use post-condition without input parameter or add return ... statement to operation.");
			}
		}
	}

	private class WaitDescriber implements CommonLogMessages {

	}

}

