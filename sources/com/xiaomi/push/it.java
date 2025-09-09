package com.xiaomi.push;

/* loaded from: classes4.dex */
public enum it {
    MISC_CONFIG(1),
    PLUGIN_CONFIG(2);


    /* renamed from: a, reason: collision with other field name */
    private final int f606a;

    it(int i2) {
        this.f606a = i2;
    }

    public int a() {
        return this.f606a;
    }

    public static it a(int i2) {
        if (i2 == 1) {
            return MISC_CONFIG;
        }
        if (i2 != 2) {
            return null;
        }
        return PLUGIN_CONFIG;
    }
}
