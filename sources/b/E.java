package b;

import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;

/* loaded from: classes2.dex */
public class E implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ExtendedBluetoothDevice f7307a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ K f7308b;

    public E(K k2, ExtendedBluetoothDevice extendedBluetoothDevice) {
        this.f7308b = k2;
        this.f7307a = extendedBluetoothDevice;
    }

    @Override // java.lang.Runnable
    public void run() {
        BleMeshManager bleMeshManagerA = this.f7308b.a(this.f7307a, false);
        if (bleMeshManagerA != null && bleMeshManagerA.isConnected()) {
            a.a.a.a.b.m.a.d(K.f7315a, String.format("Status error, timeout occurred in the connection(%s) state", this.f7307a.getAddress()));
            return;
        }
        if (bleMeshManagerA != null) {
            bleMeshManagerA.close();
        }
        this.f7308b.c(this.f7307a.getDevice());
        this.f7308b.f7339y.remove(this.f7307a.getAddress());
    }
}
