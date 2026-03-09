package lab.codeinsight.backend.scan.dal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lab.codeinsight.backend.scan.model.report.JavaSourceFile;
import org.springframework.stereotype.Repository;

@Repository
public class JavaSourceLoader {

  private static final Pattern PACKAGE_PATTERN =
      Pattern.compile("(?m)^\\s*package\\s+([a-zA-Z_][\\w\\.]*)\\s*;");

  public List<Path> loadJavaFiles(Path projectRoot) {
    if (!Files.exists(projectRoot) || !Files.isDirectory(projectRoot)) {
      throw new IllegalArgumentException(
          "Project path does not exist or is not a directory: " + projectRoot);
    }

    try (Stream<Path> paths = Files.walk(projectRoot)) {
      return paths
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().endsWith(".java"))
          .filter(path -> !containsSegment(path, "target"))
          .filter(path -> !containsSegment(path, "build"))
          .filter(path -> !containsSegment(path, ".git"))
          .sorted(Comparator.naturalOrder())
          .toList();
    } catch (IOException e) {
      throw new RuntimeException("Failed to walk project path: " + projectRoot, e);
    }
  }

  public List<JavaSourceFile> loadJavaSources(Path projectRoot, List<String> javaFilePaths) {
    return javaFilePaths.stream().map(path -> loadJavaSource(projectRoot, path)).toList();
  }

  public JavaSourceFile loadJavaSource(Path projectRoot, String javaFilePath) {
    Path absolutePath = projectRoot.resolve(javaFilePath).normalize();
    if (!Files.exists(absolutePath) || !Files.isRegularFile(absolutePath)) {
      throw new IllegalArgumentException("Java file does not exist: " + absolutePath);
    }

    try {
      String sourceCode = Files.readString(absolutePath);
      String packageName = extractPackageName(sourceCode);
      String normalizedFilePath = javaFilePath.replace('\\', '/');
      return new JavaSourceFile(normalizedFilePath, packageName, sourceCode);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read Java file: " + absolutePath, e);
    }
  }

  private String extractPackageName(String sourceCode) {
    Matcher matcher = PACKAGE_PATTERN.matcher(sourceCode);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return "";
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
