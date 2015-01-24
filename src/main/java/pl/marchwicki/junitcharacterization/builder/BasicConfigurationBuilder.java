package pl.marchwicki.junitcharacterization.builder;

import pl.marchwicki.junitcharacterization.CharacterizationBuilder;
import pl.marchwicki.junitcharacterization.rules.CaptureVerifier;
import pl.marchwicki.junitcharacterization.rules.FileCreateRule;
import pl.marchwicki.junitcharacterization.rules.FileDeleteRule;
import pl.marchwicki.junitcharacterization.rules.FileOutputCapture;
import pl.marchwicki.junitcharacterization.rules.StreamOutputCapture;
import org.junit.rules.TestRule;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BasicConfigurationBuilder implements ConfigurationBuilder<BasicConfigurationBuilder> {

    final private static Logger log = Logger.getLogger(BasicConfigurationBuilder.class.getSimpleName());
    private List<TestRule> rules = new ArrayList<>();
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
    public List<TestRule> build() {
        if (folder == null || "".equals(folder)) {
            throw new IllegalArgumentException("Folder cannot be empty");
        }

        if (filename == null || "".equals(filename)) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        final ByteArrayOutputStream capturedStream = new ByteArrayOutputStream();
        final File outputFile = new File(folder, filename);


        if (isRecording()) {
            log.warning("RECORDING MODE! Output to file [filename="+outputFile.toURI()+"]");
            rules.add(fileHandlingModeRule(outputFile));
            rules.add(new FileOutputCapture(outputFile, capturedStream));
        } else {
            rules.add(new StreamOutputCapture(capturedStream));
            rules.add(new CaptureVerifier(outputFile, capturedStream));
        }

        return rules;
    }

    private boolean isRecording() {
        String env = System.getProperty(CharacterizationBuilder.ENV_NAME_FOR_RECORDING);
        return (env != null);
    }

    private TestRule fileHandlingModeRule(File outputFile) {
        if (deleteExistingFile) {
            return new FileDeleteRule(outputFile);
        } else {
            return new FileCreateRule(outputFile);
        }
    }
}
