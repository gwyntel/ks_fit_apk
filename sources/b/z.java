package b;

import java.util.Set;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes2.dex */
public class z implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0337l f7589a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f7590b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ K f7591c;

    public z(K k2, C0337l c0337l, ProvisionedMeshNode provisionedMeshNode) {
        this.f7591c = k2;
        this.f7589a = c0337l;
        this.f7590b = provisionedMeshNode;
    }

    @Override // java.lang.Runnable
    public void run() {
        Set<Integer> flatSubscribeGroupAddress;
        if (a.a.a.a.b.G.a().c() == null || (flatSubscribeGroupAddress = a.a.a.a.b.G.a().c().getFlatSubscribeGroupAddress()) == null || flatSubscribeGroupAddress.size() == 0) {
            return;
        }
        byte[] bArr = new byte[flatSubscribeGroupAddress.size() * 2];
        int i2 = 0;
        for (Integer num : flatSubscribeGroupAddress) {
            System.arraycopy(new byte[]{(byte) ((num.intValue() >> 8) & 255), (byte) (num.intValue() & 255)}, 0, bArr, i2, 2);
            i2 += 2;
        }
        this.f7589a.a(this.f7590b, bArr);
    }
}
