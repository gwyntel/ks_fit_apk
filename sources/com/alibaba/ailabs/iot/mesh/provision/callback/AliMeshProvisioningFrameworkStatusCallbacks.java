package com.alibaba.ailabs.iot.mesh.provision.callback;

import datasource.bean.DeviceStatus;
import java.util.List;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes2.dex */
public interface AliMeshProvisioningFrameworkStatusCallbacks {
    void onConfigurationComplete(ProvisionedMeshNode provisionedMeshNode);

    void onProvisioningComplete(ProvisionedMeshNode provisionedMeshNode, List<DeviceStatus> list);

    void onProvisioningFailed(BaseMeshNode baseMeshNode, int i2);
}
