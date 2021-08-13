package com.cgg.framework.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author lovelyDog
 * @date 2019/8/16.
 */
@Slf4j
public class JacksonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // 通用配置
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        mapper.setDateFormat(new SimpleDateFormat(DateUtils.STANDARD_DATETIME));
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);
        // 序列化配置
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 反序列化配置
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String writeValueAsString(T bean) {
        try {
            return mapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("to json error", e);
        }
    }

    public static <T> T readValueToBean(String jsonStr, Class<T> objClass) {
        try {
            return mapper.readValue(jsonStr, objClass);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("json to bean error", e);
        }
    }
}
