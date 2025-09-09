package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public enum u4 {
    TERMINATOR(new int[]{0, 0, 0}, 0),
    NUMERIC(new int[]{10, 12, 14}, 1),
    ALPHANUMERIC(new int[]{9, 11, 13}, 2),
    STRUCTURED_APPEND(new int[]{0, 0, 0}, 3),
    BYTE(new int[]{8, 16, 16}, 4),
    ECI(new int[]{0, 0, 0}, 7),
    KANJI(new int[]{8, 10, 12}, 8),
    FNC1_FIRST_POSITION(new int[]{0, 0, 0}, 5),
    FNC1_SECOND_POSITION(new int[]{0, 0, 0}, 9),
    HANZI(new int[]{8, 10, 12}, 13);


    /* renamed from: a, reason: collision with root package name */
    private final int[] f17858a;

    /* renamed from: b, reason: collision with root package name */
    private final int f17859b;

    u4(int[] iArr, int i2) {
        this.f17858a = iArr;
        this.f17859b = i2;
    }

    public static u4 a(int i2) {
        if (i2 == 0) {
            return TERMINATOR;
        }
        if (i2 == 1) {
            return NUMERIC;
        }
        if (i2 == 2) {
            return ALPHANUMERIC;
        }
        if (i2 == 3) {
            return STRUCTURED_APPEND;
        }
        if (i2 == 4) {
            return BYTE;
        }
        if (i2 == 5) {
            return FNC1_FIRST_POSITION;
        }
        if (i2 == 7) {
            return ECI;
        }
        if (i2 == 8) {
            return KANJI;
        }
        if (i2 == 9) {
            return FNC1_SECOND_POSITION;
        }
        if (i2 == 13) {
            return HANZI;
        }
        throw new IllegalArgumentException();
    }

    public int a(b8 b8Var) {
        int iF = b8Var.f();
        return this.f17858a[iF <= 9 ? (char) 0 : iF <= 26 ? (char) 1 : (char) 2];
    }

    public int a() {
        return this.f17859b;
    }
}
