package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentGateMeshStrategy;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;

/* loaded from: classes2.dex */
public class L implements TimerUtils.ITimerCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ConcurrentGateMeshStrategy f10538a;

    public L(ConcurrentGateMeshStrategy concurrentGateMeshStrategy) {
        this.f10538a = concurrentGateMeshStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
    public void onTimeout() {
        ALog.d(ConcurrentGateMeshStrategy.TAG, "startProvisionTimer() onTimeout");
        this.f10538a.getProvisionTimeoutErrorInfo();
    }
}
