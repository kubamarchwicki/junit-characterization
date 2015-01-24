package pl.marchwicki.junitcharacterization.builder;

import pl.marchwicki.junitcharacterization.Configuration;

public interface ConfigurationBuilder<T> {
    T inFolder(String folder);

    T withFilename(String filename);

    T appendToExistingFile();

    T clearOutputBeforeCapture();

    Configuration build();
}
