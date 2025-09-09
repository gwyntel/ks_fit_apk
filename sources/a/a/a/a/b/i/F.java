package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes.dex */
public class F implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1367a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ J f1368b;

    public F(J j2, ProvisionedMeshNode provisionedMeshNode) {
        this.f1368b = j2;
        this.f1367a = provisionedMeshNode;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        this.f1368b.onConfigInfoReceived(this.f1367a);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1368b.onProvisionFailed(i2, str);
    }
}
