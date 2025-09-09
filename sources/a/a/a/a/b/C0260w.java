package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.provision.WiFiConfigReplyParser;

/* renamed from: a.a.a.a.b.w, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0260w implements WiFiConfigReplyParser.a<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1645a;

    public C0260w(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1645a = deviceProvisioningWorker;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.WiFiConfigReplyParser.a
    public void a(WiFiConfigReplyParser.Status status) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onSuccess(Object obj) {
        this.f1645a.a(true, 0, 0, "");
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.WiFiConfigReplyParser.a
    public void a(int i2, int i3, String str) {
        this.f1645a.a(false, i2, i3, str);
    }
}
