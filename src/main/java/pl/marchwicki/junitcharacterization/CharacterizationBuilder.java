package pl.marchwicki.junitcharacterization;

import pl.marchwicki.junitcharacterization.builder.RulesBuilder;
import pl.marchwicki.junitcharacterization.builder.SimpleRulesBuilder;
import pl.marchwicki.junitcharacterization.builder.WrappedRulesBuilder;

public class CharacterizationBuilder {

    final public static String ENV_NAME_FOR_RECORDING = "pinchpoint";
    final private static String DEFAULT_FOLDER = System.getProperty("java.io.tmpdir");
    final private String DEFAULT_FILENAME;
    final private WrappedRulesBuilder rulesBuilder;

    public CharacterizationBuilder(Class<?> clazz) {
        this.DEFAULT_FILENAME = clazz.getCanonicalName() + ".txt";
        this.rulesBuilder = withDefaultRules();
    }

    private WrappedRulesBuilder withDefaultRules() {
        RulesBuilder builder = new SimpleRulesBuilder()
                .clearOutputBeforeCapture()
                .inFolder(DEFAULT_FOLDER)
                .withFilename(DEFAULT_FILENAME);
        return new WrappedRulesBuilder(this, builder);
    }

    public WrappedRulesBuilder withRules() {
        return this.rulesBuilder;
    }

    public CharacterizationRule build() {
        return new CharacterizationRule(rulesBuilder.build());
    }

}