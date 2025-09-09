package com.aliyun.alink.business.devicecenter.provision.core;

import com.alibaba.ailabs.iot.mesh.AuthInfoListener;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;

/* loaded from: classes2.dex */
public class B implements AuthInfoListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10524a;

    public B(AppMeshStrategy appMeshStrategy) {
        this.f10524a = appMeshStrategy;
    }

    @Override // com.alibaba.ailabs.iot.mesh.AuthInfoListener
    public String getAuthInfo() {
        return "";
    }
}
