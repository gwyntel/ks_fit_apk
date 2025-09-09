package com.meizu.cloud.pushsdk.f.g;

/* loaded from: classes4.dex */
public final class d {
    public static <T> T a(T t2) {
        t2.getClass();
        return t2;
    }

    public static <T> T a(T t2, Object obj) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    public static void a(boolean z2, Object obj) {
        if (!z2) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }
}
