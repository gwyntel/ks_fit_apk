package b.e;

import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class h implements f {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ i f7451a;

    public h(i iVar) {
        this.f7451a = iVar;
    }

    @Override // b.e.f
    public void sendSegmentAcknowledgementMessage(b.d.b bVar) {
        b.d.b bVarI = this.f7451a.i(bVar);
        a.a.a.a.b.m.a.a(i.f7452r, "Sending acknowledgement: " + MeshParserUtils.bytesToHex(bVarI.m().get(0), false));
        ProvisionedMeshNode meshNode = this.f7451a.f7455u.getMeshNode(MeshParserUtils.toByteArray(bVar.l()), bVar.f());
        if (meshNode == null) {
            return;
        }
        this.f7451a.f7454t.sendPdu(meshNode, bVarI.m().get(0));
        this.f7451a.f7455u.onBlockAcknowledgementSent(meshNode);
    }
}
