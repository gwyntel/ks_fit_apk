package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;

/* renamed from: a.a.a.a.b.i.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0241j implements IActionListener<byte[]> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1486a;

    public C0241j(FastProvisionManager fastProvisionManager) {
        this.f1486a = fastProvisionManager;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(byte[] bArr) {
        a.a.a.a.b.m.a.c(FastProvisionManager.TAG, "Advertising network data, begin scan");
        FastProvisionManager fastProvisionManager = this.f1486a;
        fastProvisionManager.startScanDeviceAdvertise(fastProvisionManager.appContext);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1486a.onProvisionFailed(-1, "failed to advertise network data");
    }
}
