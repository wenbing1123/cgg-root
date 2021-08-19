package com.cgg.framework.utils;

import com.cgg.framework.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BeanUtils {

    private static final Map<String, BeanCopier> COPIER_MAP = new HashMap<>();


    public static void copy(Object source, Object target) {
        BeanCopier copier = getCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    public static <T> T copy(Object source, Class<T> targetClass) {
        BeanCopier copier = getCopier(source.getClass(), targetClass);
        try {
            T t = targetClass.newInstance();
            copier.copy(source, t, null);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            String msg = "对象创建失败";
            log.error(msg, e);
            throw new SysException(msg);
        }
    }

    private static BeanCopier getCopier(Class<?> sourceClass, Class<?> targetClass) {
        String key = generateMapKey(sourceClass, targetClass);
        BeanCopier copier = COPIER_MAP.get(key);
        if (copier == null) {
            synchronized (BeanUtils.class) {
                if (copier == null) {
                    copier = BeanCopier.create(sourceClass, targetClass, true);
                    COPIER_MAP.put(key, copier);
                }
            }
        }
        return copier;
    }

    private static  String generateMapKey(Class<?> sourceClass, Class<?> targetClass) {
        return sourceClass.getName() + ":" + targetClass.getName();
    }

}
