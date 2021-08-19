package com.cgg.service.baseconfig.api;

import com.cgg.service.baseconfig.dto.response.ConfigResult;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public interface ConfigService {

    /**
     * 刷新配置
     */
    Mono<Boolean> refreshConfig();
    Mono<ConfigResult> get(String name);

    Mono<String> getAsString(String name);
    Mono<String> getAsString(String name, String defaultValue);
    Mono<Integer> getAsInteger(String name);
    Mono<Integer> getAsInteger(String name, Integer defaultValue);
    Mono<Long> getAsLong(String name);
    Mono<Long> getAsLong(String name, Long defaultValue);
    Mono<Float> getAsFloat(String name);
    Mono<Float> getAsFloat(String name, Float defaultValue);
    Mono<Double> getAsDouble(String name);
    Mono<Double> getAsDouble(String name, Double defaultValue);
    Mono<Boolean> getAsBoolean(String name);
    Mono<Boolean> getAsBoolean(String name, Boolean defaultValue);
    Mono<Date> getAsDate(String name);
    Mono<Date> getAsDate(String name, Date defaultValue);
    Mono<LocalDate> getAsLocalDate(String name);
    Mono<LocalDate> getAsLocalDate(String name, LocalDate defaultValue);
    Mono<LocalTime> getAsLocalTime(String name);
    Mono<LocalTime> getAsLocalTime(String name, LocalTime defaultValue);
    Mono<LocalDateTime> getAsLocalDateTime(String name);
    Mono<LocalDateTime> getAsLocalDateTime(String name, LocalDateTime defaultValue);

}
