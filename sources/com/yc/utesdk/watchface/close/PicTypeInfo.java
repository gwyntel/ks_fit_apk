package com.yc.utesdk.watchface.close;

/* loaded from: classes4.dex */
public class PicTypeInfo implements Comparable<PicTypeInfo> {
    private byte[] picType;
    private int type;

    public PicTypeInfo() {
        this.type = 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(PicTypeInfo picTypeInfo) {
        return getType() - picTypeInfo.getType();
    }

    public byte[] getPicType() {
        return this.picType;
    }

    public int getType() {
        return this.type;
    }

    public void setPicType(byte[] bArr) {
        this.picType = bArr;
    }

    public void setType(int i2) {
        this.type = i2;
    }

    public PicTypeInfo(int i2, byte[] bArr) {
        this.type = i2;
        this.picType = bArr;
    }
}
