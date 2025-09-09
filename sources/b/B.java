package b;

import aisscanner.ScanResult;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes2.dex */
public class B implements BLEScannerProxy.IMeshNetworkPUDListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ K f7303a;

    public B(K k2) {
        this.f7303a = k2;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy.IMeshNetworkPUDListener
    public void onMeshNetworkPDURecevied(ScanResult scanResult) {
        if (scanResult == null || scanResult.getScanRecord() == null || scanResult.getScanRecord().getMeshNetworkPUD() == null) {
            return;
        }
        ProvisionedMeshNode provisionedMeshNodeA = a.a.a.a.b.G.a().d().a(scanResult.getDevice().getAddress());
        if (provisionedMeshNodeA == null) {
            return;
        }
        a.a.a.a.b.m.a.a("Multi_channel_upstream", "Recevied mesh network PUD from ADV channel");
        byte[] meshNetworkPUD = scanResult.getScanRecord().getMeshNetworkPUD();
        byte[] bArr = new byte[meshNetworkPUD.length + 1];
        bArr[0] = 0;
        System.arraycopy(meshNetworkPUD, 0, bArr, 1, meshNetworkPUD.length);
        this.f7303a.f7322h.a(provisionedMeshNodeA, 31, bArr, (a.a.a.a.b.h.a) null);
    }
}
