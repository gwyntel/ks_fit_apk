package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.ut.UtError;

/* renamed from: a.a.a.a.b.z, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0263z implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1651a;

    public RunnableC0263z(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1651a = deviceProvisioningWorker;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (MeshDeviceInfoManager.getInstance().isLowCostDeviceExist()) {
            a.a.a.a.b.m.a.a(this.f1651a.f8693b, "scan timeout");
            return;
        }
        if (this.f1651a.J) {
            this.f1651a.a(false, UtError.MESH_SCAN_TIMEOUT.getMsg());
        }
        this.f1651a.s();
    }
}
