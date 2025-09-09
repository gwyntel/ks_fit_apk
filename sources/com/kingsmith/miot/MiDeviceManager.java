package com.kingsmith.miot;

import android.util.Log;
import com.miot.api.CompletionHandler;
import com.miot.api.DeviceManager;
import com.miot.api.MiotManager;
import com.miot.common.abstractdevice.AbstractDevice;
import com.miot.common.device.ConnectionType;
import com.miot.common.device.Device;
import com.miot.common.device.DiscoveryType;
import com.miot.common.exception.MiotException;
import com.miot.common.utils.Logger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class MiDeviceManager {
    private static final String TAG = "MiDeviceManager";
    private static MiDeviceManager sInstance;
    private Map<String, AbstractDevice> mWanDevices = new Hashtable();
    private Map<String, AbstractDevice> mWifiDevices = new Hashtable();
    private DeviceManager.DeviceHandler mDeviceHandler = new DeviceManager.DeviceHandler() { // from class: com.kingsmith.miot.MiDeviceManager.1
        public void onFailed(int i2, String str) {
            Logger.e(MiDeviceManager.TAG, "getRemoteDeviceList onFailed: " + i2 + str);
        }

        public void onSucceed(List<AbstractDevice> list) {
            MiDeviceManager.this.foundDevices(list);
        }
    };

    /* renamed from: com.kingsmith.miot.MiDeviceManager$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f18573a;

        static {
            int[] iArr = new int[ConnectionType.values().length];
            f18573a = iArr;
            try {
                iArr[ConnectionType.MIOT_WAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f18573a[ConnectionType.MIOT_WIFI.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private MiDeviceManager() {
    }

    private void bindDevice(AbstractDevice abstractDevice) {
        try {
            MiotManager.getDeviceManager().takeOwnership(abstractDevice, new CompletionHandler() { // from class: com.kingsmith.miot.MiDeviceManager.2
                public void onFailed(int i2, String str) {
                }

                public void onSucceed() {
                }
            });
        } catch (MiotException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void foundDevices(List<AbstractDevice> list) {
        Log.d(TAG, "foundDevices   size" + list.size());
        for (AbstractDevice abstractDevice : list) {
            ConnectionType connectionType = abstractDevice.getDevice().getConnectionType();
            Log.d(TAG, "found device: " + list.size() + " " + abstractDevice.getName() + " " + abstractDevice.getDeviceId() + " " + connectionType);
            int i2 = AnonymousClass3.f18573a[connectionType.ordinal()];
            if (i2 == 1) {
                if (abstractDevice.getOwnership() == Device.Ownership.NOONES) {
                    bindDevice(abstractDevice);
                }
                this.mWanDevices.put(abstractDevice.getDeviceId(), abstractDevice);
            } else if (i2 == 2 && !this.mWifiDevices.containsKey(abstractDevice.getDeviceId())) {
                this.mWifiDevices.put(abstractDevice.getAddress(), abstractDevice);
            }
        }
    }

    public static synchronized MiDeviceManager getInstance() {
        try {
            if (sInstance == null) {
                sInstance = new MiDeviceManager();
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public void clearDevices() {
        this.mWanDevices.clear();
        this.mWifiDevices.clear();
    }

    public void clearWifiDevices() {
        this.mWifiDevices.clear();
    }

    public AbstractDevice getWanDevice(String str) {
        return this.mWanDevices.get(str);
    }

    public List<AbstractDevice> getWanDevices() {
        return new ArrayList(this.mWanDevices.values());
    }

    public AbstractDevice getWifiDevice(String str) {
        return this.mWifiDevices.get(str);
    }

    public List<AbstractDevice> getWifiDevices() {
        return new ArrayList(this.mWifiDevices.values());
    }

    public synchronized void putWanDevice(String str, AbstractDevice abstractDevice) {
        this.mWanDevices.put(str, abstractDevice);
    }

    public void queryWanDeviceList() {
        if (MiotManager.getDeviceManager() == null) {
            return;
        }
        try {
            this.mWanDevices.clear();
            MiotManager.getDeviceManager().getRemoteDeviceList(this.mDeviceHandler);
        } catch (MiotException e2) {
            e2.printStackTrace();
        }
    }

    public synchronized void removeWanDevice(String str) {
        this.mWanDevices.remove(str);
    }

    public void startScan() {
        if (MiotManager.getDeviceManager() == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(DiscoveryType.MIOT_WIFI);
        try {
            MiotManager.getDeviceManager().startScan(arrayList, this.mDeviceHandler);
        } catch (MiotException e2) {
            e2.printStackTrace();
        }
    }

    public void stopScan() {
        if (MiotManager.getDeviceManager() == null) {
            return;
        }
        try {
            MiotManager.getDeviceManager().stopScan();
        } catch (MiotException e2) {
            e2.printStackTrace();
        }
    }
}
