package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.f, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0443f implements DeviceInfoUtils.IApiCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0444g f10572a;

    public C0443f(C0444g c0444g) {
        this.f10572a = c0444g;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils.IApiCallback
    public void onFail(int i2, String str) {
        ALog.w(BreezeConfigStrategy.TAG, "pid returnTo Pk is fail");
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils.IApiCallback
    public void onSuccess(Object obj) {
        if (obj != null) {
            try {
                String strValueOf = String.valueOf(JSON.parseObject(obj.toString()).get("productKey"));
                String str = BreezeConfigStrategy.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("pidReturnToPk productkey:");
                sb.append(strValueOf);
                ALog.d(str, sb.toString());
                if (TextUtils.isEmpty(strValueOf)) {
                    return;
                }
                this.f10572a.f10573a.mConfigParams.productKey = strValueOf;
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }
}
