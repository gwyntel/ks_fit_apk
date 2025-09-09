package a.a.a.a.b.i.c;

import aisble.callback.FailCallback;
import aisble.callback.SuccessCallback;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import androidx.annotation.RequiresApi;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.gattlibrary.channel.GattTransmissionLayer;

/* loaded from: classes.dex */
public class j extends GattTransmissionLayer {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ p f1453a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public j(p pVar, Context context) {
        super(context);
        this.f1453a = pVar;
    }

    @Override // com.alibaba.ailabs.iot.gattlibrary.channel.GattTransmissionLayer, com.alibaba.ailabs.iot.aisbase.channel.ITransmissionLayer
    @RequiresApi(api = 5)
    public void connectDevice(BluetoothDevice bluetoothDevice, IActionListener<BluetoothDevice> iActionListener) {
        a.a.a.a.b.m.a.a(p.f1462a, "Start connect bluetooth device: " + bluetoothDevice.getAddress());
        connect(bluetoothDevice).done((SuccessCallback) new i(this, iActionListener)).fail((FailCallback) new h(this, iActionListener)).retry(5, 500).enqueue();
    }

    @Override // com.alibaba.ailabs.iot.gattlibrary.channel.GattTransmissionLayer, aisble.BleManagerCallbacks
    public void onDeviceReady(BluetoothDevice bluetoothDevice) {
        super.onDeviceReady(bluetoothDevice);
        if (this.f1453a.f1467f != null) {
            this.f1453a.f1467f.onReady(bluetoothDevice);
            this.f1453a.f1467f = null;
        }
    }
}
