package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface DeviceMusicListener {
    public static final int APP_CONTROL_MUSIC_SWITCH = 9;
    public static final int APP_MUSIC_PLAY_STATUS = 1;
    public static final int APP_MUSIC_PLAY_VOLUME = 3;
    public static final int DEVICE_MUSIC_PLAYER_LAST_SONG = 7;
    public static final int DEVICE_MUSIC_PLAYER_NEXT_SONG = 6;
    public static final int DEVICE_MUSIC_PLAYER_STATUS = 2;
    public static final int DEVICE_MUSIC_PLAY_VOLUME_DOWN = 5;
    public static final int DEVICE_MUSIC_PLAY_VOLUME_UP = 4;
    public static final int SEND_MUSIC_SONG_INFORMATION = 8;

    void onDeviceMusicStatus(boolean z2, int i2);
}
