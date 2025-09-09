package com.yc.utesdk.ble.close;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.alipay.sdk.m.x.d;
import com.yc.utesdk.ble.open.UteBleConnection;
import com.yc.utesdk.ble.open.UteBleDevice;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class UteBleDeviceImp implements UteBleDevice {
    private static UteBleDeviceImp instance;

    /* renamed from: a, reason: collision with root package name */
    Context f24887a;
    private int devicePlatform = -1;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    private UteBleConnection mUteBleConnection;

    public class utendo implements Runnable {
        public utendo() {
        }

        @Override // java.lang.Runnable
        public void run() {
            UteBleDeviceImp.this.utendo();
        }
    }

    public UteBleDeviceImp(Context context, BluetoothManager bluetoothManager) {
        this.f24887a = context;
        this.mBluetoothManager = bluetoothManager;
        this.mUteBleConnection = UteBleConnectionImp.getInstance(context);
    }

    public static synchronized UteBleDeviceImp getInstance(Context context, BluetoothManager bluetoothManager) {
        try {
            if (instance == null) {
                if (context == null) {
                    LogUtils.i("The provided context must not be null!");
                } else {
                    instance = new UteBleDeviceImp(context, bluetoothManager);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public UteBleConnection connect(String str) {
        String str2;
        LogUtils.i("UteBleConnection connect() address =" + str + ",bleConnectState = " + UteListenerManager.getInstance().getBleConnectState());
        if (!isBluetoothEnable()) {
            str2 = "Please turn on your phone's bluetooth.";
        } else if (isConnected()) {
            str2 = "The device is connected and does not need to be connected again.";
        } else {
            if (UteListenerManager.getInstance().getBleConnectState() != 1) {
                this.mBluetoothDevice = this.mBluetoothManager.getAdapter().getRemoteDevice(str);
                UteBluetoothGattCallback uteBluetoothGattCallback = UteBluetoothGattCallback.getInstance();
                BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
                if (bluetoothDevice != null) {
                    BluetoothGatt bluetoothGattConnectGatt = bluetoothDevice.connectGatt(this.f24887a, false, uteBluetoothGattCallback, 2);
                    this.mBluetoothGatt = bluetoothGattConnectGatt;
                    uteBluetoothGattCallback.setBluetoothGatt(bluetoothGattConnectGatt);
                    UteListenerManager.getInstance().onConnecteStateChange(1);
                } else {
                    LogUtils.w("BluetoothDevice not  found.  Unable to connect.");
                    UteListenerManager.getInstance().onConnecteStateChange(0);
                }
                LogUtils.i("mBluetoothGatt =" + this.mBluetoothGatt + ",mBluetoothDevice =" + this.mBluetoothDevice);
                return this.mUteBleConnection;
            }
            str2 = "The device is connecting. Please wait";
        }
        LogUtils.e(str2);
        return this.mUteBleConnection;
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public void disconnect() throws InterruptedException {
        LogUtils.i("disconnect()");
        synchronized (UteBleDeviceImp.class) {
            try {
                BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
                if (bluetoothGatt != null) {
                    LogUtils.i("isRefresh =" + utendo(bluetoothGatt));
                    this.mBluetoothGatt.close();
                } else {
                    LogUtils.i("disconnect() mBluetoothGatt =" + this.mBluetoothGatt);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        LogUtils.i("disconnect() tID =" + Thread.currentThread().getId());
        if (utenif()) {
            new Handler().postDelayed(new utendo(), 50L);
            return;
        }
        try {
            Thread.sleep(50L);
            utendo();
        } catch (InterruptedException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public BluetoothDevice getBluetoothDevice() {
        return this.mBluetoothDevice;
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public BluetoothGatt getBluetoothGatt() {
        return this.mBluetoothGatt;
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public String getDeviceAddress() {
        BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
        if (bluetoothDevice != null) {
            return bluetoothDevice.getAddress();
        }
        LogUtils.i("getDeviceAddress() mBluetoothDevice =" + this.mBluetoothDevice);
        return null;
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public String getDeviceName() {
        BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
        if (bluetoothDevice != null) {
            return bluetoothDevice.getName();
        }
        LogUtils.i("getDeviceName() mBluetoothDevice =" + this.mBluetoothDevice);
        return null;
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public int getDevicePlatform() {
        return this.devicePlatform;
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public int getPhoneMtuSize() {
        return SPUtil.getInstance().getMaxCommunicationLength();
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public boolean isBluetoothEnable() {
        if (this.mBluetoothManager.getAdapter() == null) {
            return false;
        }
        return this.mBluetoothManager.getAdapter().isEnabled();
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public boolean isConnected() {
        BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
        boolean z2 = false;
        if (bluetoothDevice != null) {
            boolean z3 = this.mBluetoothManager.getConnectionState(bluetoothDevice, 7) == 2;
            boolean z4 = UteListenerManager.getInstance().getBleConnectState() == 2;
            if (z3 && z4) {
                z2 = true;
            }
            LogUtils.i("gattConnected = " + z3 + ",bleConnectState = " + z4);
        }
        LogUtils.i("isConnected = " + z2);
        return z2;
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public boolean isDeviceBusy() {
        return DeviceBusyLockUtils.getInstance().getDeviceBusy();
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public void setDevicePlatform(int i2) {
        this.devicePlatform = i2;
    }

    public final boolean utenif() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    @Override // com.yc.utesdk.ble.open.UteBleDevice
    public boolean isConnected(String str) {
        BluetoothDevice remoteDevice = this.mBluetoothManager.getAdapter().getRemoteDevice(str);
        boolean z2 = false;
        if (remoteDevice != null) {
            boolean z3 = this.mBluetoothManager.getConnectionState(remoteDevice, 7) == 2;
            boolean z4 = UteListenerManager.getInstance().getBleConnectState() == 2;
            if (z3 && z4) {
                z2 = true;
            }
            LogUtils.i("gattConnected = " + z3 + ",bleConnectState = " + z4);
        }
        LogUtils.i("isConnected = " + z2);
        return z2;
    }

    public final void utendo() {
        UteBluetoothGattCallback.getInstance().removeHandler();
        UteListenerManager.getInstance().onConnecteStateChange(0);
    }

    public final boolean utendo(BluetoothGatt bluetoothGatt) throws NoSuchMethodException, SecurityException {
        if (bluetoothGatt == null) {
            return false;
        }
        try {
            Method method = bluetoothGatt.getClass().getMethod(d.f9880w, null);
            if (method != null) {
                return ((Boolean) method.invoke(bluetoothGatt, null)).booleanValue();
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
