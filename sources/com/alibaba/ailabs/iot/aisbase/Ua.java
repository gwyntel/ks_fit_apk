package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.tg.network.Callback;
import datasource.NetworkCallback;
import datasource.implemention.DefaultAuthManager;
import datasource.implemention.data.AuthCheckAndGetBleKeyRespData;

/* loaded from: classes2.dex */
public class Ua implements Callback<AuthCheckAndGetBleKeyRespData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ NetworkCallback f8338a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DefaultAuthManager f8339b;

    public Ua(DefaultAuthManager defaultAuthManager, NetworkCallback networkCallback) {
        this.f8339b = defaultAuthManager;
        this.f8338a = networkCallback;
    }

    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(int i2, AuthCheckAndGetBleKeyRespData authCheckAndGetBleKeyRespData) {
        NetworkCallback networkCallback = this.f8338a;
        if (networkCallback != null) {
            networkCallback.onSuccess(authCheckAndGetBleKeyRespData.getModel());
        }
    }

    public void onFailure(int i2, String str, String str2) {
        NetworkCallback networkCallback = this.f8338a;
        if (networkCallback != null) {
            networkCallback.onFailure(str, str2);
        }
    }
}
