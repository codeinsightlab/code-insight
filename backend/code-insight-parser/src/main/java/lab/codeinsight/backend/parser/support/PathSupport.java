package lab.codeinsight.backend.parser.support;

import java.nio.file.Path;

public final class PathSupport {

  private PathSupport() {}

  public static String toRelativeUnixPath(Path root, Path path) {
    return root.relativize(path).toString().replace('\\', '/');
  }

  public static int indexOfPathElement(Path path, String segment) {
    for (int i = 0; i < path.getNameCount(); i++) {
      if (segment.equals(path.getName(i).toString())) {
        return i;
      }
    }
    return -1;
  }
}
