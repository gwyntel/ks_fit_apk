package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes.dex */
public class ia implements IActionListener<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1526a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1527b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ byte[] f1528c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ MeshService.b f1529d;

    public ia(MeshService.b bVar, IActionListener iActionListener, ProvisionedMeshNode provisionedMeshNode, byte[] bArr) {
        this.f1529d = bVar;
        this.f1526a = iActionListener;
        this.f1527b = provisionedMeshNode;
        this.f1528c = bArr;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(String str) {
        Utils.notifySuccess((IActionListener<String>) this.f1526a, str);
        ProvisionedMeshNode provisionedMeshNode = this.f1527b;
        if (provisionedMeshNode != null) {
            this.f1529d.a(provisionedMeshNode.getUnicastAddress(), 13936641, this.f1528c);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        Utils.notifyFailed(this.f1526a, i2, str);
        ProvisionedMeshNode provisionedMeshNode = this.f1527b;
        if (provisionedMeshNode != null) {
            this.f1529d.a(provisionedMeshNode.getUnicastAddress(), 13936641, this.f1528c);
        }
    }
}
