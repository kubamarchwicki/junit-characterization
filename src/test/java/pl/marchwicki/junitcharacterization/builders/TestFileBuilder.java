package pl.marchwicki.junitcharacterization.builders;

import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TestFileBuilder {

    private final TemporaryFolder folder;
    private List<String> content = new ArrayList<>();

    public TestFileBuilder(TemporaryFolder inFolder) {
        this.folder = inFolder;
    }

    public static TestFileBuilder aFile(TemporaryFolder inFolder) {
        return new TestFileBuilder(inFolder);
    }

    public TestFileBuilder withContent(String s) {
        content.add(s);
        return this;
    }

    public Path build() throws IOException {
        Path file = folder.newFile().toPath();
        Files.write(file, content, Charset.defaultCharset());
        return file;
    }

}
