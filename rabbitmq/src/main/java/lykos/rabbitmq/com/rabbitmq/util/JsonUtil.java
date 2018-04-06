package lykos.rabbitmq.com.rabbitmq.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * @author yanfenglin
 * @version 2018/4/3 17:31
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String toJsonString(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("to json string error:{}.", obj, e);
            return "";
        }
    }

    public static <T> Optional<T> parseJson(String json, Class<T> objClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.ofNullable(objectMapper.readValue(json, objClass));
        } catch (IOException e) {
            logger.warn("parse json error:{}.", json, e);
            return Optional.empty();
        }
    }
}
