package lab.codeinsight.backend.parser.core;

import java.util.List;

public record ScanResult<T>(T data, List<String> warnings) {

  public static <T> ScanResult<T> success(T data) {
    return new ScanResult<>(data, List.of());
  }
}
