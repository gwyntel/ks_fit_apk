package com.crazecoder.flutterbugly.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class MapUtil {
    private static final Set<Class<?>> VALUE_CLASSES = Collections.unmodifiableSet(new HashSet(Arrays.asList(Object.class, String.class, Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class)));

    public static Map<String, Object> deepToMap(Object obj) throws SecurityException, IllegalArgumentException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            putValues(obj, linkedHashMap, null);
            return linkedHashMap;
        } catch (IllegalAccessException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    private static boolean isValue(Object obj) {
        return obj == null || (obj instanceof Enum) || VALUE_CLASSES.contains(obj.getClass());
    }

    private static void putValues(Object obj, Map<String, Object> map, String str) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (obj == null) {
            return;
        }
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (!field.isSynthetic() && !Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                Object obj2 = field.get(obj);
                String name = str == null ? field.getName() : str + "." + field.getName();
                if (isValue(obj2)) {
                    map.put(name, obj2);
                } else {
                    putValues(obj2, map, name);
                }
            }
        }
    }
}
