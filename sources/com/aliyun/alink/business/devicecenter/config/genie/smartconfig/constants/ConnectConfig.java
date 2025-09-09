package com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants;

/* loaded from: classes2.dex */
public class ConnectConfig {
    public static final int BLE = 4;
    public static final int SOUND = 2;
    public static final int WIFI = 1;
    public static final int WIFI_AND_SOUND_AND_BLE = 7;

    /* renamed from: a, reason: collision with root package name */
    public static ConnectConfig f10395a;

    /* renamed from: b, reason: collision with root package name */
    public int f10396b = 7;

    public static ConnectConfig getInstance() {
        if (f10395a == null) {
            synchronized (ConnectConfig.class) {
                try {
                    if (f10395a == null) {
                        f10395a = new ConnectConfig();
                    }
                } finally {
                }
            }
        }
        return f10395a;
    }

    public int getModel() {
        return this.f10396b;
    }

    public void setModel(int i2) {
        this.f10396b = i2;
    }
}
