package lab.codeinsight.backend.parser.exception;

/**
 * Thrown when scanner traversal or parsing pipeline fails unexpectedly.
 */
public class ScannerException extends RuntimeException {

	/**
	 * Creates scanner exception with message and root cause.
	 */
	public ScannerException(String message, Throwable cause) {
		super(message, cause);
	}
}
