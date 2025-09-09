package a.a.a.a.a.a;

import aisble.callback.SuccessCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.gattlibrary.channel.GattTransmissionLayer;

/* loaded from: classes.dex */
public class e implements SuccessCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1155a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ GattTransmissionLayer f1156b;

    public e(GattTransmissionLayer gattTransmissionLayer, IActionListener iActionListener) {
        this.f1156b = gattTransmissionLayer;
        this.f1155a = iActionListener;
    }

    @Override // aisble.callback.SuccessCallback
    public void onRequestCompleted(@NonNull BluetoothDevice bluetoothDevice) {
        IActionListener iActionListener = this.f1155a;
        if (iActionListener != null) {
            iActionListener.onSuccess(this.f1156b.getBluetoothDevice());
        }
    }
}
