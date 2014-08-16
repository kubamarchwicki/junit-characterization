package com.github.junitcharacterization.rules;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static com.github.junitcharacterization.builders.TestByteArrayOutputStreamBuilder.*;
import static com.github.junitcharacterization.builders.TestFileBuilder.*;
import static org.assertj.core.api.Assertions.*;

public class CaptureVerifierTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void should_pass_on_same_content() throws IOException {
        final String STRING = "foo bar bar";

        final File file = aFile(folder)
                .withContent(STRING).build();

        final ByteArrayOutputStream baos = aStream()
                .withContent(STRING).build();

        CaptureVerifier verifier = new CaptureVerifier(file, baos);

        try {
            verifier.verify();
        } catch (Throwable throwable) {
            fail("No differences on comparison", throwable);
        }
    }

    @Test
    public void should_fail_on_difference_content() throws IOException {
        final File file = aFile(folder)
                .withContent("foo bar").build();

        final ByteArrayOutputStream baos = aStream()
                .withContent("foo bar")
                .withContent(" bar")
                .build();

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
