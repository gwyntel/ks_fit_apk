package b;

import android.content.Context;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class A implements InterfaceC0338m {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ K f7302a;

    public A(K k2) {
        this.f7302a = k2;
    }

    @Override // b.InterfaceC0338m
    public int getMtu() {
        return 20;
    }

    @Override // b.InterfaceC0338m
    public void sendPdu(BaseMeshNode baseMeshNode, byte[] bArr) {
        a.a.a.a.b.m.a.a(K.f7315a, String.format("Send data to node(%s) via adv channel", MeshParserUtils.bytesToHex(baseMeshNode.getUnicastAddress(), false)));
        if (FastProvisionManager.getInstance().getInProvisionProgress()) {
            a.a.a.a.b.m.a.b(K.f7315a, "Exist provision activity for tinyMesh, discard");
            return;
        }
        if (this.f7302a.f7334t == null) {
            K k2 = this.f7302a;
            Context context = k2.f7319e;
            byte[] bArrD = this.f7302a.f7333s.d();
            K k3 = this.f7302a;
            k2.f7334t = new a.a.a.a.b.k.d(context, bArrD, k3, k3.f7337w);
        }
        this.f7302a.f7334t.a(baseMeshNode, bArr);
        a.a.a.a.b.l.c.a(baseMeshNode.getUnicastAddressInt(), "1");
    }
}
