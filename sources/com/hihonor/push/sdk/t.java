package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class t implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ HonorPushCallback f15543a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f15544b;

    public t(s sVar, HonorPushCallback honorPushCallback, Object obj) {
        this.f15543a = honorPushCallback;
        this.f15544b = obj;
    }

    @Override // java.lang.Runnable
    public void run() {
        HonorPushCallback honorPushCallback = this.f15543a;
        if (honorPushCallback != null) {
            honorPushCallback.onSuccess(this.f15544b);
        }
    }
}
