package com.chetan03tutorial.libs.aligator.util;

import com.chetan03tutorial.libs.aligator.exception.AligatorBddException;
import com.chetan03tutorial.libs.aligator.parameters.ServiceResponse;
import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Component
@ScenarioScope
public class Cache {

    private ServiceResponse response;

    private Map<String, LinkedList<ResponseCacheEntry>> cache;

    public Cache(){
        this.cache = new HashMap<>();
    }

    private void cacheEntry(String serviceName, ResponseCacheEntry entry){
        LinkedList<ResponseCacheEntry> list = Optional
                .ofNullable(cache.get(serviceName))
                .orElseGet(LinkedList::new);
        list.addLast(entry);
        cache.put(serviceName,list);
    }



    public void save(String serviceName, ServiceResponse response) {
        this.response = response;
        ResponseCacheEntry entry = new ResponseCacheEntry(this.response);
        cacheEntry(serviceName, entry);

    }

    public String get(String serviceName, int index, String jsonPath){
        LinkedList<ResponseCacheEntry> entries =  Optional.ofNullable(cache.get(serviceName))
                .orElseThrow(() -> new AligatorBddException(String.format("Could not find any response " +
                                "entry for the service = \"%s\", Was the service invoked ? ", serviceName)));
        ResponseCacheEntry cachedObj = entries.get(index);
        return JsonMapper.getValueAtJsonPath(cachedObj,jsonPath);
    }

    /*public ResponseCacheEntry getEntry(String serviceName, int index){
        LinkedList<ResponseCacheEntry> entries =  Optional.ofNullable(cache.get(serviceName))
                .orElseThrow( () -> new AligatorBddException(
                        String.format("Could not find any response " +
                                "entry for the service = \"%s\", Was the service invoked ? ", serviceName)));
        return entries.get(index);
    }*/

    public ServiceResponse getResponse(){
        return response;
    }

    @Getter
    @Setter
    private static class ResponseCacheEntry{
        private Map<String,Object> response;
        private Map<String,String> headers;

        public ResponseCacheEntry(ServiceResponse response){
            this.response = JsonMapper.getValueAsObject(response.getResponseBody(),HashMap.class);
            this.headers = response.getHeaders();
        }
    }



}
