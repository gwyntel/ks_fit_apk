package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;

/* loaded from: classes2.dex */
public class E implements AppMeshStrategy.a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceInfo f10527a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10528b;

    public E(AppMeshStrategy appMeshStrategy, DeviceInfo deviceInfo) {
        this.f10528b = appMeshStrategy;
        this.f10527a = deviceInfo;
    }

    @Override // com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy.a
    public void onFail(int i2, String str) {
        ALog.w(AppMeshStrategy.TAG, "pid returnTo Pk is fail");
        this.f10528b.provisionResCallback(this.f10527a);
    }

    @Override // com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy.a
    public void onSuccess(Object obj) {
        if (obj != null) {
            try {
                String strValueOf = String.valueOf(JSON.parseObject(obj.toString()).get("productKey"));
                String str = AppMeshStrategy.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("pidReturnToPk productkey:");
                sb.append(strValueOf);
                ALog.d(str, sb.toString());
                if (!TextUtils.isEmpty(strValueOf)) {
                    this.f10527a.productKey = strValueOf;
                }
                if (this.f10528b.mDeviceInfo != null) {
                    this.f10527a.deviceName = this.f10528b.mDeviceInfo.deviceName;
                    this.f10528b.mDeviceInfo = null;
                }
                this.f10528b.provisionResCallback(this.f10527a);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }
}
