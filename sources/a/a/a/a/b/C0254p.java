package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import datasource.MeshConfigCallback;
import datasource.bean.IotDevice;

/* renamed from: a.a.a.a.b.p, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0254p implements MeshConfigCallback<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IotDevice f1615a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ String f1616b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1617c;

    public C0254p(DeviceProvisioningWorker deviceProvisioningWorker, IotDevice iotDevice, String str) {
        this.f1617c = deviceProvisioningWorker;
        this.f1615a = iotDevice;
        this.f1616b = str;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(String str) {
        this.f1617c.a(this.f1615a, this.f1616b);
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.a(this.f1617c.f8693b, "getInfoByAuthInfo request failed, errorMessage: " + str2);
        this.f1617c.a(-1, str2);
    }
}
