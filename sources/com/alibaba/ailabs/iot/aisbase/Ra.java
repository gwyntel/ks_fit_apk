package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.tg.network.Callback;
import datasource.NetworkCallback;
import datasource.implemention.DefaultAuthManager;
import datasource.implemention.data.DeviceVersionInfo;

/* loaded from: classes2.dex */
public class Ra implements Callback<DeviceVersionInfo> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ NetworkCallback f8322a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DefaultAuthManager f8323b;

    public Ra(DefaultAuthManager defaultAuthManager, NetworkCallback networkCallback) {
        this.f8323b = defaultAuthManager;
        this.f8322a = networkCallback;
    }

    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(int i2, DeviceVersionInfo deviceVersionInfo) {
        NetworkCallback networkCallback = this.f8322a;
        if (networkCallback != null) {
            networkCallback.onSuccess(deviceVersionInfo);
        }
    }

    public void onFailure(int i2, String str, String str2) {
        NetworkCallback networkCallback = this.f8322a;
        if (networkCallback != null) {
            networkCallback.onFailure(str, str2);
        }
    }
}
