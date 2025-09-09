package com.alibaba.sdk.android.httpdns.net64;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class a implements Net64Service {

    /* renamed from: a, reason: collision with root package name */
    private b f8877a;

    /* renamed from: a, reason: collision with other field name */
    private ConcurrentHashMap<String, List<String>> f33a;

    /* renamed from: o, reason: collision with root package name */
    private boolean f8878o;

    /* renamed from: p, reason: collision with root package name */
    private volatile boolean f8879p;

    /* renamed from: com.alibaba.sdk.android.httpdns.net64.a$a, reason: collision with other inner class name */
    private static final class C0027a {

        /* renamed from: a, reason: collision with root package name */
        private static final a f8880a = new a();
    }

    private a() {
        this.f8877a = new b();
        this.f33a = new ConcurrentHashMap<>();
    }

    public static a a() {
        return C0027a.f8880a;
    }

    @Override // com.alibaba.sdk.android.httpdns.net64.Net64Service
    public void enableIPv6(boolean z2) {
        this.f8878o = z2;
    }

    @Override // com.alibaba.sdk.android.httpdns.net64.Net64Service
    public String getIPv6ByHostAsync(String str) {
        List<String> list;
        if (this.f8878o && (list = this.f33a.get(str)) != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean i() {
        return this.f8879p;
    }

    public List<String> a(String str) {
        return this.f33a.get(str);
    }

    public void a(String str, List<String> list) {
        this.f33a.put(str, list);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m47a() {
        return this.f8878o;
    }
}
