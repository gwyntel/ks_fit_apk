package com.xiaomi.push;

import android.content.Context;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class dg {

    /* renamed from: a, reason: collision with root package name */
    private int f23577a;

    public dg(int i2) {
        this.f23577a = i2;
    }

    public abstract String a(Context context, String str, List<bf> list);

    /* renamed from: a, reason: collision with other method in class */
    public boolean m280a(Context context, String str, List<bf> list) {
        return true;
    }

    public int a() {
        return this.f23577a;
    }
}
