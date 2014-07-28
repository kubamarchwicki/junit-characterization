package com.github.junitcharacterization;

import difflib.DiffUtils;
import difflib.Patch;
import org.junit.rules.Verifier;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import static com.github.junitcharacterization.IsEmptyPatchMatcher.empty;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CaptureVerifier extends Verifier {

    private final ByteArrayOutputStream capturedStream;

    public CaptureVerifier(ByteArrayOutputStream capturedStream) {
        this.capturedStream = capturedStream;
    }

    @Override
    protected void verify() throws Throwable {
        assertThat(false, is(equalTo(false)));

        //TODO: customize filename
        String logfile = "dumpfile.log";

        List<String> actual = ReadLines.fromStream(capturedStream);
        List<String> original = ReadLines.fromFile(new File(logfile));

        Patch<String> patch = DiffUtils.diff(original, actual);

        assertThat(patch, is(empty()));
    }
}
