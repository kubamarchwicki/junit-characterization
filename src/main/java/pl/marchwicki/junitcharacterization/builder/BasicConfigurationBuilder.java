package pl.marchwicki.junitcharacterization.builder;

import pl.marchwicki.junitcharacterization.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BasicConfigurationBuilder implements ConfigurationBuilder<BasicConfigurationBuilder> {

    private String folder;
    private String filename;
    private Boolean deleteExistingFile;

    @Override
    public BasicConfigurationBuilder inFolder(String folder) {
        this.folder = folder;
        return this;
    }

    @Override
    public BasicConfigurationBuilder withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    @Override
    public BasicConfigurationBuilder appendToExistingFile() {
        this.deleteExistingFile = false;
        return this;
    }

    @Override
    public BasicConfigurationBuilder clearOutputBeforeCapture() {
        this.deleteExistingFile = true;
        return this;
    }

    @Override
    public Configuration build() {
        if (folder == null || "".equals(folder)) {
            throw new IllegalArgumentException("Folder cannot be empty");
        }

        if (filename == null || "".equals(filename)) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        Path outputFile = Paths.get(folder, filename);
        return new Configuration(outputFile, deleteExistingFile);
    }

}
