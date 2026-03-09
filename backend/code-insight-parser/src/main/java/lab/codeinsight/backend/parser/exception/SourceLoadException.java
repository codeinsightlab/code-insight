package lab.codeinsight.backend.parser.exception;

/**
 * Thrown when project path or Java source file loading fails.
 */
public class SourceLoadException extends RuntimeException {

	/**
	 * Creates source load exception with failure message.
	 */
	public SourceLoadException(String message) {
		super(message);
	}
}
