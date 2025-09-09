package com.aliyun.alink.linksdk.channel.core.b;

import java.util.Random;
import java.util.UUID;

/* loaded from: classes2.dex */
public class c {
    public static String a() {
        try {
            return UUID.randomUUID().toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return String.valueOf(new Random().nextInt());
        }
    }

    public static String b() {
        return String.valueOf(a().hashCode());
    }
}
