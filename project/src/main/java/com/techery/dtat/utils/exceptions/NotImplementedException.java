package com.techery.dtat.utils.exceptions;

public class NotImplementedException extends org.apache.commons.lang3.NotImplementedException {
	private static final String MESSAGE = "Implement method %s in %s";
	private static final int STACK_TRACE_DEPTH_COUNT = 2;

	public NotImplementedException(){
		this("");
	}

	public NotImplementedException(String message) {
		super(message.isEmpty() ? "" : (message + "\n") +
				String.format(MESSAGE,
						Thread.currentThread().getStackTrace()[STACK_TRACE_DEPTH_COUNT].getMethodName(),
						Thread.currentThread().getStackTrace()[STACK_TRACE_DEPTH_COUNT].getClassName()));
	}

}
