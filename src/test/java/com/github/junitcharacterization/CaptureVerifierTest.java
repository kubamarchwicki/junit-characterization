package com.github.junitcharacterization;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class CaptureVerifierTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void should_pass_on_same_content() throws IOException {
        final String STRING = "foo bar bar";

        final File file = folder.newFile();
        final BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(STRING);
        out.close();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(STRING.getBytes());

        CaptureVerifier verifier = new CaptureVerifier(file, baos);

        try {
            verifier.verify();
        } catch (Throwable throwable) {
            fail("No differences on comparison", throwable);
        }
    }

    @Test
    public void should_fail_on_difference_content() throws IOException {
        final File file = folder.newFile();
        final BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("foo bar");
        out.close();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("foo bar bar".getBytes());

        CaptureVerifier verifier = new CaptureVerifier(file, baos);

        try {
            verifier.verify();
            failBecauseExceptionWasNotThrown(Throwable.class);
        } catch (Throwable throwable) {
            assertThat(throwable.getMessage())
                    .contains("[ChangeDelta, position: 0, lines: [foo bar] to [foo bar bar]");
        }
    }

}
