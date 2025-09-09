package com.xiaomi.mipush.sdk;

import android.text.TextUtils;

/* loaded from: classes4.dex */
class x {

    /* renamed from: a, reason: collision with root package name */
    int f23426a = 0;

    /* renamed from: a, reason: collision with other field name */
    String f157a = "";

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof x)) {
            return false;
        }
        x xVar = (x) obj;
        return !TextUtils.isEmpty(xVar.f157a) && xVar.f157a.equals(this.f157a);
    }
}
