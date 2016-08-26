package pl.marchwicki.junitcharacterization.rules;

import org.junit.rules.ExternalResource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDeleteRule extends ExternalResource {

    private final Path path;

    public FileDeleteRule(Path path) {
        this.path = path;
    }

    @Override
    protected void before() throws Throwable {
        Files.deleteIfExists(path);
        Files.createFile(path);
    }
}