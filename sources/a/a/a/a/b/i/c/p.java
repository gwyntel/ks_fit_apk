package a.a.a.a.b.i.c;

import aisble.callback.DataReceivedCallback;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.channel.ITransmissionLayer;
import com.alibaba.ailabs.iot.aisbase.channel.LayerState;
import com.alibaba.ailabs.iot.aisbase.exception.UnsupportedPluginTypeException;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;
import com.alibaba.ailabs.iot.gattlibrary.channel.GattTransmissionLayer;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;
import com.alibaba.ailabs.tg.utils.ConvertUtils;

/* loaded from: classes.dex */
public class p implements a {

    /* renamed from: a, reason: collision with root package name */
    public static String f1462a = "InexpensiveMesh.GattTransportLayer";

    /* renamed from: b, reason: collision with root package name */
    public ITransmissionLayer f1463b;

    /* renamed from: c, reason: collision with root package name */
    public BluetoothDevice f1464c;

    /* renamed from: d, reason: collision with root package name */
    public Context f1465d;

    /* renamed from: e, reason: collision with root package name */
    public c f1466e;

    /* renamed from: f, reason: collision with root package name */
    public IConnectCallback f1467f = null;

    @Override // a.a.a.a.b.i.c.a
    public void init(Context context) {
        this.f1465d = context;
        if (this.f1463b == null) {
            synchronized (this) {
                this.f1463b = new j(this, this.f1465d);
                c cVar = new c();
                this.f1466e = cVar;
                try {
                    this.f1463b.installPlugin(cVar);
                } catch (UnsupportedPluginTypeException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    @Override // a.a.a.a.b.i.c.a
    public void b() {
        c cVar;
        ITransmissionLayer iTransmissionLayer = this.f1463b;
        if (iTransmissionLayer == null || (cVar = this.f1466e) == null) {
            return;
        }
        iTransmissionLayer.uninstallPlugin(cVar);
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(DataReceivedCallback dataReceivedCallback) {
        this.f1466e.a(dataReceivedCallback);
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(BluetoothDevice bluetoothDevice, IConnectCallback iConnectCallback) {
        a.a.a.a.b.m.a.c(f1462a, "Connect...");
        this.f1464c = bluetoothDevice;
        this.f1467f = iConnectCallback;
        this.f1463b.connectDevice(bluetoothDevice, new k(this));
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(IActionListener<Boolean> iActionListener) {
        this.f1463b.disconnectDevice(new l(this, iActionListener));
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(byte[] bArr, IActionListener<byte[]> iActionListener) {
        if (a(this.f1466e, iActionListener)) {
            a.a.a.a.b.m.a.a(f1462a, "sendRawDataWithCallback data:" + ConvertUtils.bytes2HexString(bArr));
            int length = bArr == null ? 2 : bArr.length + 2;
            byte[] bArr2 = new byte[length];
            bArr2[0] = 1;
            bArr2[1] = -88;
            if (length > 2) {
                System.arraycopy(bArr, 0, bArr2, 2, bArr.length);
            }
            this.f1466e.sendRawDataWithCallback(bArr2, new m(this, iActionListener), new n(this, iActionListener));
        }
    }

    @Override // a.a.a.a.b.i.c.a
    public void a() {
        ITransmissionLayer iTransmissionLayer = this.f1463b;
        if (iTransmissionLayer != null) {
            if (iTransmissionLayer.getConnectionState() == LayerState.CONNECTED) {
                this.f1463b.disconnectDevice(new o(this));
            } else {
                ((GattTransmissionLayer) this.f1463b).close();
            }
        }
    }

    public boolean a(IPlugin iPlugin, IActionListener iActionListener) {
        ITransmissionLayer iTransmissionLayer = this.f1463b;
        if (iTransmissionLayer == null || iTransmissionLayer.getConnectionState() != LayerState.CONNECTED) {
            if (iActionListener != null) {
                iActionListener.onFailure(-1, "Not connected");
            }
            return false;
        }
        if (iPlugin != null) {
            return true;
        }
        if (iActionListener != null) {
            iActionListener.onFailure(-202, "Not supported");
        }
        return false;
    }
}
