package a.a.a.a.b.i.c;

import aisble.callback.DataReceivedCallback;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import androidx.annotation.RequiresApi;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class g implements a {

    /* renamed from: b, reason: collision with root package name */
    public Context f1446b;

    /* renamed from: c, reason: collision with root package name */
    public DataReceivedCallback f1447c;

    /* renamed from: a, reason: collision with root package name */
    public String f1445a = "InexpensiveMesh.AdvTransportLayer";

    /* renamed from: d, reason: collision with root package name */
    public final ILeScanCallback f1448d = new d(this);

    @Override // a.a.a.a.b.i.c.a
    public void b() {
    }

    public final void c() {
        a.a.a.a.b.m.a.c(this.f1445a, "registerFastProvisionMeshScanStrategy");
        BLEScannerProxy.getInstance().registerLeScanStrategy(4096, new f(this));
    }

    @Override // a.a.a.a.b.i.c.a
    public void init(Context context) {
        this.f1446b = context;
        c();
        a.a.a.a.a.g.c().a(context);
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(DataReceivedCallback dataReceivedCallback) {
        this.f1447c = dataReceivedCallback;
        BLEScannerProxy.getInstance().unlock();
        BLEScannerProxy.getInstance().startLeScan(this.f1446b, 60000, false, 4096, this.f1448d);
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(BluetoothDevice bluetoothDevice, IConnectCallback iConnectCallback) {
        if (iConnectCallback != null) {
            iConnectCallback.onReady(bluetoothDevice);
        }
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(IActionListener<Boolean> iActionListener) {
        Utils.notifySuccess(iActionListener, Boolean.TRUE);
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(byte[] bArr, IActionListener<byte[]> iActionListener) {
        a.a.a.a.a.g.c().b(this.f1446b);
        a.a.a.a.a.g.c().b(bArr, new e(this, iActionListener));
    }

    @Override // a.a.a.a.b.i.c.a
    public void a() {
        a.a.a.a.a.g.c().d();
        BLEScannerProxy.getInstance().stopScan(this.f1448d);
    }
}
