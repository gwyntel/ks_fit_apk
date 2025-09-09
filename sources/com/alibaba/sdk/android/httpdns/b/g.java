package com.alibaba.sdk.android.httpdns.b;

/* loaded from: classes2.dex */
public class g {

    /* renamed from: i, reason: collision with root package name */
    public long f8805i;
    public long id;

    /* renamed from: o, reason: collision with root package name */
    public String f8806o;

    /* renamed from: p, reason: collision with root package name */
    public String f8807p;

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("[IpRecord] ");
        sb.append("id:");
        sb.append(this.id);
        sb.append("|");
        sb.append("host_id:");
        sb.append(this.f8805i);
        sb.append("|");
        sb.append("ip:");
        sb.append(this.f8806o);
        sb.append("|");
        sb.append("ttl:");
        sb.append(this.f8807p);
        sb.append("|");
        return sb.toString();
    }
}
