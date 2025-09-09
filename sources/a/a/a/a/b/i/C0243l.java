package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* renamed from: a.a.a.a.b.i.l, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0243l implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1489a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1490b;

    public C0243l(FastProvisionManager fastProvisionManager, ProvisionedMeshNode provisionedMeshNode) {
        this.f1490b = fastProvisionManager;
        this.f1489a = provisionedMeshNode;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        this.f1490b.onAddAppKeyMsgSend(this.f1489a);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f1490b.onProvisionFailed(i2, str);
    }
}
