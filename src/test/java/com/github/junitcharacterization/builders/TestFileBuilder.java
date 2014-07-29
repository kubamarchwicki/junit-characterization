package com.github.junitcharacterization.builders;

import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public File build() throws IOException {
        File file = folder.newFile();
        final BufferedWriter out = new BufferedWriter(new FileWriter(file));
        for (String s: content) {
            out.write(s);
        }

        out.close();

        return file;
    }

}
