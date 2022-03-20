package com.chetan03tutorial.libs.aligator;

import io.cucumber.core.cli.CommandlineOptions;
import io.cucumber.core.cli.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CucumberRunner {
    private static final Logger log = LoggerFactory.getLogger(CucumberRunner.class);
    private static final String COMMON_STEPS_GLUE_PATH_FILE_NAME = "aligator.properties";
    private static final String GLUE_PATH = "alligator.glue.path";

    public static void run(String[] args){
        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        HashMap<String,String> aligatorProperties;
        InputStream resourceAsStream = CucumberRunner.class
                .getResourceAsStream("/" + COMMON_STEPS_GLUE_PATH_FILE_NAME);
        if (resourceAsStream == null) {
            log.error("Unable to detect the default glue path file name, terminating the process");
            System.exit(0);
        }
        try {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            aligatorProperties =  new HashMap(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        arguments.add(CommandlineOptions.GLUE);
        arguments.add(aligatorProperties.get(GLUE_PATH));
        log.debug("Glue Path is " + aligatorProperties.get(GLUE_PATH));
        int exitStatus = 0;
        try{
            exitStatus = Main.run(arguments.toArray(new String[arguments.size()]));
        }catch (Exception ex){
            log.error("Exception While Running Cucumber Thread Class", ex.getMessage());
            System.exit(-1);
        }
        System.exit(exitStatus);
    }

    public static void main(String[] args){
        run(args);
    }
}
