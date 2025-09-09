package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class HonorPushDataMsg {

    /* renamed from: a, reason: collision with root package name */
    public int f15456a = 1;

    /* renamed from: b, reason: collision with root package name */
    public int f15457b = 0;

    /* renamed from: c, reason: collision with root package name */
    public long f15458c;

    /* renamed from: d, reason: collision with root package name */
    public String f15459d;

    public String getData() {
        return this.f15459d;
    }

    public long getMsgId() {
        return this.f15458c;
    }

    public int getType() {
        return this.f15457b;
    }

    public int getVersion() {
        return this.f15456a;
    }

    public void setData(String str) {
        this.f15459d = str;
    }

    public void setMsgId(long j2) {
        this.f15458c = j2;
    }

    public void setType(int i2) {
        this.f15457b = i2;
    }

    public void setVersion(int i2) {
        this.f15456a = i2;
    }
}
