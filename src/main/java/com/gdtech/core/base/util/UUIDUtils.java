package com.gdtech.core.base.util;

import java.util.UUID;

/**
 * @author zhucy
 */
public class UUIDUtils {
    public static String genUID32() {
        String id = genUID();
        return id.replaceAll("[-]", "");
    }

    public static String genUID() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }
}
