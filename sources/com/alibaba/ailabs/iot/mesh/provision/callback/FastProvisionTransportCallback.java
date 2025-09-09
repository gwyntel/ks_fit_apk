package com.alibaba.ailabs.iot.mesh.provision.callback;

import meshprovisioner.BaseMeshNode;

/* loaded from: classes2.dex */
public interface FastProvisionTransportCallback {
    void onFastProvisionDataSend(BaseMeshNode baseMeshNode, byte[] bArr);

    void onReceiveFastProvisionData(BaseMeshNode baseMeshNode, byte[] bArr);
}
