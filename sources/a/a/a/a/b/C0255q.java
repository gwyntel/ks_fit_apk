package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.fastjson.JSON;
import datasource.MeshConfigCallback;
import datasource.bean.IotDevice;

/* renamed from: a.a.a.a.b.q, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0255q implements MeshConfigCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IotDevice f1622a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1623b;

    public C0255q(DeviceProvisioningWorker deviceProvisioningWorker, IotDevice iotDevice) {
        this.f1623b = deviceProvisioningWorker;
        this.f1622a = iotDevice;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a(this.f1623b.f8693b, "getInfoByAuthInfo request success");
        this.f1623b.O = true;
        this.f1623b.a(true, (String) null);
        this.f1623b.f8710s.removeCallbacks(this.f1623b.ca);
        this.f1623b.a(1, JSON.toJSONString(this.f1622a));
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.a(this.f1623b.f8693b, "getInfoByAuthInfo request failed, errorMessage: " + str2);
        this.f1623b.a(-1, str2);
    }
}
