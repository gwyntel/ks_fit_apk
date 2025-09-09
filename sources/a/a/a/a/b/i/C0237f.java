package a.a.a.a.b.i;

import aisble.callback.DataReceivedCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import com.alibaba.ailabs.tg.utils.ConvertUtils;

/* renamed from: a.a.a.a.b.i.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0237f implements DataReceivedCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1480a;

    public C0237f(FastProvisionManager fastProvisionManager) {
        this.f1480a = fastProvisionManager;
    }

    @Override // aisble.callback.DataReceivedCallback
    public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value;
        a.a.a.a.b.m.a.c(FastProvisionManager.TAG, "onAliBLEDeviceFound " + bluetoothDevice.getAddress());
        if (data != null) {
            if (this.f1480a.unprovisionedMeshNodeData == null || !this.f1480a.unprovisionedMeshNodeData.isFastSupportGatt()) {
                value = data.getValue();
            } else {
                byte[] value2 = data.getValue();
                int length = value2 == null ? 0 : value2.length - 3;
                value = new byte[length];
                if (value2 != null) {
                    System.arraycopy(value2, 3, value, 0, length);
                }
            }
            if (value == null || value.length < 3) {
                if (value == null) {
                    a.a.a.a.b.m.a.b(FastProvisionManager.TAG, "payload is null");
                    return;
                }
                a.a.a.a.b.m.a.b(FastProvisionManager.TAG, "payload length illegal " + value.length);
                return;
            }
            a.a.a.a.b.m.a.c(FastProvisionManager.TAG, ConvertUtils.bytes2HexString(value));
            if (!this.f1480a.isProvisionStatus()) {
                if (value[0] == 7) {
                    this.f1480a.assembleControlResp(value);
                    return;
                }
                return;
            }
            if (this.f1480a.unprovisionedMeshNodeData != null && value[1] == this.f1480a.unprovisionedMeshNodeData.getMac()[4] && value[2] == this.f1480a.unprovisionedMeshNodeData.getMac()[5]) {
                a.a.a.a.b.m.a.c(FastProvisionManager.TAG, "find excepted data");
                this.f1480a.dispatchProvisionData(value);
                return;
            }
            if (this.f1480a.unprovisionedMeshNodeData != null && value[1] == this.f1480a.unprovisionedMeshNodeData.getMac()[0] && value[2] == this.f1480a.unprovisionedMeshNodeData.getMac()[1]) {
                a.a.a.a.b.m.a.c(FastProvisionManager.TAG, "find ack data");
                this.f1480a.dispatchProvisionData(value);
            } else {
                if (value[0] == 7) {
                    this.f1480a.assembleControlResp(value);
                    return;
                }
                a.a.a.a.b.m.a.b(FastProvisionManager.TAG, "failed_to_process_data " + ConvertUtils.bytes2HexString(value));
            }
        }
    }
}
