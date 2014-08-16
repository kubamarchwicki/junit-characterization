package com.github.junitcharacterization;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.junitcharacterization.CharacterizationRule.*;
import static org.junit.Assert.*;
import static org.junit.experimental.results.PrintableResult.*;
import static org.junit.experimental.results.ResultMatchers.*;

public class CharacterizationRuleTest {

    final static String TEST_METHOD_PARAM = "first parameter";
    final static String BASE_FOLDER = System.getProperty("java.io.tmpdir") + File.separator;
    final static String FILENAME = "com.github.junitcharacterization.CharacterizationRuleTest.BusinessClassTest.txt";

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
                .build();

        private BusinessClass service = new BusinessClass();

        @Test
        public void just_run_the_method() {
            service.businessMethod(CharacterizationRuleTest.TEST_METHOD_PARAM);

        }
    }

    @Before
    public void delete_master_data_files() throws IOException, InterruptedException {
        System.clearProperty(RulesFactory.ENV_NAME_FOR_RECORDING);

        File outputFile = new File(BASE_FOLDER + FILENAME);
        Files.deleteIfExists(outputFile.toPath());
    }

    @Test
    public void should_throw_file_not_found_exception() {
        assertThat(testResult(BusinessClassTest.class), hasFailureContaining("java.io.FileNotFoundException"));
    }

    @Test
    public void should_create_master_output_file() {
        System.setProperty(RulesFactory.ENV_NAME_FOR_RECORDING, "true");

        assertThat(testResult(BusinessClassTest.class), isSuccessful());
        org.assertj.core.api.Assertions.assertThat(new File(BASE_FOLDER + FILENAME))
                .exists()
                .hasContent("param = " + TEST_METHOD_PARAM + System.lineSeparator() + "after split = first");
    }

    @Test
    public void should_successfully_compare() throws IOException {
        prepareMasterFile("param = " + TEST_METHOD_PARAM, "after split = first");

        assertThat(testResult(BusinessClassTest.class), isSuccessful());
    }

    @Test
    public void should_throw_comparison_error() throws IOException {
        prepareMasterFile("param = " + TEST_METHOD_PARAM, "after split = first", "Additional Line");

        assertThat(testResult(BusinessClassTest.class), hasFailureContaining("DeleteDelta, position: 2, lines: [Additional Line"));
    }

    private void prepareMasterFile(String... lines) throws IOException {
        File f = new File(BASE_FOLDER + FILENAME);
        f.createNewFile();

        FileWriter out = new FileWriter(f);
        for(String s: lines) {
            out.write(s);
            out.write(System.lineSeparator());
        }
        out.close();
    }
}
