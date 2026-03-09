package lab.codeinsight.backend.parser.core;

/** Minimal scanner abstraction for one input to one output scan step. */
@FunctionalInterface
public interface Scanner<I, O> {

  O scan(I input);
}
