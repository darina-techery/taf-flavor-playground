package utils.exceptions;

import utils.log.CommonLogMessages;

public class FailedConfigurationException extends RuntimeException implements CommonLogMessages {
	private static final String INTERRUPTED_TEST_MESSAGE
			= "Test setup was interrupted with reason:\n - ";
	private static final String INTERRUPTED_BY_EXCEPTION_TEST_MESSAGE
			= "Test setup was interrupted by exception with reason:\n - ";
	public FailedConfigurationException(String message, String... parameters){
		super(INTERRUPTED_TEST_MESSAGE + "[" + CommonLogMessages.formatMessage(message, parameters) + "]");
	}

	public FailedConfigurationException(Throwable t, String message, String... parameters){
		super(INTERRUPTED_BY_EXCEPTION_TEST_MESSAGE + "[" + CommonLogMessages.formatMessage(message, parameters) + "]", t);
	}
}
