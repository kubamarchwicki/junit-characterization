package com.github.junitcharacterization.rules;

import org.junit.rules.ExternalResource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileOutputCapture extends ExternalResource {

    private File outputFile;
    private PrintStream original;
    protected final ByteArrayOutputStream capturedStream;

    public FileOutputCapture(File file, ByteArrayOutputStream capturedStream) {
        this.outputFile = file;
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
        try {
            Files.write(outputFile.toPath(), capturedStream.toByteArray(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("File write failed! ", e);
        }
    }
}
