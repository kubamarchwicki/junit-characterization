package com.github.junitcharacterization;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.junitcharacterization.CharacterizationRule.*;
import static org.junit.Assert.*;
import static org.junit.experimental.results.PrintableResult.*;
import static org.junit.experimental.results.ResultMatchers.*;

public class CharacterizationRuleAppendModeTest {

    final static String FIRST_METHOD_PARAM = "first parameter";
    final static String SECOND_METHOD_PARAM = "second parameter";
    final static String BASE_FOLDER = System.getProperty("java.io.tmpdir") + File.separator;
    final static String FILENAME = "com.github.junitcharacterization.CharacterizationRuleAppendModeTest.BusinessClassTest.txt";

    public static class BusinessClass {

        public String businessMethod(String param) {
            System.out.println("param = " + param);
            final String split = param.split(" ")[0];
            System.out.println("after split = " + split);
            return split;
        }
    }

    public static class BusinessClassTest {
        @Rule
        public CharacterizationRule rule = aRuleFor(BusinessClassTest.class)
                .appendMode()
                .build();

        private BusinessClass service = new BusinessClass();

        @Test
        public void just_run_the_method() {
            service.businessMethod(CharacterizationRuleAppendModeTest.FIRST_METHOD_PARAM);
        }

        @Test
        public void a_second_test_method() {
            service.businessMethod(CharacterizationRuleAppendModeTest.SECOND_METHOD_PARAM);
        }
    }

    @BeforeClass
    public static void delete_master_data_files() throws IOException, InterruptedException {
        System.clearProperty(CharacterizationBuilder.ENV_NAME_FOR_RECORDING);

        File outputFile = new File(BASE_FOLDER + FILENAME);
        Files.deleteIfExists(outputFile.toPath());
    }

    @Test
    public void should_create_master_output_file() {
        System.setProperty(CharacterizationBuilder.ENV_NAME_FOR_RECORDING, "true");

        assertThat(testResult(BusinessClassTest.class), isSuccessful());
        org.assertj.core.api.Assertions.assertThat(new File(BASE_FOLDER + FILENAME))
                .exists()
                .hasContent("param = " + FIRST_METHOD_PARAM +
                        System.lineSeparator() + "after split = first" +
                        System.lineSeparator() + "param = " + SECOND_METHOD_PARAM +
                        System.lineSeparator() + "after split = second");
    }

}
