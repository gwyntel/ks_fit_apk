package a.a.a.a.b.i;

import aisble.callback.DataReceivedCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import datasource.bean.DeviceStatus;
import java.util.LinkedList;

/* renamed from: a.a.a.a.b.i.p, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0247p implements DataReceivedCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ u f1494a;

    public C0247p(u uVar) {
        this.f1494a = uVar;
    }

    @Override // aisble.callback.DataReceivedCallback
    public void onDataReceived(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
        byte[] value;
        LinkedList linkedList;
        if (this.f1494a.f1510k == null) {
            a.a.a.a.b.m.a.d(this.f1494a.f1500a, "received data in invalid state");
            return;
        }
        if (this.f1494a.f1505f == null || !this.f1494a.f1505f.isSupportFastProvisioningV2()) {
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
                a.a.a.a.b.m.a.b(this.f1494a.f1500a, "payload is null");
                return;
            }
            a.a.a.a.b.m.a.b(this.f1494a.f1500a, "payload length illegal " + value.length);
            return;
        }
        a.a.a.a.b.m.a.c(this.f1494a.f1500a, ConvertUtils.bytes2HexString(value));
        int i2 = t.f1499a[this.f1494a.f1510k.b().ordinal()];
        if (i2 == 1) {
            if (this.f1494a.f1510k.a(value)) {
                this.f1494a.a();
                this.f1494a.d();
                this.f1494a.f();
                return;
            }
            return;
        }
        if (i2 == 2 && this.f1494a.f1510k.a(value)) {
            this.f1494a.g();
            String strC = ((a.a.a.a.b.i.b.a) this.f1494a.f1510k).c();
            if (TextUtils.isEmpty(strC)) {
                linkedList = null;
            } else {
                DeviceStatus deviceStatus = new DeviceStatus();
                deviceStatus.setUserId("");
                deviceStatus.setUuid("");
                deviceStatus.setUnicastAddress(this.f1494a.f1512m.getPrimaryUnicastAddress().intValue());
                deviceStatus.setStatus(strC);
                linkedList = new LinkedList();
                linkedList.add(deviceStatus);
            }
            if (this.f1494a.f1503d != null) {
                this.f1494a.f1503d.onProvisioningComplete(this.f1494a.f1507h, linkedList);
            }
        }
    }
}
