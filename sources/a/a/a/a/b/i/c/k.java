package a.a.a.a.b.i.c;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;

/* loaded from: classes.dex */
public class k implements IActionListener<BluetoothDevice> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ p f1454a;

    public k(p pVar) {
        this.f1454a = pVar;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(p.f1462a, "connect success");
        this.f1454a.f1467f.onConnected(bluetoothDevice);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.a(p.f1462a, "connect fail:" + i2 + ";msg:" + str);
        this.f1454a.f1467f.onFailure(this.f1454a.f1464c, i2, str);
    }
}
