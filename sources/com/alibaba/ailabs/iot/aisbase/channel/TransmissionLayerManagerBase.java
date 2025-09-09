package com.alibaba.ailabs.iot.aisbase.channel;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.alibaba.ailabs.iot.aisbase.A;
import com.alibaba.ailabs.iot.aisbase.B;
import com.alibaba.ailabs.iot.aisbase.C;
import com.alibaba.ailabs.iot.aisbase.C0437z;
import com.alibaba.ailabs.iot.aisbase.D;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ITransmissionLayerCallback;
import com.alibaba.ailabs.iot.aisbase.exception.UnsupportedPluginTypeException;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public abstract class TransmissionLayerManagerBase {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8375a = "TransmissionLayerManagerBase";

    /* renamed from: b, reason: collision with root package name */
    public ITransmissionLayer f8376b;

    /* renamed from: c, reason: collision with root package name */
    public ITransmissionLayerCallback f8377c;

    /* renamed from: d, reason: collision with root package name */
    public Context f8378d;

    /* renamed from: e, reason: collision with root package name */
    public BluetoothDevice f8379e;

    /* renamed from: h, reason: collision with root package name */
    public IActionListener<BluetoothDevice> f8382h;

    /* renamed from: f, reason: collision with root package name */
    public int f8380f = -1;

    /* renamed from: g, reason: collision with root package name */
    public int f8381g = 0;

    /* renamed from: i, reason: collision with root package name */
    public BroadcastReceiver f8383i = new C0437z(this);

    public TransmissionLayerManagerBase(Context context, BluetoothDeviceWrapper bluetoothDeviceWrapper, TransmissionLayer transmissionLayer) {
        this.f8378d = context;
        this.f8379e = bluetoothDeviceWrapper.getBluetoothDevice();
        this.f8376b = createTransmissionLayer(context, transmissionLayer);
        c();
    }

    public void bind(BluetoothDevice bluetoothDevice, IActionListener<BluetoothDevice> iActionListener) {
        if (iActionListener == null) {
            iActionListener = new A(this);
        }
        this.f8382h = iActionListener;
        if (bluetoothDevice.getBondState() != 10 || bluetoothDevice.createBond()) {
            return;
        }
        this.f8382h.onFailure(-204, "");
    }

    public void connectToA2DP(BluetoothDevice bluetoothDevice) {
        b();
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.f8378d, new B(this, bluetoothDevice), 2);
    }

    public abstract ITransmissionLayer createTransmissionLayer(Context context, TransmissionLayer transmissionLayer);

    public void disconnectToA2DP(BluetoothDevice bluetoothDevice) {
        b();
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.f8378d, new C(this, bluetoothDevice), 2);
    }

    public void dynamicInstallPlugin(IPlugin iPlugin) throws UnsupportedPluginTypeException {
        ITransmissionLayer iTransmissionLayer = this.f8376b;
        if (iTransmissionLayer != null) {
            iTransmissionLayer.installPlugin(iPlugin);
        }
    }

    public int getA2DPConnectionState() {
        return this.f8381g;
    }

    public int getActiveMethodType(BluetoothA2dp bluetoothA2dp) throws SecurityException {
        if (bluetoothA2dp == null) {
            return 0;
        }
        try {
            for (Method method : bluetoothA2dp.getClass().getMethods()) {
                if (method.getName().equalsIgnoreCase("getActiveDevice")) {
                    return 1;
                }
                if (method.getName().equalsIgnoreCase("semGetActiveStreamDevice")) {
                    return 3;
                }
                if (method.getName().equalsIgnoreCase("getActiveStreamDevice")) {
                    return 2;
                }
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    public TransmissionLayer getLayerType() {
        ITransmissionLayer iTransmissionLayer = this.f8376b;
        return iTransmissionLayer == null ? TransmissionLayer.NONE : iTransmissionLayer.getLayer();
    }

    public ITransmissionLayer getTransmissionLayer() {
        return this.f8376b;
    }

    public void installPlugin(IPlugin iPlugin) throws UnsupportedPluginTypeException {
        ITransmissionLayer iTransmissionLayer = this.f8376b;
        if (iTransmissionLayer != null) {
            iTransmissionLayer.installPlugin(iPlugin);
        }
    }

    public void onDestroy() {
        ITransmissionLayer iTransmissionLayer = this.f8376b;
        if (iTransmissionLayer != null) {
            iTransmissionLayer.onDestroy();
        }
    }

    public void setTransmissionLayerCallback(ITransmissionLayerCallback iTransmissionLayerCallback) {
        this.f8377c = iTransmissionLayerCallback;
        ITransmissionLayer iTransmissionLayer = this.f8376b;
        if (iTransmissionLayer == null) {
            LogUtils.w(f8375a, "Transmission Layer is not created");
        } else {
            iTransmissionLayer.setTransmissionLayerCallback(iTransmissionLayerCallback);
        }
    }

    public void switchTransmissionLayer(TransmissionLayer transmissionLayer) {
        ITransmissionLayer iTransmissionLayer = this.f8376b;
        if (iTransmissionLayer == null || !iTransmissionLayer.getLayer().equals(transmissionLayer)) {
            ITransmissionLayer iTransmissionLayer2 = this.f8376b;
            if (iTransmissionLayer2 != null) {
                iTransmissionLayer2.onDestroy();
            }
            this.f8376b = createTransmissionLayer(this.f8378d, transmissionLayer);
        }
    }

    public void unregisterReceiver() {
        try {
            this.f8378d.unregisterReceiver(this.f8383i);
        } catch (Exception e2) {
            LogUtils.e(f8375a, "Unregister receiver error: " + e2.toString());
            e2.printStackTrace();
        }
    }

    public final void b() {
        if (this.f8380f == -1) {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.f8378d, new D(this), 2);
        }
    }

    public final void c() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        try {
            this.f8378d.registerReceiver(this.f8383i, intentFilter);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void b(int i2) {
        IActionListener<BluetoothDevice> iActionListener;
        ITransmissionLayerCallback iTransmissionLayerCallback = this.f8377c;
        if (iTransmissionLayerCallback != null) {
            iTransmissionLayerCallback.onBindStateUpdate(this.f8379e, i2);
        }
        if (i2 == 12 && this.f8379e.getAddress().equals(this.f8379e.getAddress()) && (iActionListener = this.f8382h) != null) {
            iActionListener.onSuccess(this.f8379e);
        }
    }

    public final boolean a(BluetoothA2dp bluetoothA2dp, BluetoothDevice bluetoothDevice) {
        if (bluetoothA2dp == null) {
            return false;
        }
        try {
            int i2 = this.f8380f;
            if (i2 == 0) {
                return false;
            }
            String str = "setActiveDevice";
            if (i2 != 1 && (i2 == 2 || i2 == 3)) {
                str = "selectstream";
            }
            return ((Boolean) bluetoothA2dp.getClass().getMethod(str, BluetoothDevice.class).invoke(bluetoothA2dp, bluetoothDevice)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public final BluetoothDevice a(BluetoothA2dp bluetoothA2dp) {
        if (bluetoothA2dp == null) {
            return null;
        }
        try {
            int i2 = this.f8380f;
            if (i2 == 0) {
                return null;
            }
            String str = "getActiveDevice";
            if (i2 != 1) {
                if (i2 == 2) {
                    str = "getActiveStreamDevice";
                } else if (i2 == 3) {
                    str = "semGetActiveStreamDevice";
                }
            }
            BluetoothDevice bluetoothDevice = (BluetoothDevice) bluetoothA2dp.getClass().getMethod(str, null).invoke(bluetoothA2dp, null);
            if (bluetoothDevice == null) {
                return null;
            }
            return bluetoothDevice;
        } catch (Exception unused) {
            return null;
        }
    }

    public final void a(int i2) {
        LogUtils.d(f8375a, "update A2DP connection state: " + i2);
        ITransmissionLayerCallback iTransmissionLayerCallback = this.f8377c;
        if (iTransmissionLayerCallback != null && i2 != this.f8381g) {
            iTransmissionLayerCallback.onA2DPConnectionStateUpdate(this.f8379e, i2);
        }
        this.f8381g = i2;
    }
}
