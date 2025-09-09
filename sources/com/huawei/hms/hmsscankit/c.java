package com.huawei.hms.hmsscankit;

import android.os.RemoteException;
import android.util.Log;
import com.huawei.hms.hmsscankit.api.IOnErrorCallback;

/* loaded from: classes4.dex */
class c extends IOnErrorCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    private final OnErrorCallback f16564a;

    c(OnErrorCallback onErrorCallback) {
        this.f16564a = onErrorCallback;
    }

    @Override // com.huawei.hms.hmsscankit.api.IOnErrorCallback
    public void onError(int i2) throws RemoteException {
        if (this.f16564a != null) {
            Log.i("OnErrorCallbackDelegate", "onError: ErrorCode：" + i2);
            this.f16564a.onError(i2);
        }
    }
}
