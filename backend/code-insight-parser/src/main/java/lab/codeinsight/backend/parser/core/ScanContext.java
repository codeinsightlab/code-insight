package lab.codeinsight.backend.parser.core;

import java.nio.file.Path;

/**
 * Input context shared across parser scanning steps.
 */
public record ScanContext(Path projectRoot) {
}
