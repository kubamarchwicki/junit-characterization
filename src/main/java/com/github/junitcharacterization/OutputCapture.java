package com.github.junitcharacterization;

import org.junit.rules.ExternalResource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OutputCapture extends ExternalResource {

    private PrintStream original;
    private final ByteArrayOutputStream capturedStream;

    public OutputCapture(ByteArrayOutputStream capturedStream) {
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
