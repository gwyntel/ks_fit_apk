package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes.dex */
public class G implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1369a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ J f1370b;

    public G(J j2, ProvisionedMeshNode provisionedMeshNode) {
        this.f1370b = j2;
        this.f1369a = provisionedMeshNode;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        this.f1370b.onAddAppKeyMsgSend(this.f1369a);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1370b.onProvisionFailed(i2, str);
    }
}
