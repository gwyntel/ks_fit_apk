package a.a.a.a.b.k;

import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;
import meshprovisioner.BaseMeshNode;

/* loaded from: classes.dex */
public class b implements BleAdvertiseCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BaseMeshNode f1544a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f1545b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ d f1546c;

    public b(d dVar, BaseMeshNode baseMeshNode, byte[] bArr) {
        this.f1546c = dVar;
        this.f1544a = baseMeshNode;
        this.f1545b = bArr;
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.c(d.f1548a, "send control msg success");
        this.f1546c.f1550c.onFastProvisionDataSend(this.f1544a, this.f1545b);
        d dVar = this.f1546c;
        dVar.a(dVar.f1549b);
    }

    @Override // com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.c(d.f1548a, "send control msg failed, errorCode: " + i2 + ", desc: " + str);
    }
}
