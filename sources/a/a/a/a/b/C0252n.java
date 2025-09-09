package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import datasource.MeshConfigCallback;

/* renamed from: a.a.a.a.b.n, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0252n implements MeshConfigCallback<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1608a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1609b;

    public C0252n(DeviceProvisioningWorker deviceProvisioningWorker, int i2) {
        this.f1609b = deviceProvisioningWorker;
        this.f1608a = i2;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(String str) {
        a.a.a.a.b.m.a.a(this.f1609b.f8693b, "reportDevicesStatus request success");
        a.a.a.a.b.l.c.a(this.f1608a, 1, true);
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(this.f1609b.f8693b, "reportDevicesStatus request failed, errorMessage: " + str2);
        a.a.a.a.b.l.c.a(this.f1608a, 0, true);
    }
}
