package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentAppMeshStrategy;
import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;

/* loaded from: classes2.dex */
public class J extends ApiCallBack<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ConcurrentAppMeshStrategy.a f10534a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ConcurrentAppMeshStrategy f10535b;

    public J(ConcurrentAppMeshStrategy concurrentAppMeshStrategy, ConcurrentAppMeshStrategy.a aVar) {
        this.f10535b = concurrentAppMeshStrategy;
        this.f10534a = aVar;
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onFail(int i2, String str) {
        ConcurrentAppMeshStrategy.a aVar = this.f10534a;
        if (aVar != null) {
            aVar.onFail(-1, "recover pk is failed");
        }
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onSuccess(Object obj) {
        ConcurrentAppMeshStrategy.a aVar = this.f10534a;
        if (aVar == null) {
            return;
        }
        aVar.onSuccess(obj);
    }
}
