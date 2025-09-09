package a.a.a.a.b.i;

import aisble.callback.DataReceivedCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.tg.utils.ConvertUtils;

/* loaded from: classes.dex */
public class z implements DataReceivedCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ J f1525a;

    public z(J j2) {
        this.f1525a = j2;
    }

    @Override // aisble.callback.DataReceivedCallback
    public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value;
        a.a.a.a.b.m.a.c(this.f1525a.f1373a, "onAliBLEDeviceFound " + bluetoothDevice.getAddress());
        if (data != null) {
            if (this.f1525a.f1381i == null || !this.f1525a.f1381i.isFastSupportGatt()) {
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
                    a.a.a.a.b.m.a.b(this.f1525a.f1373a, "payload is null");
                    return;
                }
                a.a.a.a.b.m.a.b(this.f1525a.f1373a, "payload length illegal " + value.length);
                return;
            }
            a.a.a.a.b.m.a.c(this.f1525a.f1373a, ConvertUtils.bytes2HexString(value));
            if (!this.f1525a.g()) {
                if (value[0] == 7) {
                    this.f1525a.a(value);
                    return;
                }
                return;
            }
            if (this.f1525a.f1381i != null && value[1] == this.f1525a.f1381i.getMac()[4] && value[2] == this.f1525a.f1381i.getMac()[5]) {
                a.a.a.a.b.m.a.c(this.f1525a.f1373a, "find excepted data");
                this.f1525a.b(value);
                return;
            }
            if (this.f1525a.f1381i != null && value[1] == this.f1525a.f1381i.getMac()[0] && value[2] == this.f1525a.f1381i.getMac()[1]) {
                a.a.a.a.b.m.a.c(this.f1525a.f1373a, "find ack data");
                this.f1525a.b(value);
            } else {
                if (value[0] == 7) {
                    this.f1525a.a(value);
                    return;
                }
                a.a.a.a.b.m.a.b(this.f1525a.f1373a, "failed_to_process_data " + ConvertUtils.bytes2HexString(value));
            }
        }
    }
}
