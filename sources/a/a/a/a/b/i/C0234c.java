package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;

/* renamed from: a.a.a.a.b.i.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0234c implements BleAdvertiseCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BaseMeshNode f1429a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f1430b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1431c;

    public C0234c(FastProvisionManager fastProvisionManager, BaseMeshNode baseMeshNode, byte[] bArr) {
        this.f1431c = fastProvisionManager;
        this.f1429a = baseMeshNode;
        this.f1430b = bArr;
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.c(FastProvisionManager.TAG, "send control msg success");
        this.f1431c.transportCallback.onFastProvisionDataSend(this.f1429a, this.f1430b);
        BaseMeshNode baseMeshNode = this.f1429a;
        if (baseMeshNode instanceof UnprovisionedMeshNode) {
            this.f1431c.unprovisionedMeshNode = (UnprovisionedMeshNode) baseMeshNode;
        }
        FastProvisionManager fastProvisionManager = this.f1431c;
        fastProvisionManager.startScanDeviceAdvertise(fastProvisionManager.appContext);
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.c(FastProvisionManager.TAG, "send control msg failed, errorCode: " + i2 + ", desc: " + str);
    }
}
