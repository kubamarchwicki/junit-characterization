package pl.marchwicki.junitcharacterization;

import org.junit.rules.TestRule;
import pl.marchwicki.junitcharacterization.rules.CaptureVerifier;
import pl.marchwicki.junitcharacterization.rules.FileCreateRule;
import pl.marchwicki.junitcharacterization.rules.FileDeleteRule;
import pl.marchwicki.junitcharacterization.rules.FileOutputCapture;
import pl.marchwicki.junitcharacterization.rules.StreamOutputCapture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Configuration {
    final private static Logger log = Logger.getLogger(Configuration.class.getSimpleName());
    final private File outputFile;
    final private Boolean deleteExistingFile;

    public Configuration(File outputFile, Boolean deleteExistingFile) {
        this.outputFile = outputFile;
        this.deleteExistingFile = deleteExistingFile;
    }

    public List<TestRule> prepareRules() {
        final ByteArrayOutputStream capturedStream = new ByteArrayOutputStream();

        final List<TestRule> rules = new ArrayList<>();
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
