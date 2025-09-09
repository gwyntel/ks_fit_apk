package com.xiaomi.push;

/* loaded from: classes4.dex */
public enum iu {
    INT(1),
    LONG(2),
    STRING(3),
    BOOLEAN(4);


    /* renamed from: a, reason: collision with other field name */
    private final int f608a;

    iu(int i2) {
        this.f608a = i2;
    }

    public static iu a(int i2) {
        if (i2 == 1) {
            return INT;
        }
        if (i2 == 2) {
            return LONG;
        }
        if (i2 == 3) {
            return STRING;
        }
        if (i2 != 4) {
            return null;
        }
        return BOOLEAN;
    }
}
