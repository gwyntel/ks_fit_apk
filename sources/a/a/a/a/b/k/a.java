package a.a.a.a.b.k;

import aisble.callback.DataReceivedCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.tg.utils.ConvertUtils;

/* loaded from: classes.dex */
public class a implements DataReceivedCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ d f1543a;

    public a(d dVar) {
        this.f1543a = dVar;
    }

    @Override // aisble.callback.DataReceivedCallback
    public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
        if (this.f1543a.f1556i != null && this.f1543a.f1556i.e()) {
            a.a.a.a.b.m.a.b(d.f1548a, "Exist provision activity for tinyMesh, discard");
            return;
        }
        a.a.a.a.b.m.a.c(d.f1548a, "onAliBLEDeviceFound " + bluetoothDevice.getAddress());
        if (data != null) {
            byte[] value = data.getValue();
            if (value != null && value.length >= 3) {
                a.a.a.a.b.m.a.c(d.f1548a, ConvertUtils.bytes2HexString(value));
                if (value[0] == 7) {
                    this.f1543a.a(value);
                    return;
                }
                return;
            }
            if (value == null) {
                a.a.a.a.b.m.a.b(d.f1548a, "payload is null");
                return;
            }
            a.a.a.a.b.m.a.b(d.f1548a, "payload length illegal " + value.length);
        }
    }
}
