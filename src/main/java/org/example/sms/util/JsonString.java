package org.example.sms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonString {

    public static <T> String toJsonString(T obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T toObject(String jsonStringValue, Class<?> cls) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return (T) mapper.readValue(jsonStringValue, cls);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
