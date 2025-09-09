package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.biz.ProvisionRepository;
import com.aliyun.alink.business.devicecenter.config.IConfigCallback;
import com.aliyun.alink.business.devicecenter.provision.core.broadcast.AlinkBroadcastConfigStrategy;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.p, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0453p implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IConfigCallback f10586a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AlinkBroadcastConfigStrategy f10587b;

    public RunnableC0453p(AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy, IConfigCallback iConfigCallback) {
        this.f10587b = alinkBroadcastConfigStrategy;
        this.f10586a = iConfigCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy = this.f10587b;
        alinkBroadcastConfigStrategy.cancelRequest(alinkBroadcastConfigStrategy.retryTransitoryClient);
        AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy2 = this.f10587b;
        alinkBroadcastConfigStrategy2.retryTransitoryClient = ProvisionRepository.getCipher(alinkBroadcastConfigStrategy2.mConfigParams.productKey, this.f10587b.mConfigParams.deviceName, "00000000000000000000000000000000", null, new C0452o(this));
    }
}
