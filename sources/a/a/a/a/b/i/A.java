package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import meshprovisioner.states.UnprovisionedMeshNodeData;

/* loaded from: classes.dex */
public class A implements IActionListener<byte[]> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedMeshNodeData f1359a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ J f1360b;

    public A(J j2, UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.f1360b = j2;
        this.f1359a = unprovisionedMeshNodeData;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(byte[] bArr) {
        a.a.a.a.b.m.a.c(this.f1360b.f1373a, "broadcast random success");
        this.f1360b.onBroadcastingRandoms(this.f1359a);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1360b.onProvisionFailed(i2, str);
    }
}
