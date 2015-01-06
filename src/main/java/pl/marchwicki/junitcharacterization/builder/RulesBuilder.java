package pl.marchwicki.junitcharacterization.builder;

import org.junit.rules.TestRule;

import java.util.List;

public interface RulesBuilder<T> {
    T inFolder(String folder);

    T withFilename(String filename);

    T appendToExistingFile();

    T clearOutputBeforeCapture();

    List<TestRule> build();
}
