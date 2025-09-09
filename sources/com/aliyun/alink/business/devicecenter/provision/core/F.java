package com.aliyun.alink.business.devicecenter.provision.core;

import com.alibaba.ailabs.iot.mesh.AuthInfoListener;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentAppMeshStrategy;

/* loaded from: classes2.dex */
public class F implements AuthInfoListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ConcurrentAppMeshStrategy f10529a;

    public F(ConcurrentAppMeshStrategy concurrentAppMeshStrategy) {
        this.f10529a = concurrentAppMeshStrategy;
    }

    @Override // com.alibaba.ailabs.iot.mesh.AuthInfoListener
    public String getAuthInfo() {
        return "";
    }
}
