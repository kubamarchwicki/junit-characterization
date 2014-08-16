package com.github.junitcharacterization.rules;

import com.github.junitcharacterization.ReadLines;
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
    private final File pinchFile;

    public CaptureVerifier(File pinchFile, ByteArrayOutputStream capturedStream) {
        this.pinchFile = pinchFile;
        this.capturedStream = capturedStream;
    }

    @Override
    protected void verify() throws Throwable {
        List<String> actual = ReadLines.fromStream(capturedStream);
        List<String> original = ReadLines.fromFile(pinchFile);

        Patch<String> patch = DiffUtils.diff(original, actual);

        assertThat(patch, is(empty()));
    }
}
