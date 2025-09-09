package com.alibaba.ailabs.iot.mesh.provision.callback;

import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;

/* loaded from: classes2.dex */
public interface FastProvisionV2StatusCallback {
    void onProvisioningComplete(ProvisionedMeshNode provisionedMeshNode);

    void onProvisioningDataSent(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningFailed(BaseMeshNode baseMeshNode, int i2, String str);
}
