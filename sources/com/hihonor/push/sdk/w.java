package com.hihonor.push.sdk;

import android.text.TextUtils;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class w {

    /* renamed from: a, reason: collision with root package name */
    public final String f15558a;

    /* renamed from: b, reason: collision with root package name */
    public final int f15559b;

    public w(String str) {
        this.f15558a = str;
        this.f15559b = a(str);
    }

    public static w a(String str) {
        return new w(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || w.class != obj.getClass()) {
            return false;
        }
        return TextUtils.equals(this.f15558a, ((w) obj).f15558a);
    }

    public final int hashCode() {
        return this.f15559b;
    }

    public static int a(Object... objArr) {
        return Arrays.hashCode(objArr);
    }
}
