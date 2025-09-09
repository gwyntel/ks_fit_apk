package a.a.a.a.b;

import android.util.SparseArray;
import b.u;
import datasource.bean.ProvisionAppKey;
import datasource.bean.SigmeshKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.MeshParserUtils;

/* renamed from: a.a.a.a.b.aa, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0223aa implements b.e.g {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ca f1298a;

    public C0223aa(ca caVar) {
        this.f1298a = caVar;
    }

    @Override // b.e.g
    public byte[] a(String str) {
        return new byte[]{0, 0, 0, 0};
    }

    @Override // b.e.g
    public byte[] b(String str, byte[] bArr) {
        ProvisionedMeshNode provisionedMeshNode = (ProvisionedMeshNode) G.a().d().a(str, bArr);
        return provisionedMeshNode != null ? provisionedMeshNode.getDeviceKey() : new byte[0];
    }

    @Override // b.e.g
    public List<byte[]> a(String str, byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        ProvisionedMeshNode provisionedMeshNode = (ProvisionedMeshNode) G.a().d().a(str, bArr);
        if (provisionedMeshNode != null) {
            Iterator<Map.Entry<Integer, String>> it = provisionedMeshNode.getAddedAppKeys().entrySet().iterator();
            while (it.hasNext()) {
                arrayList.add(MeshParserUtils.toByteArray(it.next().getValue()));
            }
        } else {
            a.a.a.a.b.m.a.b("MeshService", "Failed to get appKeys, can't find the corresponding mesh node");
            SigmeshKey sigmeshKey = (SigmeshKey) this.f1298a.f1331a.mSigmeshKeys.get(this.f1298a.f1331a.mNetKeyIndexes == null ? 0 : ((Integer) this.f1298a.f1331a.mNetKeyIndexes.get(0)).intValue());
            if (sigmeshKey == null || sigmeshKey.getProvisionAppKeys() == null) {
                u.a aVarD = G.a().d().d();
                if (aVarD != null) {
                    SparseArray<byte[]> sparseArrayA = aVarD.a();
                    for (int i2 = 0; i2 < sparseArrayA.size(); i2++) {
                        arrayList.add(sparseArrayA.get(sparseArrayA.keyAt(i2)));
                    }
                }
            } else {
                Iterator<ProvisionAppKey> it2 = sigmeshKey.getProvisionAppKeys().iterator();
                while (it2.hasNext()) {
                    arrayList.add(MeshParserUtils.toByteArray(it2.next().getAppKey()));
                }
            }
        }
        return arrayList;
    }
}
