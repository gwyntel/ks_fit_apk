package com.aliyun.player.source;

/* loaded from: classes3.dex */
public enum MediaFormat {
    mp4("mp4"),
    m3u8("m3u8"),
    flv("flv"),
    mp3("mp3");

    private String mFormat;

    MediaFormat(String str) {
        this.mFormat = str;
    }

    public String getFormat() {
        return this.mFormat;
    }
}
