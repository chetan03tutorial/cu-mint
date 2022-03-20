package com.chetan03tutorial.libs.aligator.util;

import com.chetan03tutorial.libs.aligator.CucumberRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Component
public class PropertyResolver {

    private static Map<String,String> defaultProperties;
    private static Environment environment;
    private static final String ALIGATOR_PROPERTIES = "aligator.properties";


    public PropertyResolver(ApplicationContext applicationContext) {
        init();
        environment = applicationContext.getEnvironment();
    }

    private void init(){
        InputStream resourceAsStream = CucumberRunner.class
                .getResourceAsStream("/" + ALIGATOR_PROPERTIES);
        if (resourceAsStream == null) {
            System.exit(0);
        }
        try {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            defaultProperties =  new HashMap(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String getDefaultProperty(String key){
        return defaultProperties.get(key);
    }

    public static String getApplicationProperty(String key){
        System.out.println(String.format("Key is %s and value is %s" , key, environment.getProperty(key)));
        return Optional.ofNullable(environment.getProperty(key)).orElse(getDefaultProperty(key));
    }
}
