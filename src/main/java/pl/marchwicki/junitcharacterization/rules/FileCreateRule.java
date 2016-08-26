package pl.marchwicki.junitcharacterization.rules;

import org.junit.rules.ExternalResource;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileCreateRule extends ExternalResource {
    private final Path path;

    public FileCreateRule(Path path) {
        this.path = path;
    }

    @Override
    protected void before() throws Throwable {
        Files.createFile(path);
    }
}
