package pl.marchwicki.junitcharacterization.builder;

import pl.marchwicki.junitcharacterization.CharacterizationBuilder;
import org.junit.rules.TestRule;

import java.util.List;

public class AdvancedConfigurationBuilder implements ConfigurationBuilder<AdvancedConfigurationBuilder> {

    private final CharacterizationBuilder parent;
    private final ConfigurationBuilder<BasicConfigurationBuilder> builder;

    public AdvancedConfigurationBuilder(CharacterizationBuilder characterizationBuilder, ConfigurationBuilder builder) {
        this.parent = characterizationBuilder;
        this.builder = builder;
    }

    @Override
    public AdvancedConfigurationBuilder inFolder(String folder) {
        builder.inFolder(folder);
        return this;
    }

    @Override
    public AdvancedConfigurationBuilder withFilename(String filename) {
        builder.withFilename(filename);
        return this;
    }

    @Override
    public AdvancedConfigurationBuilder appendToExistingFile() {
        builder.appendToExistingFile();
        return this;
    }

    @Override
    public AdvancedConfigurationBuilder clearOutputBeforeCapture() {
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
