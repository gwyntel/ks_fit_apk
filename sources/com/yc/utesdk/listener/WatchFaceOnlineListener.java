package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface WatchFaceOnlineListener {
    void onDeleteWatchFaceOnline(int i2);

    void onWatchFaceOnlineMaxCount(int i2);

    void onWatchFaceOnlineProgress(int i2);

    void onWatchFaceOnlineStatus(int i2);

    <T> void onWatchFaceParams(int i2, T t2);
}
