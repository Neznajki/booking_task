package com.booking.services.html;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class HtmlReaderService {
    public String readHtmlFileContent(String relativePath) {
        try {
            return Files.readString(Path.of(new ClassPathResource(relativePath).getURI()));
        } catch (IOException e) {
            throw new RuntimeException(String.format("relative path %s could not be found", relativePath));
        }
    }
}
