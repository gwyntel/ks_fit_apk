package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* renamed from: a.a.a.a.b.k, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0249k implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1541a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1542b;

    public RunnableC0249k(DeviceProvisioningWorker deviceProvisioningWorker, ProvisionedMeshNode provisionedMeshNode) {
        this.f1542b = deviceProvisioningWorker;
        this.f1541a = provisionedMeshNode;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str = (String) this.f1542b.f8706o.poll();
        Integer num = (Integer) this.f1542b.f8705n.poll();
        if (str != null) {
            if (num == null) {
                num = 0;
            }
            a.a.a.a.b.m.a.c(this.f1542b.f8693b, "try to add app key: appKeyIndex = " + num + ", mAppKey = " + str);
            this.f1542b.f8696e.a(this.f1541a, num.intValue(), str);
        }
        a.a.a.a.b.m.a.a(this.f1542b.f8693b, "addAppKey");
    }
}
