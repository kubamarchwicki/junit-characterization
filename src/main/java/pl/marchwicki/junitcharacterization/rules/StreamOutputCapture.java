package pl.marchwicki.junitcharacterization.rules;

import org.junit.rules.ExternalResource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StreamOutputCapture extends ExternalResource {

    private PrintStream original;
    protected final ByteArrayOutputStream capturedStream;

    public StreamOutputCapture(ByteArrayOutputStream capturedStream) {
        this.capturedStream = capturedStream;
    }

    @Override
    protected void before() throws Throwable {
        original = System.out;
        PrintStream pos = new PrintStream(capturedStream);
        System.setOut(pos);
    }

    @Override
    protected void after() {
        System.setOut(original);
    }
}
