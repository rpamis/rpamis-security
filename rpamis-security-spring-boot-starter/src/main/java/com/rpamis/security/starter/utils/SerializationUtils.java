package com.rpamis.security.starter.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 序列化工具类，用于深拷贝
 *
 * @author benym
 * @date 2023/8/31 17:35
 */
public class SerializationUtils {

    private SerializationUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializationUtils.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        MAPPER.registerModule(javaTimeModule);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    /**
     * 对象转json
     *
     * @param obj obj
     * @return json string
     */
    private static String toJson(Object obj) throws JsonProcessingException {
        return MAPPER.writeValueAsString(obj);
    }


    /**
     * json转Object
     *
     * @param json  json
     * @param clazz clazz
     * @param <T>   clazz
     * @return obj
     */
    private static <T> T toObj(String json, Class<T> clazz) throws JsonProcessingException {
        return MAPPER.readValue(json, clazz);
    }

    /**
     * 对象深度克隆
     * 复制待加解密的对象，避免对源对象的影响
     * 避免可能存在save操作之后还会对源对象进行操作，如果直接替换了源对象的值，则后续字段为加密的
     *
     * @param param param
     * @return Object
     */
    public static Object deepClone(Object param) {
        try {
            if (param instanceof List) {
                List<?> paramList = (List<?>) param;
                List<Object> deepCloneList = new ArrayList<>();
                for (Object paramObject : paramList) {
                    String tempSerializeString = toJson(paramObject);
                    Object deepCloneSingle = toObj(tempSerializeString, paramObject.getClass());
                    deepCloneList.add(deepCloneSingle);
                }
                return deepCloneList;
            } else {
                String tempSerializeString = toJson(param);
                return toObj(tempSerializeString, param.getClass());
            }
        } catch (Exception e) {
            LOGGER.warn("deep clone class {} failed", param.getClass().getName());
        }
        return null;
    }
}
