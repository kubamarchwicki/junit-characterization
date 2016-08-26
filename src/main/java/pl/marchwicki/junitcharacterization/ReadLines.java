package pl.marchwicki.junitcharacterization;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ReadLines {

    public static List<String> fromStream(ByteArrayOutputStream stream) {
        return fromInputStream(new ByteArrayInputStream(stream.toByteArray()));
    }

    public static List<String> fromFile(Path file) {
        try {
            return Files.readAllLines(file, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> fromInputStream(InputStream stream) {
        Scanner s = new Scanner(stream).useDelimiter(System.lineSeparator());
        List<String> list = new ArrayList<>();
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();

        return list;
    }
}
