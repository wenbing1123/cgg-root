package com.cgg.framework.redis;

import com.cgg.framework.exception.SysException;
import com.cgg.framework.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLockReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
public class RedisManagerImpl implements RedisManager {

    @Resource(name = "reactiveRedisTemplate")
    private ReactiveRedisTemplate<String, Object> redisTemplate;
    @Resource(name = "redissonReactive")
    private RedissonReactiveClient redissonClient;

    public Mono<Boolean> expire(String key, long expireTime) {
        checkArgument(expireTime > 0, "time must be ge zero");
        return redisTemplate.expire(key, Duration.ofSeconds(expireTime));
    }
    public Mono<Boolean> expireAt(String key, LocalDateTime time) {
        return redisTemplate.expireAt(key, DateUtils.toInstant(time));
    }

    public Mono<Duration> getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public Mono<Long> del(String... keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public <T> Mono<Boolean> set(String key, T value) {
        return redisTemplate.opsForValue().set(key, value);
    }


    public <T> Mono<Boolean> setEX(String key, T value, long expireTime) {
        if (expireTime > 0) {
            return redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(expireTime));
        }
        return set(key, value);
    }


    public <T> Mono<Boolean> setNX(String key, T value, long expireTime) {
        return redisTemplate.opsForValue().setIfPresent(key, value, Duration.ofMillis(expireTime));
    }

