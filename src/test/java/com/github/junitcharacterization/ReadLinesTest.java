package com.github.junitcharacterization;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ReadLinesTest {

    @Test
    public void should_return_three_elements_from_file() {
        List<String> strings = ReadLines.fromFile(new File(".gitignore"));

        assertThat(strings.size(), is(equalTo(3)));
    }

    @Test
    public void should_return_three_elements_from_stream() throws IOException {
        final String elements = "*iml\n.idea\ntarget";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(elements.getBytes());

        List<String> strings = ReadLines.fromStream(baos);
        assertThat(strings.size(), is(equalTo(3)));
    }

}
