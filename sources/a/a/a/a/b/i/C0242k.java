package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* renamed from: a.a.a.a.b.i.k, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0242k implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1487a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1488b;

    public C0242k(FastProvisionManager fastProvisionManager, ProvisionedMeshNode provisionedMeshNode) {
        this.f1488b = fastProvisionManager;
        this.f1487a = provisionedMeshNode;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        this.f1488b.onConfigInfoReceived(this.f1487a);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1488b.onProvisionFailed(i2, str);
    }
}
