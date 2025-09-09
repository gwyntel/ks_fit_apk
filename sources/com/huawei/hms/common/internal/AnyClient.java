package com.huawei.hms.common.internal;

import com.huawei.hms.core.aidl.IMessageEntity;

/* loaded from: classes4.dex */
public interface AnyClient {

    public interface CallBack {
        void onCallback(IMessageEntity iMessageEntity, String str);
    }

    void connect(int i2);

    void connect(int i2, boolean z2);

    void disconnect();

    int getRequestHmsVersionCode();

    String getSessionId();

    boolean isConnected();

    boolean isConnecting();

    void post(IMessageEntity iMessageEntity, String str, CallBack callBack);
}
