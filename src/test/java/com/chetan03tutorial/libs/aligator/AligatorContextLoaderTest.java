package com.chetan03tutorial.libs.aligator;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;


import java.util.Arrays;
//TODO :- Run this using SpringExtension or Junit-5 extension
public class AligatorContextLoaderTest {

    private static final Logger log = LoggerFactory.getLogger(AligatorContextLoaderTest.class);
    ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(AligatorAutoConfiguration.class));

    @Test
    public void testApplicationContext() {
        runner.run(this::printBeans);
        //TODO :- Put an assertion here to check if the RequestContextBuilder and AligatorParameterHandler is loaded
    }

    private void printBeans(ApplicationContext context){
        Arrays.stream(context.getBeanDefinitionNames()).forEach(b -> log.debug(String.format("Loading Bean %s", b)));
    }
}

