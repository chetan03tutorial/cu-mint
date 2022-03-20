package com.chetan03tutorial.libs.aligator.util;

import com.chetan03tutorial.libs.aligator.exception.AligatorBddException;
import com.chetan03tutorial.libs.aligator.exception.FileException;
import com.chetan03tutorial.libs.aligator.exception.JsonParsingException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public class EndpointService {

    private static final Logger log = LoggerFactory.getLogger(EndpointService.class);
    private static Map<String, Endpoint> endpoints;
    private static final String JSON_EXTENSION = ".json";
    private static final String DEFAULT_API_ENDPOINT_LOCATION = "alligator.service.config";

    @EventListener
    public void init(ContextRefreshedEvent event)  {
        String content;
        String serviceConfig = PropertyResolver.getApplicationProperty(DEFAULT_API_ENDPOINT_LOCATION);
        try{
            content = FileReader.readFile(serviceConfig);
        }catch (FileException ex){
            /*serviceConfig = PropertyResolver.getApplicationProperty(DEFAULT_API_ENDPOINT_LOCATION);
            serviceConfig = serviceConfig.endsWith(JSON_EXTENSION) ? serviceConfig : serviceConfig.concat(JSON_EXTENSION);
            log.debug(String.format("Reading Service Configuration File %s", serviceConfig));
            if(StringUtils.isEmpty(serviceConfig)){
                throw new FileException("No service-config.json found in classpath, " +
                        "create service-config.json or provide a value for " +
                        "integration.test.service-config to set service configuration location");
            }
            try{
                content = FileReader.readFile(serviceConfig);
            }catch (FileException e){
                throw new FileException("No service-config.json found in classpath, " +
                        "create service-config.json or provide a value for " +
                        "integration.test.service-config to set service configuration location");
            }*/
            throw new FileException("No service-config.json found in classpath, " +
                    "create service-config.json or provide a value for " +
                    "alligator.service.config to set the location of service configuration file");

        }
        try{
            Endpoint[] endpoint = JsonMapper.getJsonReader(Endpoint[].class).readValue(content);
            endpoints = Arrays.stream(endpoint)
                    .collect(toMap(e -> e.name.toUpperCase(), Function.identity()));
        }catch (IOException e) {
            log.error(String.format("Cannot parse the service configuration file %s", serviceConfig) , e.getCause());
            throw new JsonParsingException(String.format("Error in parsing service configuration file %s", serviceConfig),e);
        }
    }

    public static String getEndpoint(String serviceName){
        Endpoint e = Optional.ofNullable(endpoints.get(serviceName.toUpperCase()))
                .orElseThrow(() -> new AligatorBddException(String.format("Invalid Service name %s", serviceName)));
        return e.getEndpoint();
    }

    public static String getOperation(String serviceName){
        Endpoint e = Optional.ofNullable(endpoints.get(serviceName.toUpperCase()))
                .orElseThrow(() -> new AligatorBddException(String.format("Invalid Service name %s", serviceName)));
        return e.getOperation();
    }

    @Getter
    @Setter
    private static class Endpoint{
        String endpoint;
        String operation;
        String name;
    }
}
