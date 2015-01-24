package pl.marchwicki.junitcharacterization;

import pl.marchwicki.junitcharacterization.builder.BasicConfigurationBuilder;
import pl.marchwicki.junitcharacterization.rules.CaptureVerifier;
import pl.marchwicki.junitcharacterization.rules.FileDeleteRule;
import pl.marchwicki.junitcharacterization.rules.FileOutputCapture;
import pl.marchwicki.junitcharacterization.rules.StreamOutputCapture;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class RulesBuilderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void clearSystemProperties() {
        System.clearProperty(CharacterizationBuilder.ENV_NAME_FOR_RECORDING);
    }

    @Test
    public void should_return_master_creation_rule() throws IOException {
        //given
        System.setProperty(CharacterizationBuilder.ENV_NAME_FOR_RECORDING, "true");

        //when
        List<TestRule> rules = new BasicConfigurationBuilder()
                .clearOutputBeforeCapture()
                .inFolder(folder.getRoot().toString())
                .withFilename("foo.txt")
                .build();

        //then
        assertThat(rules).hasSize(2);
        assertThat(rules.get(0)).isInstanceOf(FileDeleteRule.class);
        assertThat(rules.get(1)).isInstanceOf(FileOutputCapture.class);
    }


    @Test
    public void should_return_standard_rules() throws IOException {
        //when
        List<TestRule> rules = new BasicConfigurationBuilder()
                .inFolder(folder.getRoot().toString())
                .withFilename("foo.txt")
                .build();

        //then
        assertThat(rules).hasSize(2);
        assertThat(rules.get(0)).isInstanceOf(StreamOutputCapture.class);
        assertThat(rules.get(1)).isInstanceOf(CaptureVerifier.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_assert_exceptions_on_empty_folder() {
        new BasicConfigurationBuilder()
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_assert_exceptions_on_empty_filename() {
        new BasicConfigurationBuilder()
                .inFolder(folder.getRoot().toString())
                .build();
    }
}

