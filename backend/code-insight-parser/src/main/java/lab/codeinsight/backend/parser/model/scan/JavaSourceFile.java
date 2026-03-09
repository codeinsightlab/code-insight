package lab.codeinsight.backend.parser.model.scan;

/**
 * Loaded Java source with normalized relative file path and package metadata.
 */
public record JavaSourceFile(String filePath, String packageName, String sourceCode) {
}
