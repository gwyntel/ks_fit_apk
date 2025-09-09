package a.a.a.a.b;

import android.os.ParcelUuid;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import meshprovisioner.states.UnprovisionedMeshNodeData;

/* loaded from: classes.dex */
public class E implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1196a;

    public E(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1196a = deviceProvisioningWorker;
    }

    @Override // java.lang.Runnable
    public void run() {
        byte[] serviceData = this.f1196a.f8709r.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID));
        if (this.f1196a.f8717z == null) {
            this.f1196a.f8717z = new UnprovisionedMeshNodeData(serviceData);
        }
        this.f1196a.f8696e.a(this.f1196a.f8708q.getAddress(), this.f1196a.f8708q.getName(), serviceData, this.f1196a.f8717z, this.f1196a.P);
        this.f1196a.b("identifyNode");
    }
}
