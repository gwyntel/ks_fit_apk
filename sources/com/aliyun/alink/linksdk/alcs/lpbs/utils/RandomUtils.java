package com.aliyun.alink.linksdk.alcs.lpbs.utils;

import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class RandomUtils {
    private static final String RANDOM_BYTE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    protected static SecureRandom sRandom = new SecureRandom();

    public static int getNextInt() {
        return sRandom.nextInt();
    }

    public static String getRandomString(int i2) {
        StringBuilder sb = new StringBuilder(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(RANDOM_BYTE.charAt(sRandom.nextInt(62)));
        }
        return sb.toString();
    }
}
