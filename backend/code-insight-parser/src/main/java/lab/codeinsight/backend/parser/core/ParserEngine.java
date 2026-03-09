package lab.codeinsight.backend.parser.core;

import lab.codeinsight.backend.parser.model.scan.ProjectScanModel;

/**
 * Core parser orchestration contract.
 */
public interface ParserEngine {

	/**
	 * Runs a full parser scan based on context and returns a unified scan model.
	 */
	ScanResult<ProjectScanModel> scanProject(ScanContext context);
}
