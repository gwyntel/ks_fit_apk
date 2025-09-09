package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;

/* renamed from: a.a.a.a.b.t, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0257t implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1636a;

    public RunnableC0257t(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1636a = deviceProvisioningWorker;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f1636a.R.get()) {
            a.a.a.a.b.m.a.d(this.f1636a.f8693b, "The connect semaphore is not released as expected");
            this.f1636a.o();
            this.f1636a.p();
            MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.GATT_CONNECT_TIMEOUT;
            this.f1636a.a(meshUtConst$MeshErrorEnum, meshUtConst$MeshErrorEnum.getErrorMsg());
        }
    }
}
