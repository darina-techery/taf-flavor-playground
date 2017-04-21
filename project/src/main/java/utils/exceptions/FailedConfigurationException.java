package utils.exceptions;

public class FailedConfigurationException extends RuntimeException {
	private static final String INTERRUPTED_TEST_MESSAGE
			= "Test setup was interrupted with reason:\n - ";
	private static final String INTERRUPTED_BY_EXCEPTION_TEST_MESSAGE
			= "Test setup was interrupted by exception with reason:\n - ";
	public FailedConfigurationException(String message){
		super(INTERRUPTED_TEST_MESSAGE + "[" + message + "]");
	}

	public FailedConfigurationException(Throwable t, String message){
		super(INTERRUPTED_BY_EXCEPTION_TEST_MESSAGE + "[" + message + "]", t);
	}
}
