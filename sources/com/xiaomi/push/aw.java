package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
class aw {

    /* renamed from: a, reason: collision with root package name */
    static int f23462a;

    public static ar a(Context context) {
        if (j.m549a()) {
            f23462a = 1;
            return new av(context);
        }
        if (ao.a(context)) {
            f23462a = 2;
            return new ao(context);
        }
        if (ay.a(context)) {
            f23462a = 4;
            return new ay(context);
        }
        if (bc.a(context)) {
            f23462a = 5;
            return new bc(context);
        }
        if (au.a(context)) {
            f23462a = 3;
            return new as(context);
        }
        f23462a = 0;
        return new bb();
    }
}
