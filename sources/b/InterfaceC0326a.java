package b;

import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNodeData;

/* renamed from: b.a, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public interface InterfaceC0326a {

    /* renamed from: b.a$a, reason: collision with other inner class name */
    public interface InterfaceC0015a {
        void a(UnprovisionedMeshNode unprovisionedMeshNode, boolean z2);
    }

    /* renamed from: b.a$b */
    public interface b {
        void generate(String str);
    }

    void checkConfirmationValueMatches(UnprovisionedMeshNode unprovisionedMeshNode, UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr, byte[] bArr2, byte[] bArr3, InterfaceC0015a interfaceC0015a);

    void generateConfirmationValue(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr, byte[] bArr2, b bVar);
}
