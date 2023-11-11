package com.smt.libs.utils.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class JsonSerializer {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JsonSerializer.class);
    private static ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(new StdDateFormat());

    /**
     * used for unit tests
     */
    JsonSerializer(ObjectMapper objectMapper) {
        JsonSerializer.objectMapper = objectMapper;
    }

    public static boolean isJsonContainer(Object object) {
        try {
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(object));
            return jsonNode.isContainerNode();
        } catch (Exception ex) {
            LOGGER.error("Error checking serialized object type for class=" + object.getClass().getName(), ex);
            throw new JsonParserException("Error checking serialized object type for class=" + object.getClass().getName(), ex);
        }
    }

    public static String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            LOGGER.error("Error when converting name=" + object.getClass().getName(), ex);
            throw new JsonParserException("Error when converting name=" + object.getClass().getName(), ex);
        }
    }

    public static <T> T toObject(String jsonText, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonText, clazz);
        } catch (IOException ex) {
            LOGGER.error("Exception on converting to class=" + clazz.getName(), ex);
            throw new JsonParserException("Exception on converting to class=" + clazz.getName(), ex);
        }
    }

    public static <T> List<T> toListObject(String jsonText, Class<T> clazz) {
        try {

            return objectMapper.readValue(jsonText, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (IOException ex) {
            LOGGER.error("Exception on converting to class=" + clazz.getName(), ex);
            throw new JsonParserException("Exception on converting to class=" + clazz.getName(), ex);
        }
    }

    public static <T> T convertMapToPojo(Map<String, Object> inputMap, Class<T> clazz) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return objectMapper.convertValue(inputMap, clazz);
    }

    public static <T> T convertMapToPojoIgnoreUnknownFields(Map<String, Object> inputMap, Class<T> clazz) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.convertValue(inputMap, clazz);
    }

    public static <T> List<T> convertMapListToPojoListIgnoreUnknownFields(List<Map<String, Object>> inputMapList, Class<T> clazz) {
        return inputMapList.stream().map( inputMap -> convertMapToPojoIgnoreUnknownFields(inputMap, clazz)).toList();
    }

    public static <T> List<T> convertMapListToPojoList(List<Map<String, Object>> inputMapList, Class<T> clazz) {
        return inputMapList.stream().map( inputMap -> convertMapToPojo(inputMap, clazz)).toList();
    }

    public static HashMap<String, Object> convertPojoToMap(Object inputMap) {
        return objectMapper.convertValue(inputMap, HashMap.class);
    }
}
