package com.cgg.framework.converter;

import com.cgg.framework.utils.BeanUtils;

public interface Converter {

    default <T> T covt(Class<T> clazz) {
        return BeanUtils.copy(this, clazz);
    }

}
