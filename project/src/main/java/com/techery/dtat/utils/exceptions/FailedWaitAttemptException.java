package com.techery.dtat.utils.exceptions;

public class FailedWaitAttemptException extends RuntimeException {
	public FailedWaitAttemptException(String message) {
		super(message);
	}
	public FailedWaitAttemptException(String message, Exception cause) {
		super(message, cause);
	}

	public String toString() {
		String message = this.getMessage();
		if (this.getCause() != null) {
			Throwable cause = this.getCause();
			message += ". Caused by " + cause.getClass() + ": " + cause.getMessage();
		}
		return message;
	}
}
