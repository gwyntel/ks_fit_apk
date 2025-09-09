package com.umeng.message.api;

/* loaded from: classes4.dex */
public interface UPushMessageNotifyApi {

    public interface Callback {
        void onNotified();

        void onNotifying();
    }

    boolean isEnabled();

    void setCallback(Callback callback);

    void setEnable(boolean z2);
}
