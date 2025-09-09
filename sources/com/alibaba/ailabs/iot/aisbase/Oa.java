package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.tg.network.Callback;
import com.alibaba.ailabs.tg.utils.LogUtils;
import datasource.NetworkCallback;
import datasource.implemention.DefaultAuthManager;
import datasource.implemention.data.AuthRandomIdRespData;

/* loaded from: classes2.dex */
public class Oa implements Callback<AuthRandomIdRespData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ NetworkCallback f8312a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DefaultAuthManager f8313b;

    public Oa(DefaultAuthManager defaultAuthManager, NetworkCallback networkCallback) {
        this.f8313b = defaultAuthManager;
        this.f8312a = networkCallback;
    }

    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(int i2, AuthRandomIdRespData authRandomIdRespData) {
        LogUtils.d("AuthPluginBusinessProxy", "getAuthRandomId success: " + authRandomIdRespData);
        NetworkCallback networkCallback = this.f8312a;
        if (networkCallback != null) {
            networkCallback.onSuccess(authRandomIdRespData.getModel());
        }
    }

    public void onFailure(int i2, String str, String str2) {
        NetworkCallback networkCallback = this.f8312a;
        if (networkCallback != null) {
            networkCallback.onFailure(str, str2);
        }
    }
}
