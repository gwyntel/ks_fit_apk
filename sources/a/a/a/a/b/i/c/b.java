package a.a.a.a.b.i.c;

import aisble.callback.profile.ProfileDataCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;

/* loaded from: classes.dex */
public class b implements ProfileDataCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ c f1432a;

    public b(c cVar) {
        this.f1432a = cVar;
    }

    @Override // aisble.callback.DataReceivedCallback
    public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
        if (this.f1432a.f1440h != null) {
            this.f1432a.f1440h.onDataReceived(bluetoothDevice, data);
        }
    }

    @Override // aisble.callback.profile.ProfileDataCallback
    public void onInvalidDataReceived(BluetoothDevice bluetoothDevice, Data data) {
    }
}
