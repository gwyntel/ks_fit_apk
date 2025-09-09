package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;

/* loaded from: classes.dex */
public class q implements IActionListener<byte[]> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ u f1495a;

    public q(u uVar) {
        this.f1495a = uVar;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(byte[] bArr) {
        a.a.a.a.b.m.a.c(this.f1495a.f1500a, "Advertising network data, begin scan");
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        u uVar = this.f1495a;
        uVar.onProvisioningFailed(uVar.f1506g, -60, "failed to write provisioning data, transport layer error code: " + i2 + ", errorMsg: " + str);
    }
}
