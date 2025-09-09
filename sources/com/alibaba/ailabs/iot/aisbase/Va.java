package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.tg.network.Callback;
import datasource.NetworkCallback;
import datasource.implemention.DefaultAuthManager;
import datasource.implemention.data.OtaProgressRespData;

/* loaded from: classes2.dex */
public class Va implements Callback<OtaProgressRespData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ NetworkCallback f8343a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DefaultAuthManager f8344b;

    public Va(DefaultAuthManager defaultAuthManager, NetworkCallback networkCallback) {
        this.f8344b = defaultAuthManager;
        this.f8343a = networkCallback;
    }

    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(int i2, OtaProgressRespData otaProgressRespData) {
        NetworkCallback networkCallback = this.f8343a;
        if (networkCallback != null) {
            networkCallback.onSuccess(otaProgressRespData);
        }
    }

    public void onFailure(int i2, String str, String str2) {
        NetworkCallback networkCallback = this.f8343a;
        if (networkCallback != null) {
            networkCallback.onFailure(str, str2);
        }
    }
}
