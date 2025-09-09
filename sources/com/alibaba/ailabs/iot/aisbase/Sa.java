package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.tg.network.Callback;
import datasource.NetworkCallback;
import datasource.implemention.DefaultAuthManager;
import datasource.implemention.data.UpdateDeviceVersionRespData;

/* loaded from: classes2.dex */
public class Sa implements Callback<UpdateDeviceVersionRespData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ NetworkCallback f8329a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DefaultAuthManager f8330b;

    public Sa(DefaultAuthManager defaultAuthManager, NetworkCallback networkCallback) {
        this.f8330b = defaultAuthManager;
        this.f8329a = networkCallback;
    }

    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(int i2, UpdateDeviceVersionRespData updateDeviceVersionRespData) {
        NetworkCallback networkCallback = this.f8329a;
        if (networkCallback != null) {
            networkCallback.onSuccess(updateDeviceVersionRespData);
        }
    }

    public void onFailure(int i2, String str, String str2) {
        NetworkCallback networkCallback = this.f8329a;
        if (networkCallback != null) {
            networkCallback.onFailure(str, str2);
        }
    }
}
