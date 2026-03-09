package lab.codeinsight.backend.parser.core;

/**
 * Minimal scanner abstraction for one input to one output scan step.
 */
@FunctionalInterface
public interface Scanner<I, O> {

	/**
	 * Scans input and returns scanner-specific output.
	 */
	O scan(I input);
}
