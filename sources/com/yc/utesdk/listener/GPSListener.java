package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface GPSListener {
    public static final int EPHEMERIS_REQUIRED = 5;
    public static final int GET_EPHEMERIS = 2;
    public static final int GPS_NETWORK_PARAM = 4;
    public static final int QUERY_EPHEMERIS_STATE = 6;
    public static final int SEND_GPS_COORDINATES = 1;
    public static final int SET_EPHEMERIS_STATUS = 3;

    <T> void onGPSNotify(int i2, T t2);

    void onGPSStatus(boolean z2, int i2);
}
