package com.alipay.android.phone.mrpc.core;

import android.content.Context;

/* loaded from: classes2.dex */
public final class s {

    /* renamed from: a, reason: collision with root package name */
    public static Boolean f9040a;

    public static final boolean a(Context context) {
        Boolean bool = f9040a;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            boolean z2 = (context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) != 0;
            f9040a = Boolean.valueOf(z2);
            return z2;
        } catch (Exception unused) {
            return false;
        }
    }
}
