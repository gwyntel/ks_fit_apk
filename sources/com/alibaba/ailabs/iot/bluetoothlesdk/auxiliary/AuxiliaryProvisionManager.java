package com.alibaba.ailabs.iot.bluetoothlesdk.auxiliary;

import android.content.Context;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.bluetoothlesdk.ControlMessage;
import com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDevice;
import com.alibaba.ailabs.iot.bluetoothlesdk.interfaces.AuxiliaryDeviceStatusListener;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class AuxiliaryProvisionManager {
    private static volatile AuxiliaryProvisionManager instance;
    private ConcurrentHashMap<String, CopyOnWriteArrayList<AuxiliaryDeviceStatusListener>> auxiliaryDeviceStatusListenerMap = new ConcurrentHashMap<>();

    private AuxiliaryProvisionManager() {
    }

    public static AuxiliaryProvisionManager getInstance() {
        if (instance == null) {
            synchronized (AuxiliaryProvisionManager.class) {
                try {
                    if (instance == null) {
                        synchronized (AuxiliaryProvisionManager.class) {
                            instance = new AuxiliaryProvisionManager();
                        }
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public synchronized void notifyAuxiliaryDeviceStatusChange(String str, byte[] bArr) {
        try {
            LogUtils.d("GenieBLEDevice", "notifyAuxiliaryDeviceStatusChange, mac address: " + str);
            CopyOnWriteArrayList<AuxiliaryDeviceStatusListener> copyOnWriteArrayList = this.auxiliaryDeviceStatusListenerMap.get(str);
            if (copyOnWriteArrayList != null) {
                Iterator<AuxiliaryDeviceStatusListener> it = copyOnWriteArrayList.iterator();
                while (it.hasNext()) {
                    it.next().onDeviceStatusChange(bArr);
                }
            } else {
                LogUtils.w("GenieBLEDevice", "empty listeners");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void registerAuxiliaryDeviceStatusListener(String str, AuxiliaryDeviceStatusListener auxiliaryDeviceStatusListener) {
        try {
            LogUtils.d("GenieBLEDevice", "Register status listener: " + str);
            CopyOnWriteArrayList<AuxiliaryDeviceStatusListener> copyOnWriteArrayList = this.auxiliaryDeviceStatusListenerMap.get(str);
            if (copyOnWriteArrayList == null) {
                copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                this.auxiliaryDeviceStatusListenerMap.put(str, copyOnWriteArrayList);
            }
            copyOnWriteArrayList.add(auxiliaryDeviceStatusListener);
        } catch (Throwable th) {
            throw th;
        }
    }

    public void sendMessage(Context context, GenieBLEDevice genieBLEDevice, byte[] bArr, IActionListener<byte[]> iActionListener) {
        genieBLEDevice.sendMessage(context, new ControlMessage(ControlMessage.Type.CONTROL, (byte) 13, bArr).callback(iActionListener));
    }

    public synchronized void unregisterAuxiliaryDeviceStatusListener(String str, AuxiliaryDeviceStatusListener auxiliaryDeviceStatusListener) {
        LogUtils.d("GenieBLEDevice", "Unregister status listener: " + str);
        CopyOnWriteArrayList<AuxiliaryDeviceStatusListener> copyOnWriteArrayList = this.auxiliaryDeviceStatusListenerMap.get(str);
        if (copyOnWriteArrayList != null) {
            copyOnWriteArrayList.remove(auxiliaryDeviceStatusListener);
        }
    }
}
