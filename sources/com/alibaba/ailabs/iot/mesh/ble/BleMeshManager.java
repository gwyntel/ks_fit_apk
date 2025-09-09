package com.alibaba.ailabs.iot.mesh.ble;

import a.a.a.a.b.b.a;
import a.a.a.a.b.b.b;
import a.a.a.a.b.b.c;
import a.a.a.a.b.b.d;
import aisble.BleManager;
import aisble.callback.DataReceivedCallback;
import aisble.callback.FailCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class BleMeshManager extends BleManager<BleMeshManagerCallbacks> implements DataReceivedCallback {
    public static final String ERROR_RETRY_ENABLE_NOTIFICATION = "Error on retry enable notification";
    public static final String ERROR_RETRY_WRITE_CHARACTERISTIC = "Error on retry writing characteristic";
    public static final int MAX_PACKET_SIZE = 20;
    public static final int MTU_SIZE_MAX = 517;
    public static final int MTU_SIZE_MIN = 23;
    public final String TAG;
    public boolean isProvisioningComplete;
    public int mEnableNotificationRetryMaxCount;
    public final BleManager<BleMeshManagerCallbacks>.BleManagerGattCallback mGattCallback;
    public boolean mIsPreRegisterProxyService;
    public BluetoothGattCharacteristic mMeshProvisioningDataInCharacteristic;
    public BluetoothGattCharacteristic mMeshProvisioningDataOutCharacteristic;
    public BluetoothGattCharacteristic mMeshProxyDataInCharacteristic;
    public BluetoothGattCharacteristic mMeshProxyDataOutCharacteristic;
    public boolean mNeedRequestMtu;
    public int mRssiForProxyNode;
    public WriteReadType mWriteReadType;
    public int mtuSize;
    public static final UUID MESH_PROVISIONING_UUID = UUID.fromString("00001827-0000-1000-8000-00805F9B34FB");
    public static final UUID MESH_PROVISIONING_DATA_IN = UUID.fromString("00002ADB-0000-1000-8000-00805F9B34FB");
    public static final UUID MESH_PROVISIONING_DATA_OUT = UUID.fromString("00002ADC-0000-1000-8000-00805F9B34FB");
    public static final UUID MESH_PROXY_UUID = UUID.fromString("00001828-0000-1000-8000-00805F9B34FB");
    public static final UUID MESH_PROXY_DATA_IN = UUID.fromString("00002ADD-0000-1000-8000-00805F9B34FB");
    public static final UUID MESH_PROXY_DATA_OUT = UUID.fromString("00002ADE-0000-1000-8000-00805F9B34FB");

    public enum WriteReadType {
        WRITE,
        READ,
        WRITE_AND_READ
    }

    public BleMeshManager(Context context, String str) {
        super(context, str);
        this.TAG = BleMeshManager.class.getSimpleName();
        this.mtuSize = 20;
        this.mRssiForProxyNode = 0;
        this.mNeedRequestMtu = true;
        this.mEnableNotificationRetryMaxCount = 2;
        this.mIsPreRegisterProxyService = false;
        this.mWriteReadType = WriteReadType.WRITE_AND_READ;
        this.mGattCallback = new a(this);
    }

    private void checkMtuHasSilentChange(byte[] bArr) {
        if (bArr == null || bArr.length != 20 || this.mtuSize == 20 || ((bArr[0] & 192) >> 6) != 1) {
            return;
        }
        a.a.a.a.b.m.a.d(this.TAG, "ATT MTU silent change!");
        this.mtuSize = 20;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalEnableNotification(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
        a.a.a.a.b.m.a.a(this.TAG, "internalEnableNotification() called with: characteristic = [" + bluetoothGattCharacteristic + "], count = [" + i2 + "]");
        if (i2 <= 0) {
            return;
        }
        setNotificationCallback(bluetoothGattCharacteristic).with(this);
        enableNotifications(bluetoothGattCharacteristic).fail((FailCallback) new d(this, i2, bluetoothGattCharacteristic)).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 18)
    public void send(byte[] bArr, int i2) {
        Log.d(this.TAG, "Sending data : " + MeshParserUtils.bytesToHex(bArr, true));
        Log.d(this.TAG, "isProvisioningComplete: " + this.isProvisioningComplete + ", mMeshProxyDataInCharacteristic: " + this.mMeshProxyDataInCharacteristic + ", mMeshProvisioningDataInCharacteristic: " + this.mMeshProvisioningDataInCharacteristic);
        if (this.isProvisioningComplete) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.mMeshProxyDataInCharacteristic;
            if (bluetoothGattCharacteristic == null) {
                return;
            }
            bluetoothGattCharacteristic.setWriteType(1);
            writeCharacteristic(bluetoothGattCharacteristic, bArr).fail((FailCallback) new b(this, i2, bArr)).enqueue();
            return;
        }
        BluetoothGattCharacteristic bluetoothGattCharacteristic2 = this.mMeshProvisioningDataInCharacteristic;
        if (bluetoothGattCharacteristic2 == null) {
            return;
        }
        bluetoothGattCharacteristic2.setWriteType(1);
        writeCharacteristic(bluetoothGattCharacteristic2, bArr).fail((FailCallback) new c(this, i2, bArr)).enqueue();
    }

    public void changeReadWriteType(WriteReadType writeReadType) {
        if (!this.isProvisioningComplete || this.mWriteReadType == writeReadType) {
            return;
        }
        if (writeReadType == WriteReadType.WRITE) {
            disableNotifications(this.mMeshProxyDataOutCharacteristic).enqueue();
        }
        if (writeReadType == WriteReadType.WRITE_AND_READ) {
            setNotificationCallback(this.mMeshProxyDataOutCharacteristic).with(this);
            enableNotifications(this.mMeshProxyDataOutCharacteristic).enqueue();
        }
        this.mWriteReadType = writeReadType;
    }

    @RequiresApi(api = 18)
    public void discoveryServices(boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null) {
            return;
        }
        if (z2) {
            a.a.a.a.b.m.a.a(this.TAG, "Refreshing device cache...");
            a.a.a.a.b.m.a.a(this.TAG, "gatt.refresh() (hidden)");
            try {
                bluetoothGatt.getClass().getMethod(com.alipay.sdk.m.x.d.f9880w, null).invoke(bluetoothGatt, null);
            } catch (Exception e2) {
                a.a.a.a.b.m.a.d(this.TAG, "An exception occurred while refreshing device" + e2);
                a.a.a.a.b.m.a.d(this.TAG, "gatt.refresh() method not found");
            }
        }
        bluetoothGatt.discoverServices();
        a.a.a.a.b.m.a.c(this.TAG, "gatt.discoverServices() manual called");
    }

    @Override // aisble.BleManager
    public long getConnectionTimeout() {
        return 10000L;
    }

    @Override // aisble.BleManager
    public BleManager<BleMeshManagerCallbacks>.BleManagerGattCallback getGattCallback() {
        return this.mGattCallback;
    }

    public final int getMtuSize() {
        return this.mtuSize;
    }

    public int getRealtimeRssiForProxyNode() {
        return this.mRssiForProxyNode;
    }

    @Override // aisble.BleManager
    public int getServiceDiscoveryDelay(boolean z2) {
        return z2 ? 1600 : 300;
    }

    public WriteReadType getWriteReadType() {
        return this.mWriteReadType;
    }

    public boolean isProvisioningComplete() {
        return this.isProvisioningComplete;
    }

    @Override // aisble.callback.DataReceivedCallback
    public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value = data.getValue();
        checkMtuHasSilentChange(value);
        ((BleMeshManagerCallbacks) this.mCallbacks).onDataReceived(bluetoothDevice, this.mtuSize, value);
    }

    public void read() {
        a.a.a.a.b.m.a.a(this.TAG, "Active read");
        if (this.isProvisioningComplete) {
            readCharacteristic(this.mMeshProxyDataOutCharacteristic).enqueue();
        } else {
            readCharacteristic(this.mMeshProvisioningDataOutCharacteristic).with((DataReceivedCallback) this).enqueue();
        }
    }

    public void refreshGattCache() {
        refreshDeviceCache().enqueue();
    }

    public void refreshGattCacheImmediately() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null) {
            return;
        }
        a.a.a.a.b.m.a.a(this.TAG, "Refreshing device cache immediately...");
        a.a.a.a.b.m.a.a(this.TAG, "gatt.refresh() manual called (hidden)");
        try {
            bluetoothGatt.getClass().getMethod(com.alipay.sdk.m.x.d.f9880w, null).invoke(bluetoothGatt, null);
        } catch (Exception e2) {
            a.a.a.a.b.m.a.d(this.TAG, "An exception occurred while refreshing device" + e2);
            a.a.a.a.b.m.a.d(this.TAG, "gatt.refresh() method not found");
        }
    }

    @RequiresApi(api = 18)
    public void sendPdu(byte[] bArr) {
        a.a.a.a.b.m.a.a(this.TAG, "sendPdu: " + bArr);
        int length = bArr.length;
        int i2 = this.mtuSize;
        int i3 = (length + (i2 + (-1))) / i2;
        if (i3 <= 1) {
            send(bArr, 1);
            return;
        }
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            int iMin = Math.min(bArr.length - i4, this.mtuSize);
            byte[] bArr2 = new byte[iMin];
            System.arraycopy(bArr, i4, bArr2, 0, iMin);
            i4 += iMin;
            send(bArr2, 1);
        }
    }

    public void setNeedRequestMtu(boolean z2) {
        this.mNeedRequestMtu = z2;
    }

    public void setProvisioningComplete(boolean z2) {
        this.isProvisioningComplete = z2;
    }

    @Override // aisble.BleManager
    public boolean shouldAutoConnect() {
        return super.shouldAutoConnect();
    }

    @Override // aisble.BleManager
    public boolean shouldRetryDiscoveryServiceWhenDeviceNotSupported(int i2) {
        return i2 < 2;
    }

    public BleMeshManager(Context context) {
        super(context);
        this.TAG = BleMeshManager.class.getSimpleName();
        this.mtuSize = 20;
        this.mRssiForProxyNode = 0;
        this.mNeedRequestMtu = true;
        this.mEnableNotificationRetryMaxCount = 2;
        this.mIsPreRegisterProxyService = false;
        this.mWriteReadType = WriteReadType.WRITE_AND_READ;
        this.mGattCallback = new a(this);
    }
}
