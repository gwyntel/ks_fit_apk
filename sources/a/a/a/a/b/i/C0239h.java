package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import meshprovisioner.states.UnprovisionedMeshNodeData;

/* renamed from: a.a.a.a.b.i.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0239h implements IActionListener<byte[]> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedMeshNodeData f1483a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1484b;

    public C0239h(FastProvisionManager fastProvisionManager, UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.f1484b = fastProvisionManager;
        this.f1483a = unprovisionedMeshNodeData;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(byte[] bArr) {
        a.a.a.a.b.m.a.c(FastProvisionManager.TAG, "broadcast random success");
        this.f1484b.onBroadcastingRandoms(this.f1483a);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1484b.onProvisionFailed(i2, str);
    }
}
