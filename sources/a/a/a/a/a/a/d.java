package a.a.a.a.a.a;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.gattlibrary.channel.GattTransmissionLayer;

/* loaded from: classes.dex */
public class d implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1153a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ GattTransmissionLayer f1154b;

    public d(GattTransmissionLayer gattTransmissionLayer, IActionListener iActionListener) {
        this.f1154b = gattTransmissionLayer;
        this.f1153a = iActionListener;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        IActionListener iActionListener = this.f1153a;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, String.format("Disconnect: %s failed", bluetoothDevice.getAddress()));
        }
    }
}
