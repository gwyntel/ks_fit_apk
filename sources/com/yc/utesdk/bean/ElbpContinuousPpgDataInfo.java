package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class ElbpContinuousPpgDataInfo implements Comparable<ElbpContinuousPpgDataInfo> {
    private int curDataLength;
    private byte[] ppgData;
    private int serialNum;

    public ElbpContinuousPpgDataInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(ElbpContinuousPpgDataInfo elbpContinuousPpgDataInfo) {
        return getSerialNum() - elbpContinuousPpgDataInfo.getSerialNum();
    }

    public int getCurDataLength() {
        return this.curDataLength;
    }

    public byte[] getPpgData() {
        return this.ppgData;
    }

    public int getSerialNum() {
        return this.serialNum;
    }

    public void setCurDataLength(int i2) {
        this.curDataLength = i2;
    }

    public void setPpgData(byte[] bArr) {
        this.ppgData = bArr;
    }

    public void setSerialNum(int i2) {
        this.serialNum = i2;
    }

    public ElbpContinuousPpgDataInfo(int i2, int i3, byte[] bArr) {
        this.serialNum = i2;
        this.curDataLength = i3;
        this.ppgData = bArr;
    }
}
