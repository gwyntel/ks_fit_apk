package com.xiaomi.infra.galaxy.fds.android.util;

/* loaded from: classes4.dex */
public class Args {
    public static void check(boolean z2, String str) {
        if (!z2) {
            throw new IllegalArgumentException(str);
        }
    }

    public static <T extends CharSequence> T notEmpty(T t2, String str) {
        if (t2 == null) {
            throw new IllegalArgumentException(str + " may not be null");
        }
        if (t2.length() != 0) {
            return t2;
        }
        throw new IllegalArgumentException(str + " may not be empty");
    }

    public static int notNegative(int i2, String str) {
        if (i2 >= 0) {
            return i2;
        }
        throw new IllegalArgumentException(str + " may not be negative");
    }

    public static <T> T notNull(T t2, String str) {
        if (t2 != null) {
            return t2;
        }
        throw new IllegalArgumentException(str + " may not be null");
    }

    public static int positive(int i2, String str) {
        if (i2 > 0) {
            return i2;
        }
        throw new IllegalArgumentException(str + " may not be negative or zero");
    }

    public static void check(boolean z2, String str, Object... objArr) {
        if (!z2) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
    }

    public static long notNegative(long j2, String str) {
        if (j2 >= 0) {
            return j2;
        }
        throw new IllegalArgumentException(str + " may not be negative");
    }

    public static long positive(long j2, String str) {
        if (j2 > 0) {
            return j2;
        }
        throw new IllegalArgumentException(str + " may not be negative or zero");
    }

    public static void check(boolean z2, String str, Object obj) {
        if (!z2) {
            throw new IllegalArgumentException(String.format(str, obj));
        }
    }
}
