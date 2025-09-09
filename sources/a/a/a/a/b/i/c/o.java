package a.a.a.a.b.i.c;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;

/* loaded from: classes.dex */
public class o implements IActionListener<BluetoothDevice> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ p f1461a;

    public o(p pVar) {
        this.f1461a = pVar;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(p.f1462a, "disconnect success");
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.a(p.f1462a, "disconnect fail:" + i2 + ";msg:" + str);
    }
}
