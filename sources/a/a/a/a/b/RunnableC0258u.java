package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;

/* renamed from: a.a.a.a.b.u, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0258u implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1639a;

    public RunnableC0258u(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1639a = deviceProvisioningWorker;
    }

    @Override // java.lang.Runnable
    public void run() {
        a.a.a.a.b.m.a.c(this.f1639a.f8693b, "Execute state timeout task");
        this.f1639a.f8695d.read();
    }
}
