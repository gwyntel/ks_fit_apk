package a.a.a.a.b.i.c;

import aisble.callback.SuccessCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;

/* loaded from: classes.dex */
public class i implements SuccessCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1451a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ j f1452b;

    public i(j jVar, IActionListener iActionListener) {
        this.f1452b = jVar;
        this.f1451a = iActionListener;
    }

    @Override // aisble.callback.SuccessCallback
    public void onRequestCompleted(@NonNull BluetoothDevice bluetoothDevice) {
        IActionListener iActionListener = this.f1451a;
        if (iActionListener != null) {
            iActionListener.onSuccess(bluetoothDevice);
        }
    }
}
