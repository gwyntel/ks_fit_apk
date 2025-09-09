package com.yc.utesdk.ble.open;

/* loaded from: classes4.dex */
public class DevicePlatform {
    public static final int PLATFORM_JX = 1;
    public static final int PLATFORM_RK = 2;
    public static final int PLATFORM_ZK = 3;
    private static DevicePlatform instance;

    public static synchronized DevicePlatform getInstance() {
        try {
            if (instance == null) {
                instance = new DevicePlatform();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public boolean isJXPlatform() {
        return isJXPlatformATS3085L() || isJXPlatformATS3085S();
    }

    public boolean isJXPlatformATS3085L() {
        return DeviceMode.isHasFunction_7(32768);
    }

    public boolean isJXPlatformATS3085S() {
        return DeviceMode.isHasFunction_13(65536);
    }

    public boolean isRKPlatform8773() {
        return DeviceMode.isHasFunction_13(131072);
    }

    public boolean isZKPlatform() {
        return DeviceMode.isHasFunction_13(524288);
    }
}
