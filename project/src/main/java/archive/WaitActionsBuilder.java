package archive;

//import utils.waiters.BaseWait;

import java.time.Duration;
import java.util.function.*;

abstract class WaitActionsBuilder<T, R> {

	public T getTestableObject() {
		return testableObject;
	}

	private T testableObject;

	public Predicate<T> getPreconditionWithTestableObject() {
		return preconditionWithTestableObject;
	}

	public BooleanSupplier getPreconditionWithoutTestableObject() {
		return preconditionWithoutTestableObject;
	}

	public Supplier<R> getMethodWithReturnWithoutTestableObject() {
		return methodWithReturnWithoutTestableObject;
	}

	public Runnable getMethodWithoutReturnWithoutTestableObject() {
		return methodWithoutReturnWithoutTestableObject;
	}

	public Function<T, R> getMethodWithReturnWithTestableObject() {
		return methodWithReturnWithTestableObject;
	}

	public Consumer<T> getMethodWithoutReturnWithTestableObject() {
		return methodWithoutReturnWithTestableObject;
	}

	public Predicate<R> getPostconditionWithResult() {
		return postconditionWithResult;
	}

	public BooleanSupplier getPostconditionWithoutResult() {
		return postconditionWithoutResult;
	}

	public Duration getDuration() {
		return duration;
	}

	public Duration getRetryInterval() {
		return retryInterval;
	}

	//Preconditions
	private Predicate<T> preconditionWithTestableObject;

	private BooleanSupplier preconditionWithoutTestableObject;


	//Actions
	private Supplier<R> methodWithReturnWithoutTestableObject;

	private Runnable methodWithoutReturnWithoutTestableObject;

	private Function<T, R> methodWithReturnWithTestableObject;

	private Consumer<T> methodWithoutReturnWithTestableObject;


	//Post-conditions
	private Predicate<R> postconditionWithResult;

	private BooleanSupplier postconditionWithoutResult;


	//Durations
	private Duration duration;

	private Duration retryInterval;


	WaitActionsBuilder() {

	}

	WaitActionsBuilder<T, R> with(T testableObject) {
		this.testableObject = testableObject;
		return this;
	}

	WaitActionsBuilder<T, R> when(Predicate<T> whenCondition) {
		this.preconditionWithTestableObject = whenCondition;
		return this;
	}

	WaitActionsBuilder<T, R> when(BooleanSupplier whenCondition) {
		this.preconditionWithoutTestableObject = whenCondition;
		return this;
	}

	WaitActionsBuilder<T, R> execute(Supplier<R> operation) {
		this.methodWithReturnWithoutTestableObject = operation;
		return this;
	}

	WaitActionsBuilder<T, R> execute(Runnable operation) {
		this.methodWithoutReturnWithoutTestableObject = operation;
		return this;
	}

	WaitActionsBuilder<T, R> execute(Function<T, R> operation) {
		this.methodWithReturnWithTestableObject = operation;
		return this;
	}

	WaitActionsBuilder<T, R> execute(Consumer<T> operation) {
		this.methodWithoutReturnWithTestableObject = operation;
		return this;
	}

	WaitActionsBuilder<T, R> until(Predicate<R> untilCondition) {
		this.postconditionWithResult = untilCondition;
		return this;
	}

	WaitActionsBuilder<T, R> until(BooleanSupplier untilCondition) {
		this.postconditionWithoutResult = untilCondition;
		return this;
	}

	WaitActionsBuilder<T, R> duration(Duration duration) {
		this.duration = duration;
		return this;
	}

	WaitActionsBuilder<T, R> withInterval(Duration retryInterval) {
		this.retryInterval = retryInterval;
		return this;
	}

}