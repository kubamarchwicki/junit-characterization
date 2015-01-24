package pl.marchwicki.junitcharacterization.builder;

import pl.marchwicki.junitcharacterization.Configuration;

import java.io.File;

public class BasicConfigurationBuilder implements ConfigurationBuilder<BasicConfigurationBuilder> {

    private File folder;
    private String filename;
    private Boolean deleteExistingFile;

    @Override
    public BasicConfigurationBuilder inFolder(String folder) {
        this.folder = new File(folder);
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

        final File outputFile = new File(folder, filename);
        return new Configuration(outputFile, deleteExistingFile);
    }

}