    @Override
    public Mono<Long> incr(String key, long delta) {
        checkArgument(delta > 0, "incr factor must be ge zero");
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Mono<Long> decr(String key, long delta) {
        checkArgument(delta > 0, "incr factor must be ge zero");
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    @Override
    public <V> Mono<V> get(String key) {
        checkArgument(StringUtils.isNotBlank(key), "key must be not blank");
        return (Mono<V>) redisTemplate.opsForValue().get(key);
    }

    @Override
    public Mono<Boolean> exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public <K, V> Mono<Boolean> hmset(String key, Map<K, V> map) {
        return redisTemplate.opsForHash().putAll(key, map);
    }


    public <K, V> Mono<Boolean> hmset(String key, Map<K, V> map, long expireTime) {
        Mono<Boolean> result = redisTemplate.opsForHash().putAll(key, map);
        if (expireTime > 0) {
            expire(key, expireTime);
        }
        return result;
    }

    public <K, V> Mono<Boolean> hset(String key, K item, V value) {
        return redisTemplate.opsForHash().put(key, item, value);
    }

    public <K, V> Mono<Boolean> hset(String key, K item, V value, long expireTime) {
        Mono<Boolean> result = redisTemplate.opsForHash().put(key, item, value);
        if (expireTime > 0) {
            expire(key, expireTime);
        }
        return result;
    }

    public <HK, HV> Flux<Map.Entry<HK, HV>> hmget(String key) {
        ReactiveHashOperations<String, HK, HV> operations = redisTemplate.opsForHash();
        return operations.entries(key);
    }

    public <K, V> Mono<V> hget(String key, K item) {
        return (Mono<V>) redisTemplate.opsForHash().get(key, item);
    }

    public Mono<Boolean> hdel(String key) {
        return redisTemplate.opsForHash().delete(key);
    }

    public <HK> Mono<Long> hremove(String key, HK... hashKeys) {
        return redisTemplate.opsForHash().remove(key, hashKeys);
    }

    public <K> Mono<Boolean> hHasKey(String key, K item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    public <K> Mono<Double> hincr(String key, K item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    public <K> Mono<Double> hdecr(String key, K item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    public <T> Mono<Long> sset(String key, T... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public <T> Mono<Long> sset(String key, long expireTime, T... values) {
        Mono<Long> count = redisTemplate.opsForSet().add(key, values);
        if (expireTime > 0) {
            expire(key, expireTime);
        }
        return count;
    }

    public <T> Flux<T> sget(String key) {
        return (Flux<T>) redisTemplate.opsForSet().members(key);
    }

    public Mono<Long> sgetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public <T> Mono<Boolean> sisMember(String key, T item) {
        return redisTemplate.opsForSet().isMember(key, item);
    }

    public <T> Mono<Long> sremove(String key, T... items) {
        return redisTemplate.opsForSet().remove(key, items);
    }

    public <T> Mono<Boolean> zsset(String key, T value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public <T> Mono<Long> zsset(String key, Map<T, Double> values) {
        checkArgument(MapUtils.isNotEmpty(values), "param values is empty");
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>(values.size());
        values.entrySet().forEach(entry -> tuples.add(new DefaultTypedTuple<>(entry.getKey(), entry.getValue())));
        return redisTemplate.opsForZSet().addAll(key, tuples);
    }

    public <T> Mono<Double> zsincr(String key, T member, long delta) {
        return redisTemplate.opsForZSet().incrementScore(key, member, delta);
    }

    public <T> Mono<Double> zsgetScore(String key, T member) {
        return redisTemplate.opsForZSet().score(key, member);
    }

    public <V> Flux<V> zsgetRank(String key, long start, long end) {
        Flux<ZSetOperations.TypedTuple<Object>> value = redisTemplate.opsForZSet().rangeWithScores(key, Range.open(start, end));
        return value.map(ZSetOperations.TypedTuple::getValue).map(x -> (V) x);
    }

    public <T> Mono<Long> lset(String key, T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public <T> Mono<Long> lset(String key, T value, long expireTime) {
        Mono<Long> result = redisTemplate.opsForList().rightPush(key, value);
        if (expireTime > 0) {
            expire(key, expireTime);
        }
        return result;
    }

    public <T> Mono<Long> lset(String key, List<T> valueList) {
        return redisTemplate.opsForList().rightPushAll(key, valueList);
    }

    public <T> Mono<Long> lset(String key, List<T> valueList, long expireTime) {
        Mono<Long> result = redisTemplate.opsForList().rightPushAll(key, valueList);
        if (expireTime > 0) {
            expire(key, expireTime);
        }
        return result;
    }


    public <T> void lset(String key, int index, T value) {
        redisTemplate.opsForList().set(key, index, value);
    }


    public <T> Flux<T> lget(String key, long start, long end) {
        return (Flux<T>) redisTemplate.opsForList().range(key, start, end);
    }


    public Mono<Long> lgetSize(String key) {
        return redisTemplate.opsForList().size(key);
    }


    public <T> Mono<T> lgetByIndex(String key, long index) {
        return (Mono<T>) redisTemplate.opsForList().index(key, index);
    }

    public <T> Mono<Long> lremove(String key, long count, T value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    // lock
    @Override
    public <T> Mono<T> lock(String lockKey, Supplier<Mono<T>> supplier) {
        RLockReactive lock = redissonClient.getLock(lockKey);
        return Mono.defer(() -> lock
                .lock()
                .doFinally(x -> lock.unlock())
                .flatMap(x -> supplier.get())
                .subscribeOn(Schedulers.boundedElastic()));
    }

    @Override
    public <T> Mono<T> tryLock(String lockKey, Supplier<Mono<T>> supplier) {
        RLockReactive lock = redissonClient.getLock(lockKey);
        return Mono.defer(() -> lock
                .tryLock()
                .onErrorResume(Mono::error)
                .doFinally(x -> lock.unlock())
                .filter(BooleanUtils::isTrue)
                .switchIfEmpty(Mono.error(new SysException("try lock fail")))
                .flatMap(x -> supplier.get())
                .subscribeOn(Schedulers.boundedElastic()));
    }

    @Override
    public <T> Mono<T> tryLock(String lockKey, long waitMilliseconds, Supplier<Mono<T>> supplier) {
        RLockReactive lock = redissonClient.getLock(lockKey);
        return Mono.defer(() -> lock
                .tryLock(waitMilliseconds, TimeUnit.MILLISECONDS)
                .onErrorResume(Mono::error)
                .doFinally(x -> lock.unlock())
                .filter(BooleanUtils::isTrue)
                .switchIfEmpty(Mono.error(new SysException("try lock fail")))
                .flatMap(x -> supplier.get())
                .subscribeOn(Schedulers.boundedElastic()));
    }

    @Override
    public Mono<Long> incWindowCount(String key, long windowSeconds, long maxCount) {
        long currentMs = System.currentTimeMillis();
        long maxScoreMs = currentMs - windowSeconds * 1000;
        return redisTemplate.execute(RedisScript.of(INC_WINDOW_COUNT_SCRIPT, Long.class), Collections.singletonList(key), Arrays.asList(currentMs, maxScoreMs, windowSeconds, maxCount)).single();
    }

    @Override
    public Mono<Long> getWindowCount(String key) {
        return redisTemplate.execute(connection -> connection.zSetCommands().zCard(ByteBuffer.wrap(key.getBytes(StandardCharsets.UTF_8)))).single();
    }

    private static final String INC_WINDOW_COUNT_SCRIPT =
            "local winKey=KEYS[1] " +
                    "local currentMs=ARGV[1] " +
                    "local maxScoreMs=ARGV[2] " +
                    "local windowSeconds=ARGV[3] " +
                    "local maxCount=ARGV[4] " +
                    "redis.call('ZREMRANGEBYSCORE', winKey, 0, maxScoreMs) " +
                    "local count = redis.call('ZCARD', winKey) " +
                    "if tonumber(count) >= tonumber(maxCount) then " +
                    "return count " +
                    "else " +
                    "redis.call('ZADD', winKey, currentMs, currentMs) " +
                    "redis.call('EXPIRE', winKey, windowSeconds) " +
                    "return (tonumber(count) + 1) " +
                    "end ";
}
