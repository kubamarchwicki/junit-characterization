package com.github.junitcharacterization;

import org.assertj.core.data.Index;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ReadLinesTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void should_return_empty_list_on_empty_file() throws IOException {
        File file = folder.newFile();

        List<String> strings = ReadLines.fromFile(file);

        assertThat(strings).isEmpty();
    }

    @Test
    public void should_return_empty_list_on_empty_stream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        List<String> strings = ReadLines.fromStream(baos);

        assertThat(strings).isEmpty();
    }

    @Test
    public void should_return_three_elements_from_file() throws IOException {
        //given
        File file = folder.newFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("first line\n");
        out.write("second line\n");
        out.write("third line\n");
        out.close();

        //when
        List<String> strings = ReadLines.fromFile(file);

        //then
        assertThat(strings).hasSize(3)
                .contains("first line", Index.atIndex(0))
                .contains("second line", Index.atIndex(1))
                .contains("third line", Index.atIndex(2));
    }

    @Test
    public void should_return_three_elements_from_stream() throws IOException {
        //given
        final String elements = "first\nsecond\nthird";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(elements.getBytes());

        //when
        List<String> strings = ReadLines.fromStream(baos);

        //then
        assertThat(strings).hasSize(3)
                .contains("first", Index.atIndex(0))
                .contains("second", Index.atIndex(1))
                .contains("third", Index.atIndex(2));

    }

}
