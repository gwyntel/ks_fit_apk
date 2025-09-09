package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.c, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0440c implements TimerUtils.ITimerCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10569a;

    public C0440c(BreezeConfigStrategy breezeConfigStrategy) {
        this.f10569a = breezeConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
    public void onTimeout() {
        ThreadPool.execute(new RunnableC0439b(this));
    }
}
