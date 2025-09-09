package a.a.a.a.a.a;

import aisble.callback.SuccessCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.gattlibrary.channel.GattTransmissionLayer;

/* loaded from: classes.dex */
public class c implements SuccessCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1151a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ GattTransmissionLayer f1152b;

    public c(GattTransmissionLayer gattTransmissionLayer, IActionListener iActionListener) {
        this.f1152b = gattTransmissionLayer;
        this.f1151a = iActionListener;
    }

    @Override // aisble.callback.SuccessCallback
    public void onRequestCompleted(@NonNull BluetoothDevice bluetoothDevice) {
        IActionListener iActionListener = this.f1151a;
        if (iActionListener != null) {
            iActionListener.onSuccess(bluetoothDevice);
        }
    }
}
