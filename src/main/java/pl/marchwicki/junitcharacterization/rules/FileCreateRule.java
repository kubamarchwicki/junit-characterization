package pl.marchwicki.junitcharacterization.rules;

import org.junit.rules.ExternalResource;

import java.io.File;

public class FileCreateRule extends ExternalResource {
    private final File file;

    public FileCreateRule(File file) {
        this.file = file;
    }

    @Override
    protected void before() throws Throwable {
        file.createNewFile();
    }
}
