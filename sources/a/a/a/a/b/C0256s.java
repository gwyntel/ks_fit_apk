package a.a.a.a.b;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;

/* renamed from: a.a.a.a.b.s, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0256s implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1632a;

    public C0256s(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1632a = deviceProvisioningWorker;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.c(this.f1632a.f8693b, "tiny mesh gatt disconnected");
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
    }
}
