package pl.marchwicki.junitcharacterization;

import org.assertj.core.data.Index;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static pl.marchwicki.junitcharacterization.builders.TestByteArrayOutputStreamBuilder.*;
import static pl.marchwicki.junitcharacterization.builders.TestFileBuilder.*;
import static org.assertj.core.api.Assertions.*;

public class ReadLinesTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void should_return_empty_list_on_empty_file() throws IOException {
        File file = folder.newFile();

        List<String> strings = ReadLines.fromFile(file.toPath());

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
        final Path file = aFile(folder)
                .withContent("first line")
                .withContent("second line")
                .withContent("third line")
                .build();

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
        final ByteArrayOutputStream baos = aStream()
                .withContent("first" + System.lineSeparator())
                .withContent("second" + System.lineSeparator())
                .withContent("third" + System.lineSeparator()).build();

        //when
        List<String> strings = ReadLines.fromStream(baos);

        //then
        assertThat(strings).hasSize(3)
                .contains("first", Index.atIndex(0))
                .contains("second", Index.atIndex(1))
                .contains("third", Index.atIndex(2));

    }

}
