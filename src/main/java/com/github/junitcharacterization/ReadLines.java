package com.github.junitcharacterization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadLines {

    public static List<String> fromStream(ByteArrayOutputStream stream) {
        return fromInputStream(new ByteArrayInputStream(stream.toByteArray()));
    }

    public static List<String> fromFile(File file) {
        try {
            return fromInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
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
