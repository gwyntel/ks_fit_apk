package a.a.a.a.b.i;

import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;

/* loaded from: classes.dex */
public class M implements SIGMeshBizRequest.b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ P f1401a;

    public M(P p2) {
        this.f1401a = p2;
    }

    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.b
    public byte[] getEncodedParameters() {
        return C0232a.a((String) this.f1401a.f1408e.get("ssid"), (String) this.f1401a.f1408e.get("password"), this.f1401a.f1408e.containsKey(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_BSSID) ? (String) this.f1401a.f1408e.get(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_BSSID) : null, this.f1401a.f1408e.get(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_REGION_INDEX) != null ? ((Byte) this.f1401a.f1408e.get(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_REGION_INDEX)).byteValue() : (byte) 0);
    }
}
