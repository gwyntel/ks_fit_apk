package com.huawei.hms.scankit.p;

import java.security.SecureRandom;

/* loaded from: classes4.dex */
public class n6 {

    /* renamed from: a, reason: collision with root package name */
    private static final SecureRandom f17581a = new SecureRandom();

    public static int a(int i2) {
        return f17581a.nextInt(i2);
    }

    public static float a(float f2) {
        return f17581a.nextFloat() * f2;
    }
}
