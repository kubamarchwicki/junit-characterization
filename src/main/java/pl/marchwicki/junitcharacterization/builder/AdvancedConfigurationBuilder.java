package pl.marchwicki.junitcharacterization.builder;

import pl.marchwicki.junitcharacterization.CharacterizationBuilder;
import pl.marchwicki.junitcharacterization.Configuration;

public class AdvancedConfigurationBuilder implements ConfigurationBuilder<AdvancedConfigurationBuilder> {

    private final CharacterizationBuilder builder;
    private final ConfigurationBuilder<BasicConfigurationBuilder> parent;

    public AdvancedConfigurationBuilder(CharacterizationBuilder characterizationBuilder, ConfigurationBuilder builder) {
        this.builder = characterizationBuilder;
        this.parent = builder;
    }

    @Override
    public AdvancedConfigurationBuilder inFolder(String folder) {
        parent.inFolder(folder);
        return this;
    }

    @Override
    public AdvancedConfigurationBuilder withFilename(String filename) {
        parent.withFilename(filename);
        return this;
    }

    @Override
    public AdvancedConfigurationBuilder appendToExistingFile() {
        parent.appendToExistingFile();
        return this;
    }

    @Override
    public AdvancedConfigurationBuilder clearOutputBeforeCapture() {
        parent.clearOutputBeforeCapture();
        return this;
    }

    @Override
    public Configuration build() {
        return parent.build();
    }

    public CharacterizationBuilder up() {
        return builder;
    }
}
