package a.a.a.a.b;

import aisscanner.ScanCallback;
import aisscanner.ScanRecord;
import aisscanner.ScanResult;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import java.util.List;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes.dex */
public class A extends ScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1189a;

    public A(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1189a = deviceProvisioningWorker;
    }

    @Override // aisscanner.ScanCallback
    public void onBatchScanResults(List<ScanResult> list) {
    }

    @Override // aisscanner.ScanCallback
    public void onScanFailed(int i2) {
        a.a.a.a.b.m.a.b(this.f1189a.f8693b, "onScanFailed: " + i2);
    }

    @Override // aisscanner.ScanCallback
    public void onScanResult(int i2, ScanResult scanResult) throws InterruptedException {
        String address;
        byte[] serviceData;
        if (!this.f1189a.J) {
            address = scanResult.getDevice() != null ? scanResult.getDevice().getAddress() : "";
            a.a.a.a.b.m.a.b(this.f1189a.f8693b, "mIsScanning:" + this.f1189a.J + ";macAddress" + address);
            return;
        }
        ScanRecord scanRecord = scanResult.getScanRecord();
        address = scanResult.getDevice() != null ? scanResult.getDevice().getAddress() : "";
        a.a.a.a.b.m.a.a(this.f1189a.f8693b, "scanCallback ====>" + address);
        if (TextUtils.isEmpty(address) || !this.f1189a.a(address) || scanRecord == null || (serviceData = scanRecord.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROXY_UUID))) == null) {
            return;
        }
        a.a.a.a.b.m.a.a(this.f1189a.f8693b, "serviceData exect ====>");
        if (this.f1189a.f8696e.f(serviceData)) {
            if (this.f1189a.a(serviceData)) {
                this.f1189a.s();
                this.f1189a.c(scanRecord.getDeviceName());
                this.f1189a.b(new ExtendedBluetoothDevice(scanResult));
                return;
            }
            return;
        }
        if (this.f1189a.f8696e.g(serviceData)) {
            if (this.f1189a.f8696e.a(this.f1189a.f8696e.b(MeshParserUtils.toByteArray(this.f1189a.f8704m.h())), serviceData)) {
                this.f1189a.s();
                this.f1189a.c(scanRecord.getDeviceName());
                this.f1189a.b(new ExtendedBluetoothDevice(scanResult));
            }
        }
    }
}
