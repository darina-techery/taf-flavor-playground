package com.techery.dtat.utils.exceptions;

import com.techery.dtat.utils.log.CommonLogMessages;

public class FailedTestException extends RuntimeException implements CommonLogMessages {
	private static final String INTERRUPTED_TEST_MESSAGE
			= "Test execution was interrupted with reason:\n - ";
	private static final String INTERRUPTED_BY_EXCEPTION_TEST_MESSAGE
			= "Test execution was interrupted by exception with reason:\n - ";
	public FailedTestException(String message){
		super(INTERRUPTED_TEST_MESSAGE + "[" + message + "]");
	}

	public FailedTestException(String message, Throwable t){
		super(INTERRUPTED_BY_EXCEPTION_TEST_MESSAGE + "[" + message + "]", t);
	}
}
