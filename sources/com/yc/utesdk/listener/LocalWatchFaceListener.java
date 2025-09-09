package com.yc.utesdk.listener;

import com.yc.utesdk.bean.LocalWatchFaceInfo;

/* loaded from: classes4.dex */
public interface LocalWatchFaceListener {
    public static final int SET_LOCAL_WATCH_FACE_FAIL = 2;
    public static final int SET_LOCAL_WATCH_FACE_SUCCESS = 1;

    void notifyCurrentWatchFaceIndex(int i2);

    void queryLocalWatchFaceFail();

    void queryLocalWatchFaceSuccess(LocalWatchFaceInfo localWatchFaceInfo);

    void setLocalWatchFaceStatus(int i2, int i3);
}
