package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.tg.utils.LogUtils;
import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;
import datasource.NetworkCallback;
import datasource.implemention.FeiyanAuthManager;

/* loaded from: classes2.dex */
public class Wa extends ApiCallBack {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ NetworkCallback f8346a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ FeiyanAuthManager f8347b;

    public Wa(FeiyanAuthManager feiyanAuthManager, NetworkCallback networkCallback) {
        this.f8347b = feiyanAuthManager;
        this.f8346a = networkCallback;
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onFail(int i2, String str) {
        LogUtils.i(FeiyanAuthManager.f24966a, "getAuthRandomIdForBLEDevice: onFail code " + i2 + ", msg " + str);
        NetworkCallback networkCallback = this.f8346a;
        if (networkCallback != null) {
            networkCallback.onFailure(i2 + "", str);
        }
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onSuccess(Object obj) {
        LogUtils.i(FeiyanAuthManager.f24966a, "getAuthRandomIdForBLEDevice: onSuccess--- ");
        NetworkCallback networkCallback = this.f8346a;
        if (networkCallback != null) {
            if (obj instanceof String) {
                networkCallback.onSuccess((String) obj);
            } else {
                onFail(-1, "resp is not string");
            }
        }
    }
}
