package com.aliyun.alink.linksdk.cmp.core.util;

import java.util.Random;

/* loaded from: classes2.dex */
public class RandomStringUtil {
    public static String getRandomString(int i2) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < i2; i3++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62)));
        }
        return stringBuffer.toString();
    }
}
