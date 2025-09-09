package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class u implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ HonorPushCallback f15548a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f15549b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f15550c;

    public u(s sVar, HonorPushCallback honorPushCallback, int i2, String str) {
        this.f15548a = honorPushCallback;
        this.f15549b = i2;
        this.f15550c = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        HonorPushCallback honorPushCallback = this.f15548a;
        if (honorPushCallback != null) {
            honorPushCallback.onFailure(this.f15549b, this.f15550c);
        }
    }
}
