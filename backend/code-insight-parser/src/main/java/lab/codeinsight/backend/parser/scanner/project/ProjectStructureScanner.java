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

public class ProjectStructureScanner {

  private final AstParser astParser;

  public ProjectStructureScanner(AstParser astParser) {
    this.astParser = astParser;
  }

  public ProjectStructure scan(Path projectRoot) {
    Path normalizedRoot = projectRoot.toAbsolutePath().normalize();
    if (!Files.exists(normalizedRoot) || !Files.isDirectory(normalizedRoot)) {
      throw new SourceLoadException(
          "Project path does not exist or is not a directory: " + normalizedRoot);
    }

    List<Path> javaPaths = loadJavaPaths(normalizedRoot);
    List<String> javaFiles =
        javaPaths.stream()
            .map(path -> PathSupport.toRelativeUnixPath(normalizedRoot, path))
            .toList();

    TreeSet<String> sourceDirectories = new TreeSet<>();
    TreeSet<String> basePackages = new TreeSet<>();

    for (Path javaPath : javaPaths) {
      Path sourceDirectory = resolveSourceDirectory(normalizedRoot, javaPath);
      sourceDirectories.add(PathSupport.toRelativeUnixPath(normalizedRoot, sourceDirectory));
      resolveBasePackage(sourceDirectory, javaPath).ifPresent(basePackages::add);
    }

    return new ProjectStructure(
        normalizedRoot.toString(),
        javaFiles,
        new ArrayList<>(sourceDirectories),
        new ArrayList<>(basePackages));
  }

  private List<Path> loadJavaPaths(Path projectRoot) {
    try (Stream<Path> paths = Files.walk(projectRoot)) {
      return paths
          .filter(Files::isRegularFile)
          .filter(astParser::isJavaSourcePath)
          .filter(path -> !containsSegment(path, "target"))
          .filter(path -> !containsSegment(path, "build"))
          .filter(path -> !containsSegment(path, ".git"))
          .sorted(Comparator.naturalOrder())
          .toList();
    } catch (IOException e) {
      throw new ScannerException("Failed to scan project path: " + projectRoot, e);
    }
  }

  private Path resolveSourceDirectory(Path projectRoot, Path javaPath) {
    Path relative = projectRoot.relativize(javaPath);
    int javaIndex = PathSupport.indexOfPathElement(relative, "java");
    if (javaIndex >= 0) {
      return projectRoot.resolve(relative.subpath(0, javaIndex + 1)).normalize();
    }
    return javaPath.getParent();
  }

  private Optional<String> resolveBasePackage(Path sourceDirectory, Path javaPath) {
    Path parent = javaPath.getParent();
    if (parent == null || parent.equals(sourceDirectory)) {
      return Optional.empty();
    }

    Path packagePath = sourceDirectory.relativize(parent);
    String packageName = packagePath.toString().replace('\\', '.').replace('/', '.');
    return packageName.isBlank() ? Optional.empty() : Optional.of(packageName);
  }

  private boolean containsSegment(Path path, String segment) {
    for (Path part : path) {
      if (segment.equals(part.toString())) {
        return true;
      }
    }
    return false;
  }
}
