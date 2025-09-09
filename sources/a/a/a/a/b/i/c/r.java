package a.a.a.a.b.i.c;

import a.a.a.a.b.C0224b;
import aisble.callback.DataReceivedCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.media3.exoplayer.ExoPlayer;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.ConvertUtils;

/* loaded from: classes.dex */
public class r implements a, BleMeshManagerCallbacks {

    /* renamed from: b, reason: collision with root package name */
    public BleMeshManager f1470b;

    /* renamed from: c, reason: collision with root package name */
    public DataReceivedCallback f1471c;

    /* renamed from: d, reason: collision with root package name */
    public BluetoothDevice f1472d;

    /* renamed from: g, reason: collision with root package name */
    public String f1475g;

    /* renamed from: i, reason: collision with root package name */
    public Runnable f1477i;

    /* renamed from: a, reason: collision with root package name */
    public String f1469a = "InexpensiveMesh.GattTransportLayer";

    /* renamed from: e, reason: collision with root package name */
    public IConnectCallback f1473e = null;

    /* renamed from: f, reason: collision with root package name */
    public IActionListener f1474f = null;

    /* renamed from: h, reason: collision with root package name */
    public Handler f1476h = new Handler(Looper.getMainLooper());

    public r(String str) {
        this.f1469a += str;
        this.f1475g = str;
    }

    @Override // a.a.a.a.b.i.c.a
    public void b() {
    }

    public final void c() {
        Runnable runnable = this.f1477i;
        if (runnable != null) {
            this.f1476h.removeCallbacks(runnable);
            this.f1477i = null;
        }
    }

    public BleMeshManager d() {
        return this.f1470b;
    }

    public final void e() {
        Handler handler = this.f1476h;
        q qVar = new q(this);
        this.f1477i = qVar;
        handler.postDelayed(qVar, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    @Override // a.a.a.a.b.i.c.a
    public void init(Context context) {
        BleMeshManager bleMeshManager = new BleMeshManager(context, this.f1475g);
        this.f1470b = bleMeshManager;
        bleMeshManager.setProvisioningComplete(false);
        this.f1470b.setGattCallbacks(this);
    }

    @Override // aisble.BleManagerCallbacks
    public void onBatteryValueReceived(BluetoothDevice bluetoothDevice, int i2) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onBonded(BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onBondingFailed(BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onBondingRequired(BluetoothDevice bluetoothDevice) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks
    public void onDataReceived(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        a.a.a.a.b.m.a.a(this.f1469a, "onDataReceived, device: " + bluetoothDevice.getName() + ", mac: " + bluetoothDevice.getAddress() + ", mtu: " + i2 + ", pdu: " + bArr);
        DataReceivedCallback dataReceivedCallback = this.f1471c;
        if (dataReceivedCallback != null) {
            dataReceivedCallback.onDataReceived(bluetoothDevice, new Data(bArr));
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks
    public void onDataSent(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceConnected(BluetoothDevice bluetoothDevice) {
        e();
        IConnectCallback iConnectCallback = this.f1473e;
        if (iConnectCallback != null) {
            iConnectCallback.onConnected(bluetoothDevice);
        }
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceConnecting(BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceDisconnected(BluetoothDevice bluetoothDevice) {
        IActionListener iActionListener = this.f1474f;
        if (iActionListener != null) {
            iActionListener.onSuccess(Boolean.TRUE);
            this.f1474f = null;
        }
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceDisconnecting(BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceNotSupported(BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceReady(BluetoothDevice bluetoothDevice) {
        IConnectCallback iConnectCallback = this.f1473e;
        if (iConnectCallback != null) {
            iConnectCallback.onReady(bluetoothDevice);
            this.f1473e = null;
        }
        C0224b.b().a(this.f1470b);
    }

    @Override // aisble.BleManagerCallbacks
    public void onError(BluetoothDevice bluetoothDevice, String str, int i2) {
        a.a.a.a.b.m.a.b(this.f1469a, "onError: " + str);
    }

    @Override // aisble.BleManagerCallbacks
    public void onLinkLossOccurred(BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onServicesDiscovered(BluetoothDevice bluetoothDevice, boolean z2) {
        c();
    }

    @Override // aisble.BleManagerCallbacks
    public boolean shouldEnableBatteryLevelNotifications(BluetoothDevice bluetoothDevice) {
        return false;
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(DataReceivedCallback dataReceivedCallback) {
        this.f1471c = dataReceivedCallback;
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(BluetoothDevice bluetoothDevice, IConnectCallback iConnectCallback) {
        a.a.a.a.b.m.a.c(this.f1469a, "Connect...");
        this.f1472d = bluetoothDevice;
        this.f1473e = iConnectCallback;
        this.f1470b.connect(bluetoothDevice).retry(5, 300).useAutoConnect(true).enqueue();
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(IActionListener<Boolean> iActionListener) {
        this.f1474f = iActionListener;
        this.f1470b.disconnect().enqueue();
    }

    @Override // a.a.a.a.b.i.c.a
    public void a(byte[] bArr, IActionListener<byte[]> iActionListener) {
        a.a.a.a.b.m.a.a(this.f1469a, "sendRawDataWithCallback data:" + ConvertUtils.bytes2HexString(bArr));
        int length = bArr == null ? 2 : bArr.length + 2;
        byte[] bArr2 = new byte[length];
        bArr2[0] = 1;
        bArr2[1] = -88;
        if (length > 2) {
            System.arraycopy(bArr, 0, bArr2, 2, bArr.length);
        }
        this.f1470b.sendPdu(bArr2);
        Utils.notifySuccess(iActionListener, bArr2);
    }

    @Override // a.a.a.a.b.i.c.a
    public void a() {
        if (this.f1470b.getConnectState() == 2) {
            this.f1470b.disconnect().enqueue();
        } else {
            this.f1470b.close();
        }
        C0224b.b().b(this.f1470b);
    }
}
