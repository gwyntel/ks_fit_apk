package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;
import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.u, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0458u extends ApiCallBack<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy.a f10593a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10594b;

    public C0458u(AppMeshStrategy appMeshStrategy, AppMeshStrategy.a aVar) {
        this.f10594b = appMeshStrategy;
        this.f10593a = aVar;
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onFail(int i2, String str) {
        AppMeshStrategy.a aVar = this.f10593a;
        if (aVar != null) {
            aVar.onFail(-1, "recover pk is failed");
        }
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onSuccess(Object obj) {
        AppMeshStrategy.a aVar = this.f10593a;
        if (aVar == null) {
            return;
        }
        aVar.onSuccess(obj);
    }
}
