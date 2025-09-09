package com.hihonor.push.sdk;

import com.hihonor.push.sdk.internal.HonorPushErrorEnum;
import com.hihonor.push.sdk.z;

/* loaded from: classes3.dex */
public class y implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f15566a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ z.a f15567b;

    public y(z.a aVar, int i2) {
        this.f15567b = aVar;
        this.f15566a = i2;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f15567b.a(HonorPushErrorEnum.fromCode(this.f15566a));
    }
}
