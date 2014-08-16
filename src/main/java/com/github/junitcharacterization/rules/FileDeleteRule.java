package com.github.junitcharacterization.rules;

import org.junit.rules.ExternalResource;

import java.io.File;
import java.nio.file.Files;

public class FileDeleteRule extends ExternalResource {

    private final File file;

    public FileDeleteRule(File file) {
        this.file = file;
    }

    @Override
    protected void before() throws Throwable {
        Files.deleteIfExists(file.toPath());
        file.createNewFile();
    }

    @Override
    protected void after() {
        //do nothing
    }
}
