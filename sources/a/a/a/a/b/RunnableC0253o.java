package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;

/* renamed from: a.a.a.a.b.o, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0253o implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1614a;

    public RunnableC0253o(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1614a = deviceProvisioningWorker;
    }

    @Override // java.lang.Runnable
    public void run() {
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.TIMEOUT_ERROR;
        this.f1614a.a(meshUtConst$MeshErrorEnum, meshUtConst$MeshErrorEnum.getErrorMsg());
    }
}
