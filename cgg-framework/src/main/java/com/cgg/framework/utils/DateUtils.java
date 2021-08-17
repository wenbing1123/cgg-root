package com.cgg.framework.utils;

import com.cgg.framework.exception.DataInvalidException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    
    public static final String STANDARD_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String STANDARD_DATE = "yyyy-MM-dd";
    public static final String STANDARD_TIME = "HH:mm:ss";
    public static final String NON_SEP_DATETIME = "yyyyMMddHHmmss";
    public static final String NON_SEP_DATE = "yyyyMMddHHmmss";
    public static final String NON_SEP_TIME = "yyyyMMddHHmmss";


    public static Date parse(String date) {
        try {
            return parseDate(date, STANDARD_DATETIME, STANDARD_DATE, STANDARD_TIME);
        } catch (ParseException e) {
            String msg = "解析日期失败";
            log.error(msg, e);
            throw new DataInvalidException(msg);
        }
    }

    public static LocalDateTime parseLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(STANDARD_TIME));
    }

    public static LocalDate parseLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(STANDARD_DATE));
    }

    public static LocalTime parseLocalTime(String date) {
        return LocalTime.parse(date, DateTimeFormatter.ofPattern(STANDARD_TIME));
    }

    public static Instant toInstant(LocalDateTime time) {
        return time.toInstant(ZoneOffset.ofHours(8));
    }

    public static Date toDate(LocalDateTime time) {
        return Date.from(toInstant(time));
    }

    public static long toLong(LocalDateTime time) {
        return toDate(time).getTime();
    }

    public static String format(LocalDateTime time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }

    public static String format(LocalDate time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }

    public static long minus(LocalDateTime time1, LocalDateTime time2) {
        return toLong(time1) - toLong(time2);
    }

}
