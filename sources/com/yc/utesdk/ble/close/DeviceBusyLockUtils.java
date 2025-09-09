package com.yc.utesdk.ble.close;

import com.yc.utesdk.log.LogUtils;

/* loaded from: classes4.dex */
public class DeviceBusyLockUtils {
    public static final long HONEY_CMD_TIMEOUT = 3000;
    private static DeviceBusyLockUtils instance;
    private final Object deviceBusyLock = new Object();
    private boolean deviceBusy = false;

    public static synchronized DeviceBusyLockUtils getInstance() {
        try {
            if (instance == null) {
                instance = new DeviceBusyLockUtils();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public boolean getDeviceBusy() {
        return this.deviceBusy;
    }

    public Object getDeviceBusyLock() {
        return this.deviceBusyLock;
    }

    public boolean isDeviceBusyLockTime(long j2) throws InterruptedException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
            if (!getDeviceBusy()) {
                LogUtils.i("设备不忙");
                return false;
            }
            LogUtils.i("设备忙，等待");
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }

    public void setDeviceBusy(boolean z2) {
        this.deviceBusy = z2;
    }
}
