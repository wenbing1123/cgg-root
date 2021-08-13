package com.cgg.framework.redis;

import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public interface RedisManager {

    // 增加key
    <T> Mono<Boolean> set(String key, T value);
    <T> Mono<Boolean> setEX(String key, T value, long expireTime);

    // 查询key
    <V> Mono<V> get(String key);
    Mono<Boolean> exists(String key);

    // 累加key
    Mono<Long> incr(String key, long delta);
    Mono<Long> decr(String key, long delta);

    //删除 key
    Mono<Long> del(String... keys);

    // reactive distribute lock
    <T> Mono<T> lock(String lockKey, Supplier<Mono<T>> supplier); //阻塞获取锁
    <T> Mono<T> tryLock(String lockKey, Supplier<Mono<T>> supplier); //非阻塞获取锁
    <T> Mono<T> tryLock(String lockKey, long waitMilliseconds, Supplier<Mono<T>> supplier); //阻塞指定时间（毫秒）获取锁

    // 滑窗计数
    Mono<Long> incWindowCount(String key, long windowSeconds, long maxCount);
    Mono<Long> getWindowCount(String key);
}
