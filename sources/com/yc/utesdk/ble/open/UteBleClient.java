package com.yc.utesdk.ble.open;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.yc.utesdk.ble.close.UteBleConnectionImp;
import com.yc.utesdk.ble.close.UteBleDeviceImp;
import com.yc.utesdk.ble.close.UteBluetoothAdapter;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.scan.UteScanCallback;
import com.yc.utesdk.scan.UteScanManager;
import com.yc.utesdk.utils.close.UteCalculatorImp;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.utils.open.UteCalculator;
import com.yc.utesdk.watchface.close.OnlineDialTimeOut;
import java.util.Set;

/* loaded from: classes4.dex */
public class UteBleClient {
    private static UteBleClient mClient;
    private static Context mContext;

    /* renamed from: a, reason: collision with root package name */
    UteBleDevice f24888a;

    /* renamed from: b, reason: collision with root package name */
    UteBleConnection f24889b;

    /* renamed from: c, reason: collision with root package name */
    UteScanManager f24890c;
    private BluetoothManager mBluetoothManager;
    private UteBluetoothAdapter mUteBluetoothAdapter;

    public UteBleClient(Context context) {
        mContext = context;
        SPUtil.initialize(context);
        if (!mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            LogUtils.e("No support ble 4.0");
            return;
        }
        BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService("bluetooth");
        this.mBluetoothManager = bluetoothManager;
        this.mUteBluetoothAdapter = new UteBluetoothAdapter(bluetoothManager.getAdapter());
        this.f24890c = new UteScanManager(this.mBluetoothManager.getAdapter());
        this.f24888a = UteBleDeviceImp.getInstance(mContext, this.mBluetoothManager);
        this.f24889b = UteBleConnectionImp.getInstance(mContext);
        CommandTimeOutUtils.getInstance();
        OnlineDialTimeOut.getInstance();
    }

    public static Context getContext() {
        if (mContext == null) {
            LogUtils.initializeLog("UteBleClient.initialize()");
        }
        return mContext;
    }

    public static UteBleClient getUteBleClient() {
        if (mClient == null) {
            LogUtils.initializeLog("UteBleClient.initialize()");
        }
        return mClient;
    }

    public static synchronized UteBleClient initialize(@NonNull Context context) {
        try {
            if (mClient == null) {
                if (context == null) {
                    LogUtils.e("The provided context must not be null!");
                } else {
                    mClient = new UteBleClient(context.getApplicationContext());
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return getUteBleClient();
    }

    public void cancelScan() {
        this.f24890c.stopScan();
    }

    public UteBleConnection connect(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        UteBleConnection uteBleConnectionConnect = utendo().connect(str);
        this.f24889b = uteBleConnectionConnect;
        return uteBleConnectionConnect;
    }

    public void disconnect() {
        utendo().disconnect();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return this.mUteBluetoothAdapter.getBluetoothAdapter();
    }

    public BluetoothDevice getBluetoothDevice() {
        return utendo().getBluetoothDevice();
    }

    public BluetoothGatt getBluetoothGatt() {
        return utendo().getBluetoothGatt();
    }

    public BluetoothManager getBluetoothManager() {
        return this.mBluetoothManager;
    }

    public Set<BluetoothDevice> getBondedDevices() {
        return this.mUteBluetoothAdapter.getBluetoothAdapter().getBondedDevices();
    }

    public String getDeviceAddress() {
        return utendo().getDeviceAddress();
    }

    public String getDeviceName() {
        return utendo().getDeviceName();
    }

    public int getDevicePlatform() {
        return utendo().getDevicePlatform();
    }

    public int getPhoneMtuSize() {
        return utendo().getPhoneMtuSize();
    }

    public UteBleConnection getUteBleConnection() {
        if (this.f24889b == null) {
            LogUtils.initializeLog("UteBleClient.connect()");
        }
        return this.f24889b;
    }

    public UteCalculator getUteCalculator() {
        return UteCalculatorImp.getInstance();
    }

    public boolean isBluetoothEnable() {
        return utendo().isBluetoothEnable();
    }

    public boolean isConnected() {
        return utendo().isConnected();
    }

    public boolean isDeviceBusy() {
        return utendo().isDeviceBusy();
    }

    public boolean scanDevice(UteScanCallback uteScanCallback, long j2) {
        return this.f24890c.startScan(uteScanCallback, j2);
    }

    public void setDevicePlatform(int i2) {
        utendo().setDevicePlatform(i2);
    }

    public final UteBleDevice utendo() {
        if (this.f24888a == null) {
            LogUtils.initializeLog("UteBleClient.initialize()");
        }
        return this.f24888a;
    }

    public boolean isConnected(String str) {
        return utendo().isConnected(str);
    }
}
