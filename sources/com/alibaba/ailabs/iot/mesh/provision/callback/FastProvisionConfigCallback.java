package com.alibaba.ailabs.iot.mesh.provision.callback;

import aisscanner.ScanRecord;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes2.dex */
public interface FastProvisionConfigCallback {
    void advertiseAppKey(ProvisionedMeshNode provisionedMeshNode, IActionListener<Boolean> iActionListener);

    void requestConfigMsg(ProvisionedMeshNode provisionedMeshNode, IActionListener<Boolean> iActionListener);

    void requestProvisionMsg(ScanRecord scanRecord);
}
