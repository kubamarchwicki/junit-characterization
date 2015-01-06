package pl.marchwicki.junitcharacterization;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.List;

public class CharacterizationRule implements TestRule {
    final private List<TestRule> rules;

    @Override
    public Statement apply(Statement base, Description description) {
        for (TestRule each : rules) {
            base = each.apply(base, description);
        }
        return base;
    }

    public static CharacterizationBuilder aRuleFor(Class<?> clazz) {
        return new CharacterizationBuilder(clazz);
    }

    public CharacterizationRule(List<TestRule> rules) {
        this.rules = rules;
    }

}

