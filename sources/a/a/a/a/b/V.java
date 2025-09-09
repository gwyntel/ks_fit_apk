package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import java.util.Set;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes.dex */
public class V implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1233a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MeshService f1234b;

    public V(MeshService meshService, ProvisionedMeshNode provisionedMeshNode) {
        this.f1234b = meshService;
        this.f1233a = provisionedMeshNode;
    }

    @Override // java.lang.Runnable
    public void run() {
        Set<Integer> flatSubscribeGroupAddress = G.a().b().getFlatSubscribeGroupAddress();
        byte[] bArr = new byte[flatSubscribeGroupAddress.size() * 2];
        int i2 = 0;
        for (Integer num : flatSubscribeGroupAddress) {
            System.arraycopy(new byte[]{(byte) ((num.intValue() >> 8) & 255), (byte) (num.intValue() & 255)}, 0, bArr, i2, 2);
            i2 += 2;
        }
        this.f1234b.mMeshManagerApi.a(this.f1233a, bArr);
    }
}
