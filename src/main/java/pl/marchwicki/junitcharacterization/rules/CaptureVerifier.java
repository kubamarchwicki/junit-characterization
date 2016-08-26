package pl.marchwicki.junitcharacterization.rules;

import difflib.DiffUtils;
import difflib.Patch;
import org.junit.rules.Verifier;
import pl.marchwicki.junitcharacterization.ReadLines;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static pl.marchwicki.junitcharacterization.IsEmptyPatchMatcher.*;

public class CaptureVerifier extends Verifier {

    private final ByteArrayOutputStream capturedStream;
    private final Path pinchFile;

    public CaptureVerifier(Path pinchFile, ByteArrayOutputStream capturedStream) {
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
