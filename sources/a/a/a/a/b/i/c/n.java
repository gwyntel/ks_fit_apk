package a.a.a.a.b.i.c;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* loaded from: classes.dex */
public class n implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1459a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ p f1460b;

    public n(p pVar, IActionListener iActionListener) {
        this.f1460b = pVar;
        this.f1459a = iActionListener;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(BluetoothDevice bluetoothDevice, int i2) {
        Utils.notifyFailed(this.f1459a, i2, "Write failed");
    }
}
