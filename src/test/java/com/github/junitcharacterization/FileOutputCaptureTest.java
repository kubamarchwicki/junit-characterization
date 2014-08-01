package com.github.junitcharacterization;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;

public class FileOutputCaptureTest {

    private FileOutputCapture capture;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void should_capture_output() throws Throwable {
        //given
        final ByteArrayOutputStream capturedStream = new ByteArrayOutputStream();
        final PrintStream originalStream = System.out;
        final File outputFile = folder.newFile();
        capture = new FileOutputCapture(outputFile, capturedStream);

        //when
        capture.before();

        System.out.println("first output line");
        System.out.println("second output line");

        capture.after();

        //then
        assertThat(System.out)
                .isSameAs(originalStream);
        assertThat(capturedStream.toString())
                .isNotEmpty()
                .contains("first output line")
                .contains("second output line");
        assertThat(Files.readAllLines(outputFile.toPath(), StandardCharsets.UTF_8))
                .isNotEmpty()
                .contains("first output line")
                .contains("second output line");
    }

}
