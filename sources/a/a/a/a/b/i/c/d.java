package a.a.a.a.b.i.c;

import aisble.data.Data;
import com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.mesh.provision.bean.FastProvisionDevice;

/* loaded from: classes.dex */
public class d implements ILeScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ g f1441a;

    public d(g gVar) {
        this.f1441a = gVar;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onAliBLEDeviceFound(BluetoothDeviceWrapper bluetoothDeviceWrapper, BluetoothDeviceSubtype bluetoothDeviceSubtype) {
        a.a.a.a.b.m.a.c(this.f1441a.f1445a, "onAliBLEDeviceFound " + bluetoothDeviceWrapper.getAddress());
        if (!(bluetoothDeviceWrapper instanceof FastProvisionDevice)) {
            a.a.a.a.b.m.a.c(this.f1441a.f1445a, "device is not FastProvisionDevice " + bluetoothDeviceWrapper.getClass().getSimpleName());
            return;
        }
        if (bluetoothDeviceWrapper.getScanRecord() == null) {
            a.a.a.a.b.m.a.b(this.f1441a.f1445a, "scan record is null");
            return;
        }
        this.f1441a.f1447c.onDataReceived(bluetoothDeviceWrapper.getBluetoothDevice(), new Data(((FastProvisionDevice) bluetoothDeviceWrapper).a()));
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onStartScan() {
        a.a.a.a.b.m.a.c(this.f1441a.f1445a, "onStartScan: ");
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onStopScan() {
        a.a.a.a.b.m.a.c(this.f1441a.f1445a, "onStopScan");
    }
}
