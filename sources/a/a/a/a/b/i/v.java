package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;

/* loaded from: classes.dex */
public class v implements BleAdvertiseCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BaseMeshNode f1519a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f1520b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ J f1521c;

    public v(J j2, BaseMeshNode baseMeshNode, byte[] bArr) {
        this.f1521c = j2;
        this.f1519a = baseMeshNode;
        this.f1520b = bArr;
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.c(this.f1521c.f1373a, "send control msg success");
        this.f1521c.f1393u.onFastProvisionDataSend(this.f1519a, this.f1520b);
        BaseMeshNode baseMeshNode = this.f1519a;
        if (baseMeshNode instanceof UnprovisionedMeshNode) {
            this.f1521c.f1382j = (UnprovisionedMeshNode) baseMeshNode;
        }
        J j2 = this.f1521c;
        j2.a(j2.f1374b);
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.c(this.f1521c.f1373a, "send control msg failed, errorCode: " + i2 + ", desc: " + str);
    }
}
