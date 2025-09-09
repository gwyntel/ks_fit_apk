package com.yc.utesdk.watchface.bean.acts;

/* loaded from: classes4.dex */
public class DevicePacketDataInfo {
    private int flag;
    private int length;
    private byte[] packetData;
    private int packetValue;

    public DevicePacketDataInfo(int i2, int i3, byte[] bArr, int i4) {
        this.flag = i2;
        this.length = i3;
        this.packetData = bArr;
        this.packetValue = i4;
    }

    public int getFlag() {
        return this.flag;
    }

    public int getLength() {
        return this.length;
    }

    public byte[] getPacketData() {
        return this.packetData;
    }

    public int getPacketValue() {
        return this.packetValue;
    }

    public void setFlag(int i2) {
        this.flag = i2;
    }

    public void setLength(int i2) {
        this.length = i2;
    }

    public void setPacketData(byte[] bArr) {
        this.packetData = bArr;
    }

    public void setPacketValue(int i2) {
        this.packetValue = i2;
    }
}
