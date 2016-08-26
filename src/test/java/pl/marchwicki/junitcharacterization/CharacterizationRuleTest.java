package pl.marchwicki.junitcharacterization;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static pl.marchwicki.junitcharacterization.CharacterizationRule.*;
import static org.junit.Assert.*;
import static org.junit.experimental.results.PrintableResult.*;
import static org.junit.experimental.results.ResultMatchers.*;

public class CharacterizationRuleTest {

    final static String TEST_METHOD_PARAM = "first parameter";
    final static String BASE_FOLDER = System.getProperty("java.io.tmpdir");
    final static String FILENAME = "pl.marchwicki.junitcharacterization.CharacterizationRuleTest.BusinessClassTest.txt";

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
            service.businessMethod(CharacterizationRuleTest.TEST_METHOD_PARAM);

        }
    }

    @Before @After
    public void delete_master_data_files() throws IOException, InterruptedException {
        System.clearProperty(CharacterizationBuilder.ENV_NAME_FOR_RECORDING);

        Path outputFile = Paths.get(BASE_FOLDER, FILENAME);
        Files.deleteIfExists(outputFile);
    }

    @Test
    public void should_create_master_output_file() {
        System.setProperty(CharacterizationBuilder.ENV_NAME_FOR_RECORDING, "true");

        assertThat(testResult(BusinessClassTest.class), isSuccessful());
        org.assertj.core.api.Assertions.assertThat(Paths.get(BASE_FOLDER, FILENAME))
                .exists()
                .hasContent("param = " + TEST_METHOD_PARAM + System.lineSeparator() + "after split = first");
    }

    @Test
    public void should_throw_file_not_found_exception() {
        assertThat(testResult(BusinessClassTest.class), hasFailureContaining("NoSuchFileException"));
    }

    @Test
    public void should_throw_comparison_error() throws IOException {
        prepareMasterFile("param = " + TEST_METHOD_PARAM, "after split = first", "Additional Line");

        assertThat(testResult(BusinessClassTest.class), hasFailureContaining("DeleteDelta, position: 2, lines: [Additional Line"));
    }

    @Test
    public void should_successfully_compare() throws IOException {
        prepareMasterFile("param = " + TEST_METHOD_PARAM, "after split = first");

        assertThat(testResult(BusinessClassTest.class), isSuccessful());
    }

    protected void prepareMasterFile(String... lines) throws IOException {
        Path outputFile = Paths.get(BASE_FOLDER, FILENAME);
        Path file = Files.createFile(outputFile);

        Files.write(file, Arrays.asList(lines), Charset.defaultCharset());
    }
}
