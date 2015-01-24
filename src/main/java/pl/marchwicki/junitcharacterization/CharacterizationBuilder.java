package pl.marchwicki.junitcharacterization;

import pl.marchwicki.junitcharacterization.builder.ConfigurationBuilder;
import pl.marchwicki.junitcharacterization.builder.BasicConfigurationBuilder;
import pl.marchwicki.junitcharacterization.builder.AdvancedConfigurationBuilder;

public class CharacterizationBuilder {

    final public static String ENV_NAME_FOR_RECORDING = "pinchpoint";
    final private static String DEFAULT_FOLDER = System.getProperty("java.io.tmpdir");
    final private String DEFAULT_FILENAME;
    final private AdvancedConfigurationBuilder rulesBuilder;

    public CharacterizationBuilder(Class<?> clazz) {
        this.DEFAULT_FILENAME = clazz.getCanonicalName() + ".txt";
        this.rulesBuilder = withDefaultRules();
    }

    private AdvancedConfigurationBuilder withDefaultRules() {
        ConfigurationBuilder builder = new BasicConfigurationBuilder()
                .clearOutputBeforeCapture()
                .inFolder(DEFAULT_FOLDER)
                .withFilename(DEFAULT_FILENAME);
        return new AdvancedConfigurationBuilder(this, builder);
    }

    public AdvancedConfigurationBuilder withRules() {
        return this.rulesBuilder;
    }

    public CharacterizationRule build() {
        return new CharacterizationRule(rulesBuilder.build());
    }

}