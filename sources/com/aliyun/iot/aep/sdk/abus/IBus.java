package com.aliyun.iot.aep.sdk.abus;

/* loaded from: classes3.dex */
public interface IBus {
    void attachListener(int i2, Object obj, String str, Class<? extends Object> cls);

    void blockChannel(int i2, boolean z2);

    void cancelChannel(int i2);

    void detachListener(int i2, Object obj);

    void detachListener(int i2, Object obj, Class<? extends Object> cls);

    void postEvent(int i2, Object obj);
}
