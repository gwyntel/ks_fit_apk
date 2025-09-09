package com.alibaba.ailabs.iot.mesh.callback;

import com.alibaba.ailabs.iot.mesh.bean.MeshAccessPayload;

/* loaded from: classes2.dex */
public interface MeshMsgListener {
    void onReceiveMeshMessage(byte[] bArr, MeshAccessPayload meshAccessPayload);
}
