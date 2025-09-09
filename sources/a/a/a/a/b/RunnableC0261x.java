package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;

/* renamed from: a.a.a.a.b.x, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0261x implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1647a;

    public RunnableC0261x(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1647a = deviceProvisioningWorker;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1647a.a(false, -31, 0, "no proactive reporting was received from the device");
    }
}
