package com.cgg.service.baseconfig.service;

import com.cgg.framework.exception.DataNotFoundException;
import com.cgg.framework.utils.DateUtils;
import com.cgg.service.baseconfig.api.ConfigService;
import com.cgg.service.baseconfig.dao.entity.Config;
import com.cgg.service.baseconfig.dao.repository.ConfigRepository;
import com.cgg.service.baseconfig.dto.response.ConfigResult;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

@Service("configService")
@Slf4j
public class ConfigServiceImpl implements ConfigService, InitializingBean {

    private static final Map<String, Config> CONFIG_CACHE = Maps.newHashMap();

    @Resource
    private ConfigRepository configRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshConfig().subscribe();
    }

    @Override
    public Mono<Boolean> refreshConfig() {
        return configRepository.findAll().collectMap(Config::getName).doOnNext(map -> {
            CONFIG_CACHE.clear();
            CONFIG_CACHE.putAll(map);
        }).thenReturn(true);

    }

    @Override
    public Mono<ConfigResult> get(String name) {
        return getCfg(name)
                .map(config -> ConfigResult
                        .builder()
                        .name(config.getName())
                        .value(config.getValue())
                        .remark(config.getRemark())
                        .build());
    }

    @Override
    public Mono<String> getAsString(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue);
    }

    @Override
    public Mono<Integer> getAsInteger(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue)
                .map(Integer::valueOf);
    }

    @Override
    public Mono<Long> getAsLong(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue)
                .map(Long::valueOf);
    }

    @Override
    public Mono<Float> getAsFloat(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue)
                .map(Float::valueOf);
    }

    @Override
    public Mono<Double> getAsDouble(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue)
                .map(Double::valueOf);
    }

    @Override
    public Mono<Boolean> getAsBoolean(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue)
                .map(Boolean::valueOf);
    }

    @Override
    public Mono<Date> getAsDate(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue)
                .map(DateUtils::parse);
    }

    @Override
    public Mono<LocalDate> getAsLocalDate(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue)
                .map(DateUtils::parseLocalDate);
    }

    @Override
    public Mono<LocalTime> getAsLocalTime(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue)
                .map(DateUtils::parseLocalTime);
    }

    @Override
    public Mono<LocalDateTime> getAsLocalDateTime(String name) {
        return getCfg(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException("未配置")))
                .map(Config::getValue)
                .map(DateUtils::parseLocalDateTime);
    }

    @Override
    public Mono<String> getAsString(String name, String defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    @Override
    public Mono<Integer> getAsInteger(String name, Integer defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .map(Integer::valueOf)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    @Override
    public Mono<Long> getAsLong(String name, Long defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .map(Long::valueOf)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    @Override
    public Mono<Float> getAsFloat(String name, Float defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .map(Float::valueOf)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    @Override
    public Mono<Double> getAsDouble(String name, Double defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .map(Double::valueOf)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    @Override
    public Mono<Boolean> getAsBoolean(String name, Boolean defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .map(Boolean::valueOf)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    @Override
    public Mono<Date> getAsDate(String name, Date defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .map(DateUtils::parse)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    @Override
    public Mono<LocalDate> getAsLocalDate(String name, LocalDate defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .map(DateUtils::parseLocalDate)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    @Override
    public Mono<LocalTime> getAsLocalTime(String name, LocalTime defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .map(DateUtils::parseLocalTime)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    @Override
    public Mono<LocalDateTime> getAsLocalDateTime(String name, LocalDateTime defaultValue) {
        return getCfg(name)
                .map(Config::getValue)
                .map(DateUtils::parseLocalDateTime)
                .switchIfEmpty(Mono.just(defaultValue));
    }

    private Mono<Config> getCfg(String name) {
        return Mono.justOrEmpty(CONFIG_CACHE.get(name));
    }
}
