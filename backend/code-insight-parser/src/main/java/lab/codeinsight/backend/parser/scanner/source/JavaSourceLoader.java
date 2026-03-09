package lab.codeinsight.backend.parser.scanner.source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lab.codeinsight.backend.parser.exception.SourceLoadException;
import lab.codeinsight.backend.parser.model.scan.JavaSourceFile;

/**
 * Loads Java source files from root-relative paths and extracts package
 * metadata.
 */
public class JavaSourceLoader {

	private static final Pattern PACKAGE_PATTERN = Pattern.compile("(?m)^\\s*package\\s+([a-zA-Z_][\\w\\.]*)\\s*;");

	/**
	 * Loads and parses all provided Java file paths.
	 */
	public List<JavaSourceFile> load(Path projectRoot, List<String> javaFilePaths) {
		return javaFilePaths.stream().map(path -> loadOne(projectRoot, path)).toList();
	}

	/**
	 * Loads one Java source file and extracts package metadata.
	 */
	private JavaSourceFile loadOne(Path projectRoot, String javaFilePath) {
		Path absolutePath = projectRoot.resolve(javaFilePath).normalize();
		if (!Files.exists(absolutePath) || !Files.isRegularFile(absolutePath)) {
			throw new SourceLoadException("Java file does not exist: " + absolutePath);
		}

		try {
			String sourceCode = Files.readString(absolutePath);
			String packageName = extractPackageName(sourceCode);
			return new JavaSourceFile(javaFilePath.replace('\\', '/'), packageName, sourceCode);
		} catch (IOException e) {
			throw new SourceLoadException("Failed to read Java file: " + absolutePath);
		}
	}

	/**
	 * Extracts package declaration from Java source text.
	 */
	private String extractPackageName(String sourceCode) {
		Matcher matcher = PACKAGE_PATTERN.matcher(sourceCode);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
}
