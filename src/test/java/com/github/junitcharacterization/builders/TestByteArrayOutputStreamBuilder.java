package com.github.junitcharacterization.builders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestByteArrayOutputStreamBuilder {

    private List<String> content = new ArrayList<>();

    public static TestByteArrayOutputStreamBuilder aStream() {
        return new TestByteArrayOutputStreamBuilder();
    }

    public TestByteArrayOutputStreamBuilder withContent(String s) {
        content.add(s);
        return this;
    }

    public ByteArrayOutputStream build() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (String s : content) {
            out.write(s.getBytes());
        }
        out.close();

        return out;
    }
}
