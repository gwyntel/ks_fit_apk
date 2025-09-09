package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class MusicPushInfo {
    public static final int MUSIC_PLAY_MODE_ORDER = 0;
    public static final int MUSIC_PLAY_MODE_RANDOM = 1;
    public static final int MUSIC_PLAY_MODE_SINGLE = 2;
    public static final int MUSIC_STATUS_PLAY = 1;
    public static final int MUSIC_STATUS_STOP = 2;
    public static final int STATUS_NO_CHANGE = -1;
    public static final int VOLUME_DOWN = 1;
    public static final int VOLUME_UP = 0;
    public String artist;
    public String songName;
    public int playStatus = -1;
    public int PlayMode = -1;
    public int volumePer = -1;
    public int totalTime = -1;
    public int currentTime = -1;

    public String getArtist() {
        return this.artist;
    }

    public int getCurrentTime() {
        return this.currentTime;
    }

    public int getPlayMode() {
        return this.PlayMode;
    }

    public int getPlayStatus() {
        return this.playStatus;
    }

    public String getSongName() {
        return this.songName;
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    public int getVolumePer() {
        return this.volumePer;
    }

    public void setArtist(String str) {
        this.artist = str;
    }

    public void setCurrentTime(int i2) {
        this.currentTime = i2;
    }

    public void setPlayMode(int i2) {
        this.PlayMode = i2;
    }

    public void setPlayStatus(int i2) {
        this.playStatus = i2;
    }

    public void setSongName(String str) {
        this.songName = str;
    }

    public void setTotalTime(int i2) {
        this.totalTime = i2;
    }

    public void setVolumePer(int i2) {
        this.volumePer = i2;
    }
}
