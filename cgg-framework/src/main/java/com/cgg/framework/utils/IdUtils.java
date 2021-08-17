package com.cgg.framework.utils;

import com.cgg.framework.config.properties.NodeProperties;
import com.cgg.framework.exception.SysException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

public class IdUtils {

    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String bizNo(String bizCode) {
        return bizCode +
                DateUtils.format(LocalDateTime.now(), DateUtils.NON_SEP_DATETIME) +
                StringUtils.leftPad(String.valueOf(redisTemplate.opsForValue().increment(REDIS_KEY_NO_SEQ)), 6, '0');
    }

    public static long snowflakeID() {
        return snowflake.nextId();
    }

    private static final RedisTemplate<String, Long> redisTemplate = SpringContextHolder.getBean(RedisTemplate.class);
    private static final Snowflake snowflake = new Snowflake();
    private static final String REDIS_KEY_NO_SEQ = "common:bizSeq";

}

class Snowflake {

    /** 开始时间截 (2021-04-13) */
    private final long twepoch = 1618243200000L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    public Snowflake() {
        this.datacenterId = 1L;
        this.workerId = SpringContextHolder.getBean(NodeProperties.class).getId();
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new SysException( String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        sequence = (sequence + 1) & sequenceMask;

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
