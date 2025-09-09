package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.tg.network.Callback;
import datasource.NetworkCallback;
import datasource.implemention.DefaultAuthManager;
import datasource.implemention.data.GetDeviceUUIDRespData;

/* loaded from: classes2.dex */
public class Qa implements Callback<GetDeviceUUIDRespData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ NetworkCallback f8320a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DefaultAuthManager f8321b;

    public Qa(DefaultAuthManager defaultAuthManager, NetworkCallback networkCallback) {
        this.f8321b = defaultAuthManager;
        this.f8320a = networkCallback;
    }

    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(int i2, GetDeviceUUIDRespData getDeviceUUIDRespData) {
        NetworkCallback networkCallback = this.f8320a;
        if (networkCallback != null) {
            networkCallback.onSuccess(getDeviceUUIDRespData);
        }
    }

    public void onFailure(int i2, String str, String str2) {
        NetworkCallback networkCallback = this.f8320a;
        if (networkCallback != null) {
            networkCallback.onFailure(str, str2);
        }
    }
}
