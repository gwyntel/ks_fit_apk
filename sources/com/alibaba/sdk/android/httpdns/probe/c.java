package com.alibaba.sdk.android.httpdns.probe;

/* loaded from: classes2.dex */
class c {
    private String hostName;
    private String[] ips;

    /* renamed from: k, reason: collision with root package name */
    private long f8890k;

    /* renamed from: l, reason: collision with root package name */
    private long f8891l;

    /* renamed from: r, reason: collision with root package name */
    private String f8892r;

    /* renamed from: s, reason: collision with root package name */
    private String f8893s;

    c(String str, String[] strArr, String str2, String str3, long j2, long j3) {
        this.hostName = str;
        this.ips = strArr;
        this.f8892r = str2;
        this.f8893s = str3;
        this.f8890k = j2;
        this.f8891l = j3;
    }

    public long c() {
        return this.f8890k;
    }

    public long d() {
        return this.f8891l;
    }

    public String getHostName() {
        return this.hostName;
    }

    public String[] getIps() {
        return this.ips;
    }

    public String j() {
        return this.f8892r;
    }

    public String k() {
        return this.f8893s;
    }
}
