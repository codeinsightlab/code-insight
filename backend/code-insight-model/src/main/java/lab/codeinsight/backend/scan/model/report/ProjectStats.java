package lab.codeinsight.backend.scan.model.report;

/**
 * Project-level statistics derived from scanner outputs.
 */
public record ProjectStats(int totalJavaFiles, int totalControllers, int totalEndpoints, int totalEntities,
		int totalEntityFields) {
}
