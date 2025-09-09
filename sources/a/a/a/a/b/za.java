package a.a.a.a.b;

import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.mesh.TgScanManager;
import com.alibaba.ailabs.iot.mesh.UnprovisionedBluetoothMeshDevice;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes.dex */
public class za implements ILeScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f1652a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1653b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ TgScanManager f1654c;

    public za(TgScanManager tgScanManager, String str, IActionListener iActionListener) {
        this.f1654c = tgScanManager;
        this.f1652a = str;
        this.f1653b = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onAliBLEDeviceFound(BluetoothDeviceWrapper bluetoothDeviceWrapper, BluetoothDeviceSubtype bluetoothDeviceSubtype) {
        if (bluetoothDeviceWrapper instanceof UnprovisionedBluetoothMeshDevice) {
            UnprovisionedBluetoothMeshDevice unprovisionedBluetoothMeshDevice = (UnprovisionedBluetoothMeshDevice) bluetoothDeviceWrapper;
            int sigMeshProductID = unprovisionedBluetoothMeshDevice.getSigMeshProductID();
            String strValueOf = String.valueOf(sigMeshProductID);
            if (this.f1654c.isMultiConfigId(this.f1652a, sigMeshProductID)) {
                this.f1654c.mGetRemoteSpecifiedPIDDeviceSuccess = true;
                BLEScannerProxy.getInstance().stopScan(this);
                IActionListener iActionListener = this.f1653b;
                if (iActionListener != null) {
                    iActionListener.onSuccess(unprovisionedBluetoothMeshDevice);
                    return;
                }
                return;
            }
            if (TextUtils.isEmpty(this.f1652a) || TmpConstant.GROUP_ROLE_UNKNOWN.equals(this.f1652a) || this.f1652a.equals(strValueOf)) {
                this.f1654c.mGetRemoteSpecifiedPIDDeviceSuccess = true;
                IActionListener iActionListener2 = this.f1653b;
                if (iActionListener2 != null) {
                    iActionListener2.onSuccess(unprovisionedBluetoothMeshDevice);
                }
            }
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onStartScan() {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onStopScan() {
        IActionListener iActionListener;
        a.a.a.a.b.m.a.a(TgScanManager.TAG, "Scan finished");
        if (this.f1654c.mGetRemoteSpecifiedPIDDeviceSuccess || (iActionListener = this.f1653b) == null) {
            return;
        }
        iActionListener.onFailure(-5, "Scanning device failed");
    }
}
