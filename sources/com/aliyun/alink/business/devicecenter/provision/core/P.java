package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.provision.core.mesh.GatewayMeshStrategy;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;

/* loaded from: classes2.dex */
public class P implements TimerUtils.ITimerCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ GatewayMeshStrategy f10542a;

    public P(GatewayMeshStrategy gatewayMeshStrategy) {
        this.f10542a = gatewayMeshStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
    public void onTimeout() {
        this.f10542a.getProvisionTimeoutErrorInfo();
    }
}
