package lab.codeinsight.backend.parser.core;

@FunctionalInterface
public interface Scanner<I, O> {

  O scan(I input);
}
