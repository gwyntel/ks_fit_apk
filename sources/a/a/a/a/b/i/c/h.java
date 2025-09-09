package a.a.a.a.b.i.c;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;

/* loaded from: classes.dex */
public class h implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1449a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ j f1450b;

    public h(j jVar, IActionListener iActionListener) {
        this.f1450b = jVar;
        this.f1449a = iActionListener;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        IActionListener iActionListener = this.f1449a;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, "");
        }
    }
}
