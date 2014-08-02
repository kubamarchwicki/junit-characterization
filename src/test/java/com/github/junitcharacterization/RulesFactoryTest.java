package com.github.junitcharacterization;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class RulesFactoryTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void clearSystemProperties() {
        System.clearProperty(RulesFactory.ENV_NAME_FOR_RECORDING);
    }

    @Test
    public void should_return_master_creation_rule() throws IOException {
        //given
        System.setProperty(RulesFactory.ENV_NAME_FOR_RECORDING, "true");

        //when
        List<TestRule> rules = RulesFactory.getRules(folder.newFile(), new ByteArrayOutputStream());

        //then
        assertThat(rules).hasSize(1);
        assertThat(rules.get(0)).isInstanceOf(FileOutputCapture.class);
    }


    @Test
    public void should_return_standard_rules() throws IOException {
        //when
        List<TestRule> rules = RulesFactory.getRules(folder.newFile(), new ByteArrayOutputStream());

        //then
        assertThat(rules).hasSize(2);
        assertThat(rules.get(0)).isInstanceOf(StreamOutputCapture.class);
        assertThat(rules.get(1)).isInstanceOf(CaptureVerifier.class);
    }

}

