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

    public static CharacterizationRuleBuilder aRule() {
        return new CharacterizationRuleBuilder();
    }

    private CharacterizationRule(Class<?> clazz) {
        //TODO; create an output file per method (not only class)
        //TODO: refactor, move to class, factory class
        String filename = clazz.getCanonicalName() + ".txt";
        String baseFolder = clazz.getResource("/").getFile();
        File file = new File(baseFolder + filename);

        rules = RulesFactory.getRules(file, capturedStream);
    }


    public static class CharacterizationRuleBuilder {

        private Class<?> clazz;

        public CharacterizationRuleBuilder forClass(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public CharacterizationRule build() {
            return new CharacterizationRule(clazz);
        }
    }
}

