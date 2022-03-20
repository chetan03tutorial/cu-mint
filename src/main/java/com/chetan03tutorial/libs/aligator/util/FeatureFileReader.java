package com.chetan03tutorial.libs.aligator.util;

import com.chetan03tutorial.libs.aligator.exception.FileException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FeatureFileReader {

    public static void readFeatureFile(String fileName){
        Path path ;
        //String data;
        Stream<String> lines ;
        try {
            URL url = FeatureFileReader.class.getClassLoader()
                    .getResource(fileName);
            Optional.ofNullable(url)
                    .orElseThrow(() -> new FileException(String.format("Unable to locate file %s", fileName)));
            path = Paths.get(url.toURI());
            lines = Files.lines(path);
            lines.forEach(line -> {
                Matcher stepMatcher = Pattern.compile("\\b(Scenario)(.*)").matcher(line);
                if (stepMatcher.find()) {
                    System.out.println(line);
                }
            });
            //data = lines.collect(Collectors.joining("\n"));
            lines.close();
        } catch (URISyntaxException | IOException e) {
            throw new FileException(e.getMessage(), e);
        }
        return;
    }


    public static void readFile() {
        Path path = Paths.get("src/main/java/resources/features/retrieve-orders.feature").toAbsolutePath();
        //try {
            File file = new File(path.toString());
            try {
                List<String> data = Files.readAllLines(path);

                data.forEach(line -> {
                    Matcher stepMatcher = Pattern.compile("\\b(Scenario)(.*)").matcher(line);
                    if (stepMatcher.find()) {
                        System.out.println(line);
                    }
                });
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        //}
    }

    public static void main(String[] args) {
        readFeatureFile("features/retrieve-orders.feature");
    }

}
