package com.github.junitcharacterization;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CharacterizationRule implements TestRule {
    private static final String ENV_NAME_FOR_RECORDING = "pinchpoint";
    private static final Logger log = Logger.getLogger(CharacterizationRule.class.getSimpleName());

    private final ByteArrayOutputStream capturedStream = new ByteArrayOutputStream();
    private List<TestRule> rules = new ArrayList<>();

    public CharacterizationRule(Class<?> clazz) {
        String filename = clazz.getCanonicalName() + ".txt";
        File file = new File(filename);

        String env = System.getenv(ENV_NAME_FOR_RECORDING);
        if (env != null) {
            log.warning("RECORDING MODE! Output to file [filename="+filename+"]");
            rules.add(new FileOutputCapture(file, capturedStream));
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
