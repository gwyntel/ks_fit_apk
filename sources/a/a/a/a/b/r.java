package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import java.util.Set;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes.dex */
public class r implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f1628a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1629b;

    public r(DeviceProvisioningWorker deviceProvisioningWorker, ProvisionedMeshNode provisionedMeshNode) {
        this.f1629b = deviceProvisioningWorker;
        this.f1628a = provisionedMeshNode;
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
        this.f1629b.f8696e.a(this.f1628a, bArr);
    }
}
