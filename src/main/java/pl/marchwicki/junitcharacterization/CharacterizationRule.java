package pl.marchwicki.junitcharacterization;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class CharacterizationRule implements TestRule {
    final private Configuration configuration;

    @Override
    public Statement apply(Statement base, Description description) {
        for (TestRule each : configuration.prepareRules()) {
            base = each.apply(base, description);
        }
        return base;
    }

    public static CharacterizationBuilder aRuleFor(Class<?> clazz) {
        return new CharacterizationBuilder(clazz);
    }

    public CharacterizationRule(Configuration config) {
        this.configuration = config;
    }
}

