package lab.codeinsight.backend.parser.parser.ast;

import java.nio.file.Path;

/**
 * Thin AST utility wrapper used by scanners for source-file filtering.
 */
public class AstParser {

	/**
	 * Returns true when a path points to a Java source file.
	 */
	public boolean isJavaSourcePath(Path path) {
		return path.toString().endsWith(".java");
	}
}
