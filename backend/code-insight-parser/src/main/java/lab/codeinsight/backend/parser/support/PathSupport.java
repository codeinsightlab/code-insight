package lab.codeinsight.backend.parser.support;

import java.nio.file.Path;

/** Shared path helper methods for cross-platform normalization. */
public final class PathSupport {

  private PathSupport() {}

  /** Converts a root-relative path into Unix-style separator format. */
  public static String toRelativeUnixPath(Path root, Path path) {
    return root.relativize(path).toString().replace('\\', '/');
  }

  /** Finds index of a path segment, returns -1 when segment does not exist. */
  public static int indexOfPathElement(Path path, String segment) {
    for (int i = 0; i < path.getNameCount(); i++) {
      if (segment.equals(path.getName(i).toString())) {
        return i;
      }
    }
    return -1;
  }
}
