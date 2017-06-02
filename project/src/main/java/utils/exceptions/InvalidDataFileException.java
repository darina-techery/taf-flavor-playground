package utils.exceptions;

public class InvalidDataFileException extends RuntimeException {
	public InvalidDataFileException(String message) {
		super(message);
	}
	public InvalidDataFileException(String message, Throwable cause) {
		super(message, cause);
	}
}
