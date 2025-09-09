package com.xiaomi.push.service;

import com.xiaomi.push.it;
import com.xiaomi.push.iu;

/* loaded from: classes4.dex */
/* synthetic */ class bb {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ int[] f24491a;

    /* renamed from: b, reason: collision with root package name */
    static final /* synthetic */ int[] f24492b;

    static {
        int[] iArr = new int[iu.values().length];
        f24492b = iArr;
        try {
            iArr[iu.INT.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f24492b[iu.LONG.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f24492b[iu.STRING.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f24492b[iu.BOOLEAN.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        int[] iArr2 = new int[it.values().length];
        f24491a = iArr2;
        try {
            iArr2[it.MISC_CONFIG.ordinal()] = 1;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f24491a[it.PLUGIN_CONFIG.ordinal()] = 2;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
