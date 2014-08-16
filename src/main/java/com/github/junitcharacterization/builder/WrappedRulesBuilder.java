package com.github.junitcharacterization.builder;

import com.github.junitcharacterization.CharacterizationBuilder;
import org.junit.rules.TestRule;

import java.util.List;

public class WrappedRulesBuilder implements RulesBuilder<WrappedRulesBuilder> {

    private final CharacterizationBuilder parent;
    private final RulesBuilder<SimpleRulesBuilder> builder;

    public WrappedRulesBuilder(CharacterizationBuilder characterizationBuilder, RulesBuilder builder) {
        this.parent = characterizationBuilder;
        this.builder = builder;
    }

    @Override
    public WrappedRulesBuilder inFolder(String folder) {
        builder.inFolder(folder);
        return this;
    }

    @Override
    public WrappedRulesBuilder withFilename(String filename) {
        builder.withFilename(filename);
        return this;
    }

    @Override
    public WrappedRulesBuilder appendToExistingFile() {
        builder.appendToExistingFile();
        return this;
    }

    @Override
    public WrappedRulesBuilder clearOutputBeforeCapture() {
        builder.clearOutputBeforeCapture();
        return this;
    }

    @Override
    public List<TestRule> build() {
        return builder.build();
    }

    public CharacterizationBuilder up() {
        return parent;
    }
}
