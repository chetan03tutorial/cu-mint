package com.chetan03tutorial.libs.aligator.util;

import com.chetan03tutorial.libs.aligator.exception.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {
    private static final Logger log = LoggerFactory.getLogger(FileReader.class);

    private FileReader(){
    }

    public static String readFile(String fileName) {
        Path path ;
        String data;
        Stream<String> lines ;
        try {
            URL url = FileReader.class.getClassLoader()
                    .getResource(fileName);
            Optional.ofNullable(url)
                    .orElseThrow(() -> new FileException(String.format("Unable to locate file %s", fileName)));
            path = Paths.get(url.toURI());
            lines = Files.lines(path);
            data = lines.collect(Collectors.joining("\n"));
            lines.close();
        } catch (URISyntaxException | IOException e) {
            log.error(String.format("Unable to locate file %s", fileName), e.getCause());
            throw new FileException(e.getMessage(), e);
        }
        return data;
    }
}
