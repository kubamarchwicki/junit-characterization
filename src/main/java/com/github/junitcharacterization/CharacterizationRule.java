package com.github.junitcharacterization;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CharacterizationRule implements TestRule {
    private final ByteArrayOutputStream capturedStream = new ByteArrayOutputStream();
    private List<TestRule> rules = new ArrayList<>();

    @Override
    public Statement apply(Statement base, Description description) {
        for (TestRule each : rules) {
            base = each.apply(base, description);
        }
        return base;
    }

    public static CharacterizationRuleBuilder aRuleFor(Class<?> clazz) {
        return new CharacterizationRuleBuilder(clazz);
    }

    public CharacterizationRule(File folder, String filename) {
        this(new File(folder, filename));
    }

    private CharacterizationRule(File outputFile) {
        //TODO; create an output file per method (not only class)

        rules = RulesFactory.getRules(outputFile, capturedStream);
    }


    public static class CharacterizationRuleBuilder {

        private final static String DEFAULT_FOLDER = System.getProperty("java.io.tmpdir");
        private String filename;
        private File folder;

        public CharacterizationRuleBuilder(Class<?> clazz) {
            withFilename(clazz.getCanonicalName());
            inFolder(DEFAULT_FOLDER);
        }

        public CharacterizationRuleBuilder withFilename(String filename) {
            this.filename = filename + ".txt";
            return this;
        }

        public CharacterizationRuleBuilder inFolder(String folder) {
            this.folder = new File(folder);
            return this;
        }

        public CharacterizationRule build() {
            return new CharacterizationRule(new File(folder, filename));
        }
    }
}

