package com.chetan03tutorial.libs.aligator.util;

import com.chetan03tutorial.libs.aligator.exception.JsonParsingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is a facade to work with Jackson Object Mapper
 * for serializing and deserializing the objects/string.
 * This is a thread-safe class using ObjectWriter and ObjectReader.
 *
 * All instance of the JsonMapper would share the same instance of the object mapper.
 * Hence, ObjectMapper heavy lifting is done just once.
 */
public class JsonMapper {

    private JsonMapper(){}

    private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();

    public static ObjectWriter getJsonWriter() {
        return jacksonObjectMapper.writer();
    }

    public static <T> ObjectReader getJsonReader(Class<T> clazz) {
        return jacksonObjectMapper.readerFor(clazz);
    }

    public static String getValueAsString(Object object, SerializationFeature feature) {
        try {
            return getJsonWriter().with(feature).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(e);
        }
    }

    public static <T> T getValueAsObject(String data, Class<T> myClass) {
        try {
            return getJsonReader(myClass).readValue(data);
        } catch (IOException e) {
            throw new JsonParsingException(e);
        }
    }

    public static <T> T getValueAsObject(InputStream data, Class<T> myClass){
        try {
            return getJsonReader(myClass).readValue(data);
        } catch (IOException e) {
            throw new JsonParsingException(e);
        }
    }

    public static String getValueAsString(Object object){
        try {
            return getJsonWriter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(e);
        }
    }

    public static String getValueAtJsonPath(Object o, String jsonPointer) {
        String jsonStr = getValueAsString(o);
        try {
            JsonNode jn = jacksonObjectMapper.readTree(jsonStr);
            return jn.at(jsonPointer).textValue();
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(e);
        }
    }




}
