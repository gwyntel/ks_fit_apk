package a.a.a.a.b;

import aisscanner.ScanCallback;
import aisscanner.ScanRecord;
import aisscanner.ScanResult;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import java.util.List;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes.dex */
public class da extends ScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MeshService f1337a;

    public da(MeshService meshService) {
        this.f1337a = meshService;
    }

    @Override // aisscanner.ScanCallback
    public void onBatchScanResults(List<ScanResult> list) {
    }

    @Override // aisscanner.ScanCallback
    public void onScanFailed(int i2) {
        a.a.a.a.b.m.a.b(MeshService.TAG, "onScanFailed: " + i2);
    }

    @Override // aisscanner.ScanCallback
    public void onScanResult(int i2, ScanResult scanResult) {
        String address;
        byte[] serviceData;
        if (!this.f1337a.mIsScanning) {
            address = scanResult.getDevice() != null ? scanResult.getDevice().getAddress() : "";
            a.a.a.a.b.m.a.b(MeshService.TAG, "mIsScanning:" + this.f1337a.mIsScanning + ";macAddress" + address);
            return;
        }
        ScanRecord scanRecord = scanResult.getScanRecord();
        address = scanResult.getDevice() != null ? scanResult.getDevice().getAddress() : "";
        a.a.a.a.b.m.a.a(MeshService.TAG, "scanCallback ====>" + address);
        if (TextUtils.isEmpty(address) || !this.f1337a.checkMacAddressInWhiteList(address) || scanRecord == null || (serviceData = scanRecord.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROXY_UUID))) == null) {
            return;
        }
        a.a.a.a.b.m.a.a(MeshService.TAG, "serviceData exect ====>");
        if (this.f1337a.mMeshManagerApi.f(serviceData)) {
            if (this.f1337a.checkIfNodeIdentityMatches(serviceData)) {
                this.f1337a.stopScan();
                this.f1337a.sendBroadcastProvisionedNodeFound(scanRecord.getDeviceName());
                this.f1337a.onProvisionedDeviceFound(new ExtendedBluetoothDevice(scanResult));
                return;
            }
            return;
        }
        if (this.f1337a.mMeshManagerApi.g(serviceData)) {
            if (this.f1337a.mMeshManagerApi.a(this.f1337a.mMeshManagerApi.b(MeshParserUtils.toByteArray(this.f1337a.mProvisioningSettings.h())), serviceData)) {
                this.f1337a.stopScan();
                this.f1337a.sendBroadcastProvisionedNodeFound(scanRecord.getDeviceName());
                this.f1337a.onProvisionedDeviceFound(new ExtendedBluetoothDevice(scanResult));
            }
        }
    }
}
