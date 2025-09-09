package a.a.a.a.b.i.c;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;

/* loaded from: classes.dex */
public class l implements IActionListener<BluetoothDevice> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1455a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ p f1456b;

    public l(p pVar, IActionListener iActionListener) {
        this.f1456b = pVar;
        this.f1455a = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(p.f1462a, "disconnect success");
        this.f1455a.onSuccess(Boolean.TRUE);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.a(p.f1462a, "disconnect fail:" + i2 + ";msg:" + str);
        this.f1455a.onFailure(i2, str);
    }
}
