package com.huawei.hms.hmsscankit;

import android.os.RemoteException;
import com.huawei.hms.hmsscankit.api.IOnLightCallback;

/* loaded from: classes4.dex */
class d extends IOnLightCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    private final OnLightVisibleCallBack f16565a;

    d(OnLightVisibleCallBack onLightVisibleCallBack) {
        this.f16565a = onLightVisibleCallBack;
    }

    @Override // com.huawei.hms.hmsscankit.api.IOnLightCallback
    public void onVisibleChanged(boolean z2) throws RemoteException {
        this.f16565a.onVisibleChanged(z2);
    }
}
