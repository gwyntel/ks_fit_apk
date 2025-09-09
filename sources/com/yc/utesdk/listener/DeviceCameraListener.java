package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface DeviceCameraListener {
    public static final int APP_CLOSE_CAMERA = 1;
    public static final int APP_OPEN_CAMERA = 2;
    public static final int DEVICE_CLOSE_CAMERA = 3;
    public static final int DEVICE_OPEN_CAMERA = 4;
    public static final int PRESS_TAKE_PICTURE_BUTTON = 5;

    void onDeviceCameraStatus(boolean z2, int i2);
}
