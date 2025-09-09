package com.xiaomi.push;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class be {

    /* renamed from: a, reason: collision with root package name */
    public int f23488a;

    /* renamed from: a, reason: collision with other field name */
    public String f207a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f208a = new HashMap();

    public String a() {
        return this.f207a;
    }

    public String toString() {
        return String.format("resCode = %1$d, headers = %2$s, response = %3$s", Integer.valueOf(this.f23488a), this.f208a.toString(), this.f207a);
    }
}
