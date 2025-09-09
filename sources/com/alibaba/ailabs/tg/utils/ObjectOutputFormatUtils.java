package com.alibaba.ailabs.tg.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/* loaded from: classes2.dex */
public class ObjectOutputFormatUtils {
    private static final String LOG_PREFIX = "        ";

    public static String getString(Object obj, Class<?> cls) {
        String str = "";
        if (obj != null && cls != null) {
            try {
                str = cls.getSimpleName() + ":\n";
                for (Field field : cls.getDeclaredFields()) {
                    if (field != null) {
                        makeAccessible(field);
                        str = str + LOG_PREFIX + field.getName() + " = " + field.get(obj) + "\n";
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return str;
    }

    private static void makeAccessible(Field field) throws SecurityException {
        if (Modifier.isPublic(field.getModifiers()) && Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            return;
        }
        field.setAccessible(true);
    }
}
