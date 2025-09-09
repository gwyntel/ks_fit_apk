package a.a.a.a.b.i.c;

import aisble.callback.DataReceivedCallback;
import aisble.callback.DataSentCallback;
import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import com.alibaba.ailabs.iot.aisbase.channel.ITransmissionLayer;
import com.alibaba.ailabs.iot.aisbase.channel.LayerState;
import com.alibaba.ailabs.iot.aisbase.exception.UnsupportedLayerException;
import com.alibaba.ailabs.iot.aisbase.spec.AISCommand;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.gattlibrary.channel.GattTransmissionLayer;
import com.alibaba.ailabs.iot.gattlibrary.plugin.BluetoothGattPlugin;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import java.util.UUID;

/* loaded from: classes.dex */
public class c implements BluetoothGattPlugin {

    /* renamed from: a, reason: collision with root package name */
    public static final UUID f1433a = UUID.fromString("00002ADB-0000-1000-8000-00805F9B34FB");

    /* renamed from: b, reason: collision with root package name */
    public static final UUID f1434b = UUID.fromString("00002ADC-0000-1000-8000-00805F9B34FB");

    /* renamed from: c, reason: collision with root package name */
    public String f1435c = "ProvisionPlugin";

    /* renamed from: d, reason: collision with root package name */
    public GattTransmissionLayer f1436d;

    /* renamed from: e, reason: collision with root package name */
    public BluetoothDevice f1437e;

    /* renamed from: f, reason: collision with root package name */
    public BluetoothGattCharacteristic f1438f;

    /* renamed from: g, reason: collision with root package name */
    public BluetoothGattCharacteristic f1439g;

    /* renamed from: h, reason: collision with root package name */
    public DataReceivedCallback f1440h;

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void enableAESEncryption(byte[] bArr) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public BluetoothDeviceWrapper getBluetoothDeviceWrapper() {
        return null;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void init(ITransmissionLayer iTransmissionLayer) throws UnsupportedLayerException {
        if (!(iTransmissionLayer instanceof GattTransmissionLayer)) {
            throw new UnsupportedLayerException();
        }
        GattTransmissionLayer gattTransmissionLayer = (GattTransmissionLayer) iTransmissionLayer;
        this.f1436d = gattTransmissionLayer;
        this.f1437e = gattTransmissionLayer.getBluetoothDevice();
        this.f1436d.setNotificationCallback(this.f1439g).with(new b(this));
        this.f1436d.enableNotifications(this.f1439g).enqueue();
    }

    @Override // com.alibaba.ailabs.iot.gattlibrary.plugin.BluetoothGattPlugin
    public boolean isCommandSupported(byte b2) {
        return false;
    }

    @Override // com.alibaba.ailabs.iot.gattlibrary.plugin.BluetoothGattPlugin
    public boolean isRequiredServiceSupported(BluetoothGatt bluetoothGatt) {
        BluetoothGattService service = bluetoothGatt.getService(BleMeshManager.MESH_PROVISIONING_UUID);
        if (service == null) {
            return false;
        }
        this.f1438f = service.getCharacteristic(f1433a);
        this.f1439g = service.getCharacteristic(f1434b);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.f1438f;
        return this.f1438f != null && (bluetoothGattCharacteristic != null && (bluetoothGattCharacteristic.getProperties() & 4) > 0);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void onBluetoothConnectionStateChanged(int i2) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void onDeviceReady() {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public AISCommand sendCommandWithCallback(byte b2, byte[] bArr, DataSentCallback dataSentCallback, FailCallback failCallback) {
        return null;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void sendRawDataWithCallback(byte[] bArr, DataSentCallback dataSentCallback, FailCallback failCallback) {
        GattTransmissionLayer gattTransmissionLayer = this.f1436d;
        if (gattTransmissionLayer == null || gattTransmissionLayer.getConnectionState() != LayerState.CONNECTED) {
            failCallback.onRequestFailed(this.f1437e, -1);
        } else {
            this.f1438f.setWriteType(1);
            this.f1436d.writeCharacteristic(this.f1438f, bArr).with(dataSentCallback).fail(failCallback).enqueue();
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void setBluetoothDeviceWrapper(BluetoothDeviceWrapper bluetoothDeviceWrapper) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void start() {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void stop() {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void uninstall() {
    }

    public void a(DataReceivedCallback dataReceivedCallback) {
        this.f1440h = dataReceivedCallback;
    }
}
