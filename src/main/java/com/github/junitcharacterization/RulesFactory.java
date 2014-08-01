package com.github.junitcharacterization;

import org.junit.rules.TestRule;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RulesFactory {
    public static final String ENV_NAME_FOR_RECORDING = "pinchpoint";
    private static final Logger log = Logger.getLogger(RulesFactory.class.getSimpleName());

    public static List<TestRule> getRules(File outputFile, ByteArrayOutputStream capturedStream) {

        String env = System.getProperty(ENV_NAME_FOR_RECORDING);
        if (env != null) {
            return masterCreateRule(outputFile, capturedStream);
        } else {
            return Lists.aList()
                    .withRule(new StreamOutputCapture(capturedStream))
                    .withRule(new CaptureVerifier(outputFile, capturedStream))
                    .build();
        }

    }

    private static List<TestRule> masterCreateRule(File outputFile, ByteArrayOutputStream capturedStream) {
        try {
            log.warning("RECORDING MODE! Output to file [filename="+outputFile.toURI()+"]");
            outputFile.createNewFile();
            return Lists.aList()
                    .withRule(new FileOutputCapture(outputFile, capturedStream))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Could not create a file [filename="+outputFile.toPath()+"]");
        }
    }

    public static class Lists {

        List<TestRule> rules = new ArrayList<>();

        public static Lists aList() {
            return new Lists();
        }

        public Lists withRule(TestRule rule) {
            rules.add(rule);
            return this;
        }

        public List<TestRule> build() {
            return rules;
        }
    }
}
