package com.meizu.cloud.pushsdk.handler.e.h;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private long f19693a;

    /* renamed from: b, reason: collision with root package name */
    private int f19694b;

    /* renamed from: c, reason: collision with root package name */
    private List<String> f19695c;

    /* renamed from: d, reason: collision with root package name */
    private List<String> f19696d;

    /* renamed from: e, reason: collision with root package name */
    private List<a> f19697e;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private String f19698a;

        /* renamed from: b, reason: collision with root package name */
        private String f19699b;

        a(String str, String str2) {
            this.f19698a = str;
            this.f19699b = str2;
        }

        public String a() {
            return this.f19698a;
        }

        public String b() {
            return this.f19699b;
        }

        public String toString() {
            return "ShieldConfig{mModel=" + this.f19698a + "mOs=" + this.f19699b + '}';
        }
    }

    public List<a> a() {
        return this.f19697e;
    }

    public List<String> b() {
        return this.f19696d;
    }

    public List<String> c() {
        return this.f19695c;
    }

    public boolean d() {
        int i2;
        long j2 = this.f19693a;
        return (j2 == 0 || (i2 = this.f19694b) == 0 || j2 + ((long) (i2 * 3600000)) <= System.currentTimeMillis()) ? false : true;
    }

    public String toString() {
        return "PushConfigInfo{mRequestTime=" + this.f19693a + "mIntervalHour=" + this.f19694b + "mShieldPackageList=" + this.f19696d + "mWhitePackageList=" + this.f19695c + "mShieldConfigList=" + this.f19697e + '}';
    }

    public void a(int i2) {
        this.f19694b = i2;
    }

    public void b(String str) {
        if (this.f19695c == null) {
            this.f19695c = new ArrayList();
        }
        this.f19695c.add(str);
    }

    public void a(long j2) {
        this.f19693a = j2;
    }

    public void a(a aVar) {
        if (this.f19697e == null) {
            this.f19697e = new ArrayList();
        }
        this.f19697e.add(aVar);
    }

    public void a(String str) {
        if (this.f19696d == null) {
            this.f19696d = new ArrayList();
        }
        this.f19696d.add(str);
    }
}
