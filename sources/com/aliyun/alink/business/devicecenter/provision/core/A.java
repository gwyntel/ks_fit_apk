package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;

/* loaded from: classes2.dex */
public class A implements TimerUtils.ITimerCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10523a;

    public A(AppMeshStrategy appMeshStrategy) {
        this.f10523a = appMeshStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
    public void onTimeout() {
        this.f10523a.getProvisionTimeoutErrorInfo();
    }
}
