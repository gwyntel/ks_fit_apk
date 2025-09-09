package com.alipay.sdk.m.l0;

import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    public static final Pattern f9486a = Pattern.compile("([\t\r\n])+");

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m56a(String str) {
        return str == null || str.length() <= 0;
    }

    public static int a(String str) {
        if (str.length() <= 0) {
            return 0;
        }
        int i2 = 0;
        for (char c2 : str.toCharArray()) {
            i2 = (i2 * 31) + c2;
        }
        return i2;
    }
}
