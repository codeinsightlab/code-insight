package lab.codeinsight.backend.parser.exception;

/** Thrown when scanner traversal or parsing pipeline fails unexpectedly. */
public class ScannerException extends RuntimeException {

  public ScannerException(String message, Throwable cause) {
    super(message, cause);
  }
}
