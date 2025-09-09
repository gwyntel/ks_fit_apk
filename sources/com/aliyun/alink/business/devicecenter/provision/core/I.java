package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentAppMeshStrategy;

/* loaded from: classes2.dex */
public class I implements ConcurrentAppMeshStrategy.a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceInfo f10532a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ConcurrentAppMeshStrategy f10533b;

    public I(ConcurrentAppMeshStrategy concurrentAppMeshStrategy, DeviceInfo deviceInfo) {
        this.f10533b = concurrentAppMeshStrategy;
        this.f10532a = deviceInfo;
    }

    @Override // com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentAppMeshStrategy.a
    public void onFail(int i2, String str) {
        ALog.w(ConcurrentAppMeshStrategy.TAG, "pid returnTo Pk is fail");
        this.f10533b.provisionResCallback(this.f10532a);
    }

    @Override // com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentAppMeshStrategy.a
    public void onSuccess(Object obj) {
        if (obj != null) {
            try {
                String strValueOf = String.valueOf(JSON.parseObject(obj.toString()).get("productKey"));
                String str = ConcurrentAppMeshStrategy.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("pidReturnToPk productkey:");
                sb.append(strValueOf);
                ALog.d(str, sb.toString());
                if (!TextUtils.isEmpty(strValueOf)) {
                    this.f10532a.productKey = strValueOf;
                }
                this.f10533b.provisionResCallback(this.f10532a);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }
}
