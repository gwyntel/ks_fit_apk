package com.yc.utesdk.watchface.close;

/* loaded from: classes4.dex */
public class PicDataInfo implements Comparable<PicDataInfo> {
    private byte[] picData;
    private int type;

    public PicDataInfo() {
        this.type = 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(PicDataInfo picDataInfo) {
        return getType() - picDataInfo.getType();
    }

    public byte[] getPicData() {
        return this.picData;
    }

    public int getType() {
        return this.type;
    }

    public void setPicData(byte[] bArr) {
        this.picData = bArr;
    }

    public void setType(int i2) {
        this.type = i2;
    }

    public PicDataInfo(int i2, byte[] bArr) {
        this.type = i2;
        this.picData = bArr;
    }
}
