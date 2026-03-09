package lab.codeinsight.backend.parser.exception;

/** Thrown when project path or Java source file loading fails. */
public class SourceLoadException extends RuntimeException {

  public SourceLoadException(String message) {
    super(message);
  }
}
