package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class GptVoiceInfo {
    private int dataLength;
    private byte[] voiceOpusData;

    public GptVoiceInfo() {
    }

    public int getDataLength() {
        return this.dataLength;
    }

    public byte[] getVoiceOpusData() {
        return this.voiceOpusData;
    }

    public void setDataLength(int i2) {
        this.dataLength = i2;
    }

    public void setVoiceOpusData(byte[] bArr) {
        this.voiceOpusData = bArr;
    }

    public GptVoiceInfo(int i2, byte[] bArr) {
        this.dataLength = i2;
        this.voiceOpusData = bArr;
    }
}
