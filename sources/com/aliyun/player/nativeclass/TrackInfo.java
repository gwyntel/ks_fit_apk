package com.aliyun.player.nativeclass;

/* loaded from: classes3.dex */
public class TrackInfo {
    public static final int AUTO_SELECT_INDEX = -1;
    public int audioChannels;
    public String audioLang;
    public int audioSampleFormat;
    public int audioSampleRate;
    public String codecName;
    public String description;
    public int index;
    public String subtitleLang;
    public int videoBitrate;
    public int videoHeight;
    public int videoWidth;
    public String vodDefinition;
    public long vodFileSize;
    public String vodFormat;
    public String vodPlayUrl;
    public String vodWaterMarkPlayUrl;
    public Type mType = Type.TYPE_VOD;
    public VideoHDRType videoHDRType = VideoHDRType.VideoHDRType_SDR;

    public enum Type {
        TYPE_VIDEO,
        TYPE_AUDIO,
        TYPE_SUBTITLE,
        TYPE_VOD
    }

    public enum VideoHDRType {
        VideoHDRType_SDR,
        VideoHDRType_HDR10
    }

    private int nGetType() {
        return this.mType.ordinal();
    }

    private void setType(int i2) {
        Type type = Type.TYPE_VIDEO;
        if (i2 != type.ordinal()) {
            type = Type.TYPE_AUDIO;
            if (i2 != type.ordinal()) {
                type = Type.TYPE_SUBTITLE;
                if (i2 != type.ordinal()) {
                    type = Type.TYPE_VOD;
                    if (i2 != type.ordinal()) {
                        return;
                    }
                }
            }
        }
        this.mType = type;
    }

    public int getAudioChannels() {
        return this.audioChannels;
    }

    public String getAudioLang() {
        return this.audioLang;
    }

    public int getAudioSampleFormat() {
        return this.audioSampleFormat;
    }

    public int getAudioSampleRate() {
        return this.audioSampleRate;
    }

    public String getCodecName() {
        return this.codecName;
    }

    public String getDescription() {
        return this.description;
    }

    public int getIndex() {
        return this.index;
    }

    public String getSubtitleLang() {
        return this.subtitleLang;
    }

    public Type getType() {
        return this.mType;
    }

    public int getVideoBitrate() {
        return this.videoBitrate;
    }

    public int getVideoHDRType() {
        return this.videoHDRType.ordinal();
    }

    public int getVideoHeight() {
        return this.videoHeight;
    }

    public int getVideoWidth() {
        return this.videoWidth;
    }

    public String getVodDefinition() {
        return this.vodDefinition;
    }

    public long getVodFileSize() {
        return this.vodFileSize;
    }

    public String getVodFormat() {
        return this.vodFormat;
    }

    public String getVodPlayUrl() {
        return this.vodPlayUrl;
    }

    public String getVodWaterMarkPlayUrl() {
        return this.vodWaterMarkPlayUrl;
    }

    public void setVideoHDRType(int i2) {
        VideoHDRType videoHDRType = VideoHDRType.VideoHDRType_SDR;
        if (i2 != videoHDRType.ordinal()) {
            videoHDRType = VideoHDRType.VideoHDRType_HDR10;
            if (i2 != videoHDRType.ordinal()) {
                return;
            }
        }
        this.videoHDRType = videoHDRType;
    }
}
