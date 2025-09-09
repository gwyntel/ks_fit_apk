package com.alibaba.ailabs.iot.mesh.callback;

import com.alibaba.ailabs.iot.mesh.TgMeshManager;

/* loaded from: classes2.dex */
public interface DeviceOnlineStatusListener {
    void onOnlineStatusChange(String str, TgMeshManager.DevOnlineStatus devOnlineStatus);
}
