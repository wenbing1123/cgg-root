package com.cgg.framework.utils;

import java.util.UUID;

public class IdUtils {

    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
