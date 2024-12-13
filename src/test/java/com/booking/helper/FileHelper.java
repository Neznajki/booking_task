package com.booking.helper;

import com.sun.istack.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHelper {
    @NotNull
    public static String readFileAsString(String fileRelativePath) {
        Stream<String> stream = new BufferedReader(
            new InputStreamReader(getFileInputStream(fileRelativePath))
        ).lines();

        return stream.collect(Collectors.joining("\r\n"));
    }

    @NotNull
    public static InputStream getFileInputStream(String fileRelativePath) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileRelativePath);

        return Objects.requireNonNull(resourceAsStream);
    }
}
