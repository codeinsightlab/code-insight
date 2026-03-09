package lab.codeinsight.backend.parser.scanner.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Stream;
import lab.codeinsight.backend.parser.exception.ScannerException;
import lab.codeinsight.backend.parser.exception.SourceLoadException;
import lab.codeinsight.backend.parser.model.scan.ProjectStructure;
import lab.codeinsight.backend.parser.parser.ast.AstParser;
import lab.codeinsight.backend.parser.support.PathSupport;

/**
 * Scans filesystem structure and builds project-level Java source topology.
 */
public class ProjectStructureScanner {

	private final AstParser astParser;

	/**
	 * Creates structure scanner with AST helper dependency.
	 */
	public ProjectStructureScanner(AstParser astParser) {
		this.astParser = astParser;
	}

	/**
	 * Returns project structure with Java files, source directories and inferred
	 * base packages.
	 */
	public ProjectStructure scan(Path projectRoot) {
		Path normalizedRoot = projectRoot.toAbsolutePath().normalize();
		if (!Files.exists(normalizedRoot) || !Files.isDirectory(normalizedRoot)) {
			throw new SourceLoadException("Project path does not exist or is not a directory: " + normalizedRoot);
		}

		List<Path> javaPaths = loadJavaPaths(normalizedRoot);
		List<String> javaFiles = javaPaths.stream().map(path -> PathSupport.toRelativeUnixPath(normalizedRoot, path))
				.toList();

		TreeSet<String> sourceDirectories = new TreeSet<>();
		TreeSet<String> basePackages = new TreeSet<>();

		for (Path javaPath : javaPaths) {
			Path sourceDirectory = resolveSourceDirectory(normalizedRoot, javaPath);
			sourceDirectories.add(PathSupport.toRelativeUnixPath(normalizedRoot, sourceDirectory));
			resolveBasePackage(sourceDirectory, javaPath).ifPresent(basePackages::add);
		}

		return new ProjectStructure(normalizedRoot.toString(), javaFiles, new ArrayList<>(sourceDirectories),
				new ArrayList<>(basePackages));
	}

	/**
	 * Walks filesystem and collects included Java source paths.
	 */
	private List<Path> loadJavaPaths(Path projectRoot) {
		try (Stream<Path> paths = Files.walk(projectRoot)) {
			return paths.filter(Files::isRegularFile).filter(astParser::isJavaSourcePath).filter(this::isIncludedPath)
					.sorted(Comparator.naturalOrder()).toList();
		} catch (IOException e) {
			throw new ScannerException("Failed to scan project path: " + projectRoot, e);
		}
	}

	/**
	 * Resolves source root (`.../java`) for one Java file path.
	 */
	private Path resolveSourceDirectory(Path projectRoot, Path javaPath) {
		Path relative = projectRoot.relativize(javaPath);
		int javaIndex = PathSupport.indexOfPathElement(relative, "java");
		if (javaIndex >= 0) {
			// Preserve conventional source root like src/main/java or src/test/java.
			return projectRoot.resolve(relative.subpath(0, javaIndex + 1)).normalize();
		}
		return javaPath.getParent();
	}

	/**
	 * Resolves package name by relativizing file parent from source root.
	 */
	private Optional<String> resolveBasePackage(Path sourceDirectory, Path javaPath) {
		Path parent = javaPath.getParent();
		if (parent == null || parent.equals(sourceDirectory)) {
			return Optional.empty();
		}

		Path packagePath = sourceDirectory.relativize(parent);
		String packageName = packagePath.toString().replace('\\', '.').replace('/', '.');
		return packageName.isBlank() ? Optional.empty() : Optional.of(packageName);
	}
	/**
	 * Returns true when path should be considered in scan.
	 */
	private boolean isIncludedPath(Path path) {
		return !containsSegment(path, "target") && !containsSegment(path, "build") && !containsSegment(path, ".git");
	}

	/**
	 * Returns true when a specific segment appears in path elements.
	 */
	private boolean containsSegment(Path path, String segment) {
		for (Path part : path) {
			if (segment.equals(part.toString())) {
				return true;
			}
		}
		return false;
	}
}
