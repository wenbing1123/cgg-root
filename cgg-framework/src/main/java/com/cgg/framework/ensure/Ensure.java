package com.cgg.framework.ensure;

import com.cgg.framework.exception.ArgumentException;
import com.cgg.framework.exception.BaseException;
import com.cgg.framework.exception.DataDuplicateException;
import com.cgg.framework.exception.DataNotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

public class Ensure {

    public static void paramNotNull(Object obj, String msg) {
        param(Objects.isNull(obj), msg);
    }

    public static void paramNotBlank(String str, String msg) {
        param(StringUtils.isNotBlank(str), msg);
    }

    public static void paramNotEmpty(Collection collection, String msg) {
        param(CollectionUtils.isNotEmpty(collection), msg);
    }

    public static void param(boolean result) {
        if (!result) {
            throw new ArgumentException();
        }
    }

    public static void param(boolean result, String msg) {
        if (!result) {
            throw new ArgumentException(msg);
        }
    }

    /**
     * 确保数据存在
     */
    public static void dataExist(boolean result, String msg) {
        if (!result) {
            throw new DataNotFoundException(msg);
        }
    }

    /**
     * 确保数据不重复
     */
    public static void dataNotDuplicate(boolean result, String msg) {
        if (!result) {
            throw new DataDuplicateException(msg);
        }
    }

    /**
     * 抛出指定异常
     */
    public static void that(boolean result, BaseException exception) {
        if (!result) {
            throw exception;
        }
    }

}
