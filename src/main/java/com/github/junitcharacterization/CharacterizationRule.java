package com.github.junitcharacterization;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CharacterizationRule implements TestRule {
    private static final String ENV_NAME_FOR_RECORDING = "pinchpoint";
    private static final Logger log = Logger.getLogger(CharacterizationRule.class.getSimpleName());

    private final ByteArrayOutputStream capturedStream = new ByteArrayOutputStream();
    private List<TestRule> rules = new ArrayList<>();

    public CharacterizationRule(Class<?> clazz) {
        //TODO; create an output file per method (not only class)
        //TODO: refactor, move to class, factory class
        String filename = clazz.getCanonicalName() + ".txt";
        String baseFolder = clazz.getResource("/").getFile();
        File file = new File(baseFolder + filename);

        String env = System.getProperty(ENV_NAME_FOR_RECORDING);
        if (env != null) {
            try {
                log.warning("RECORDING MODE! Output to file [filename="+file.toURI()+"]");
                file.createNewFile();
                rules.add(new FileOutputCapture(file, capturedStream));
            } catch (IOException e) {
                throw new RuntimeException("Could not create a file [filename="+file.toPath()+"]");
            }
        } else {
            rules.add(new StreamOutputCapture(capturedStream));
            rules.add(new CaptureVerifier(file, capturedStream));
        }
    }

    @Override
    public Statement apply(Statement base, Description description) {
        for (TestRule each : rules) {
            base = each.apply(base, description);
        }
        return base;
    }
}
