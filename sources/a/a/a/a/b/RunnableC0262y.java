package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;

/* renamed from: a.a.a.a.b.y, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0262y implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1649a;

    public RunnableC0262y(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1649a = deviceProvisioningWorker;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f1649a.J) {
            return;
        }
        this.f1649a.q();
    }
}
