package com.chetan03tutorial.libs.aligator.parameters;

import com.chetan03tutorial.libs.aligator.constant.GherkinParameterMnemonics;
import com.chetan03tutorial.libs.aligator.constant.RestProtocols;
import com.chetan03tutorial.libs.aligator.exception.FileException;
import com.chetan03tutorial.libs.aligator.exception.InvalidParameterException;
import com.chetan03tutorial.libs.aligator.exception.JsonParsingException;
import com.chetan03tutorial.libs.aligator.parsers.LiteralParameterParser;
import com.chetan03tutorial.libs.aligator.util.EndpointService;
import com.chetan03tutorial.libs.aligator.util.FileReader;
import com.chetan03tutorial.libs.aligator.util.JsonMapper;
import com.chetan03tutorial.libs.aligator.util.PropertyResolver;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Component
public class UrlParameter implements AligatorParameter {

    private final LiteralParameterParser parser;
    private static Map<String, Endpoint> endpoints;
    private static final String JSON_EXTENSION = ".json";
    private static final String DEFAULT_API_ENDPOINT_LOCATION = "service-config.json";

    public UrlParameter(LiteralParameterParser parser){
        this.parser = parser;
    }

    public void prepareContext(RequestContext context, String cellText) {
        List<Pair> params = parser.parse(cellText);
        if(params.size() !=1 ){
            throw new InvalidParameterException(
                    format("Invalid number of arguments provided for type %s, Maximum allowed argument is 1", getMnemonic()));
        }
        String serviceName = params.get(0).value;
        String endpoint =  EndpointService.getEndpoint(serviceName);
        context.setEndPoint(serviceName);
        context.setEndPoint(endpoint);
        context.setProtocol(RestProtocols.protocol(serviceName));
    }

    @Override
    public String getMnemonic() {
        return PropertyResolver.getApplicationProperty(GherkinParameterMnemonics.API_ENDPOINT);
    }


    /**
     * Method to set the service name when gherkin file contains the service name in the statement
     * for example :- "SampleService" is invoked, then serviceName passed would be SampleService
     * and user don't have to provide any service name in the datatable passed as argument.
     *
     */
    /*public void setEndpoint(RequestContext context, String serviceName){
        List<Pair> params = parser.parse(serviceName);
        if(params.size() !=1 ){
            throw new InvalidParameterException(
                    format("Invalid number of arguments provided for type %s, Maximum allowed argument is 1", getMnemonic()));
        }
        String service = params.get(0).getParamValue();
        Endpoint endpoint = Optional.ofNullable(endpoints.get(service))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid Service Name %s", serviceName)));
        context.setEndPoint(endpoint.endpoint);
        context.setProtocol(RestProtocols.protocol(endpoint.operation));
        //context.setServiceName(service);
        //request.setRequestMethod(RestProtocols.valueOf(endpoint.getOperation().toUpperCase()));
    }*/


    @EventListener
    public void init(ContextRefreshedEvent event)  {
        String content;
        String serviceConfig = DEFAULT_API_ENDPOINT_LOCATION;
        try{
            content = FileReader.readFile(serviceConfig);
        }catch (FileException ex){
            serviceConfig = PropertyResolver.getApplicationProperty("integration.test.service-config");
            if(StringUtils.isEmpty(serviceConfig)){
                throw new FileException("No service-config.json found in classpath, " +
                        "create service-config.json or provide a value for " +
                        "integration.test.service-config to set service configuration location");
            }
            StringBuilder sb = new StringBuilder(serviceConfig);
            if(! serviceConfig.endsWith(JSON_EXTENSION)) {
                sb.append(JSON_EXTENSION);
            }
            content = FileReader.readFile(sb.toString());
        }
        try {
            Endpoint[] endpoint = JsonMapper.getJsonReader(Endpoint[].class).readValue(content);
            endpoints = Arrays.stream(endpoint).collect(Collectors.toMap(e -> e.getName().toUpperCase(), Function.identity()));
        } catch (IOException e) {
            throw new JsonParsingException(String.format("Error in parsing service configuration file {}", serviceConfig),e);
        }
    }

    @Getter
    @Setter
    private static class Endpoint{
        String endpoint;
        String operation;
        String name;
    }
}
