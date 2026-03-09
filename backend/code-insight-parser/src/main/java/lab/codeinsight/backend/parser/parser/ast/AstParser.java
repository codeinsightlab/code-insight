package lab.codeinsight.backend.parser.parser.ast;

import java.nio.file.Path;

public class AstParser {

  public boolean isJavaSourcePath(Path path) {
    return path.toString().endsWith(".java");
  }
}
