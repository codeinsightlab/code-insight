package lab.codeinsight.backend.scan.main;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lab.codeinsight.backend.scan.dal.JavaSourceLoader;
import lab.codeinsight.backend.scan.model.report.ProjectStructure;
import org.springframework.stereotype.Service;

/**
 * Scans project filesystem and extracts source topology metadata.
 */
@Service
public class ProjectStructureScanner {

	private final JavaSourceLoader javaSourceLoader;

	/**
	 * Creates structure scanner with Java source loader dependency.
	 */
	public ProjectStructureScanner(JavaSourceLoader javaSourceLoader) {
		this.javaSourceLoader = javaSourceLoader;
	}

	/**
	 * Builds project structure for one project path.
	 */
	public ProjectStructure scan(String projectPath) {
		Path projectRoot = Path.of(projectPath).toAbsolutePath().normalize();
		List<Path> javaPaths = javaSourceLoader.loadJavaFiles(projectRoot);

		List<String> javaFiles = javaPaths.stream().map(path -> toRelative(projectRoot, path))
				.collect(Collectors.toList());

		TreeSet<String> sourceDirectories = new TreeSet<>();
		TreeSet<String> basePackages = new TreeSet<>();

		for (Path javaPath : javaPaths) {
			Path sourceDirectory = resolveSourceDirectory(projectRoot, javaPath);
			sourceDirectories.add(toRelative(projectRoot, sourceDirectory));

			resolveBasePackage(sourceDirectory, javaPath).ifPresent(basePackages::add);
		}

		return new ProjectStructure(projectRoot.toString(), javaFiles, new ArrayList<>(sourceDirectories),
				new ArrayList<>(basePackages));
	}

	/**
	 * Resolves source root directory from a Java file path.
	 */
	private Path resolveSourceDirectory(Path projectRoot, Path javaPath) {
		Path relative = projectRoot.relativize(javaPath);
		int javaIndex = indexOfPathElement(relative, "java");
		if (javaIndex >= 0) {
			return projectRoot.resolve(relative.subpath(0, javaIndex + 1)).normalize();
		}
		return javaPath.getParent();
	}

	/**
	 * Resolves package name from source root and Java file location.
	 */
	private java.util.Optional<String> resolveBasePackage(Path sourceDirectory, Path javaPath) {
		Path parent = javaPath.getParent();
		if (parent == null || parent.equals(sourceDirectory)) {
			return java.util.Optional.empty();
		}

		Path packagePath = sourceDirectory.relativize(parent);
		String packageName = packagePath.toString().replace('\\', '.').replace('/', '.');
		if (packageName.isBlank()) {
			return java.util.Optional.empty();
		}
		return java.util.Optional.of(packageName);
	}

	/**
	 * Finds index of a path segment, or -1 when absent.
	 */
	private int indexOfPathElement(Path path, String segment) {
		for (int i = 0; i < path.getNameCount(); i++) {
			if (segment.equals(path.getName(i).toString())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Converts absolute path into root-relative Unix style path string.
	 */
	private String toRelative(Path root, Path path) {
		return root.relativize(path).toString().replace('\\', '/');
	}
}
