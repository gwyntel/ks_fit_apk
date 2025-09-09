package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.provision.callback.AliMeshProvisioningFrameworkStatusCallbacks;
import datasource.bean.DeviceStatus;
import java.util.List;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;

/* loaded from: classes.dex */
public class C implements AliMeshProvisioningFrameworkStatusCallbacks {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1194a;

    public C(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1194a = deviceProvisioningWorker;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.AliMeshProvisioningFrameworkStatusCallbacks
    public void onConfigurationComplete(ProvisionedMeshNode provisionedMeshNode) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.AliMeshProvisioningFrameworkStatusCallbacks
    public void onProvisioningComplete(ProvisionedMeshNode provisionedMeshNode, List<DeviceStatus> list) {
        a.a.a.a.b.i.c.a aVarB = this.f1194a.X.b();
        if (aVarB instanceof a.a.a.a.b.i.c.r) {
            BleMeshManager bleMeshManagerD = ((a.a.a.a.b.i.c.r) aVarB).d();
            if (this.f1194a.f8695d != null) {
                bleMeshManagerD.setProvisioningComplete(true);
                bleMeshManagerD.setGattCallbacks(this.f1194a);
                this.f1194a.f8695d = bleMeshManagerD;
            }
        }
        this.f1194a.f8700i = provisionedMeshNode;
        if (list != null && list.size() > 0) {
            this.f1194a.U.a(list);
        }
        this.f1194a.a(provisionedMeshNode.getUnicastAddress(), (list == null || list.size() <= 0) ? null : list.get(0));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.AliMeshProvisioningFrameworkStatusCallbacks
    public void onProvisioningFailed(BaseMeshNode baseMeshNode, int i2) {
        this.f1194a.onProvisioningFailed((UnprovisionedMeshNode) baseMeshNode, i2);
    }
}
