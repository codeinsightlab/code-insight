package lab.codeinsight.backend.parser.core;

import java.util.List;

/** Generic wrapper for parser outputs with optional warnings. */
public record ScanResult<T>(T data, List<String> warnings) {

  /** Convenience factory for successful result without warnings. */
  public static <T> ScanResult<T> success(T data) {
    return new ScanResult<>(data, List.of());
  }
}
