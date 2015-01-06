package com.github.junitcharacterization;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.junitcharacterization.CharacterizationRule.*;
import static org.junit.Assert.*;
import static org.junit.experimental.results.PrintableResult.*;
import static org.junit.experimental.results.ResultMatchers.*;

public class CharacterizationRuleVerificationTest {

    final static String TEST_METHOD_PARAM = "first parameter";
    final static String BASE_FOLDER = System.getProperty("java.io.tmpdir") + File.separator;
    final static String FILENAME = "com.github.junitcharacterization.CharacterizationRuleVerificationTest.BusinessClassTest.txt";

    public static class BusinessClass {

        public String businessMethod(String param) {
            System.out.println("param = " + param);
            final String split = param.split(" ")[0];
            System.out.println("after split = " + split);
            return split;
        }
    }

    public static class BusinessClassTest {
        @ClassRule
        public static CharacterizationRule rule = aRuleFor(BusinessClassTest.class)
                .build();

        private BusinessClass service = new BusinessClass();

        @Test
        public void just_run_the_method() {
            service.businessMethod(CharacterizationRuleVerificationTest.TEST_METHOD_PARAM);

        }
    }

    @Before @After
    public void delete_master_data_files() throws IOException, InterruptedException {
        System.clearProperty(CharacterizationBuilder.ENV_NAME_FOR_RECORDING);

        File outputFile = new File(BASE_FOLDER + FILENAME);
        Files.deleteIfExists(outputFile.toPath());
    }

    @Test
    public void should_successfully_compare() throws IOException {
        prepareMasterFile("param = " + TEST_METHOD_PARAM, "after split = first");

        assertThat(testResult(BusinessClassTest.class), isSuccessful());
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
