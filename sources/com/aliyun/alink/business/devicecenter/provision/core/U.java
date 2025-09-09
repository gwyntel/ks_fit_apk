package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.biz.ProvisionRepository;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.GatewayMeshStrategy;

/* loaded from: classes2.dex */
public class U implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f10546a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ GatewayMeshStrategy f10547b;

    public U(GatewayMeshStrategy gatewayMeshStrategy, String str) {
        this.f10547b = gatewayMeshStrategy;
        this.f10546a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f10547b.provisionHasStopped.get()) {
            return;
        }
        ProvisionRepository.getMeshProvisionResult(this.f10546a, new T(this));
    }
}
