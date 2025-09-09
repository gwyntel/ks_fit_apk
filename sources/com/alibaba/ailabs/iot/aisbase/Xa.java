package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.tg.utils.LogUtils;
import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;
import datasource.NetworkCallback;
import datasource.implemention.FeiyanAuthManager;

/* loaded from: classes2.dex */
public class Xa extends ApiCallBack {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ NetworkCallback f8350a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ FeiyanAuthManager f8351b;

    public Xa(FeiyanAuthManager feiyanAuthManager, NetworkCallback networkCallback) {
        this.f8351b = feiyanAuthManager;
        this.f8350a = networkCallback;
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onFail(int i2, String str) {
        LogUtils.i(FeiyanAuthManager.f24966a, "authCipherCheckThenGetKeyForBLEDevice onFail code " + i2 + ", msg " + str);
        NetworkCallback networkCallback = this.f8350a;
        if (networkCallback != null) {
            networkCallback.onFailure(i2 + "", str);
        }
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onSuccess(Object obj) {
        LogUtils.i(FeiyanAuthManager.f24966a, "authCipherCheckThenGetKeyForBLEDevice onSuccess");
        if (!(obj instanceof String)) {
            onFail(-1, "resp is not string");
            return;
        }
        NetworkCallback networkCallback = this.f8350a;
        if (networkCallback != null) {
            networkCallback.onSuccess((String) obj);
        } else {
            LogUtils.i(FeiyanAuthManager.f24966a, "authCipherCheckThenGetKeyForBLEDevice callback is null");
        }
    }
}
