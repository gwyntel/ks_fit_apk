package com.yc.utesdk.watchface.close;

/* loaded from: classes4.dex */
public class PicTypeConfigInfo implements Comparable<PicTypeConfigInfo> {
    private byte[] type = new byte[2];
    private byte[] picWidth = new byte[2];
    private byte[] picStartAddress = new byte[4];
    private byte[] picHeight = new byte[2];

    /* renamed from: x, reason: collision with root package name */
    private byte[] f24959x = new byte[2];

    /* renamed from: y, reason: collision with root package name */
    private byte[] f24960y = new byte[2];
    private byte[] animationTime = new byte[2];
    private byte[] animaitonCnt = new byte[1];
    private byte[] reserved = new byte[7];

    @Override // java.lang.Comparable
    public int compareTo(PicTypeConfigInfo picTypeConfigInfo) {
        return Rgb.getInstance().bytes2HLExchangeInt(getType()) - Rgb.getInstance().bytes2HLExchangeInt(picTypeConfigInfo.getType());
    }

    public byte[] getAnimaitonCnt() {
        return this.animaitonCnt;
    }

    public byte[] getAnimationTime() {
        return this.animationTime;
    }

    public byte[] getPicHeight() {
        return this.picHeight;
    }

    public byte[] getPicStartAddress() {
        return this.picStartAddress;
    }

    public byte[] getPicWidth() {
        return this.picWidth;
    }

    public byte[] getReserved() {
        return this.reserved;
    }

    public byte[] getType() {
        return this.type;
    }

    public byte[] getX() {
        return this.f24959x;
    }

    public byte[] getY() {
        return this.f24960y;
    }

    public void setAnimaitonCnt(byte[] bArr) {
        this.animaitonCnt = bArr;
    }

    public void setAnimationTime(byte[] bArr) {
        this.animationTime = bArr;
    }

    public void setPicHeight(byte[] bArr) {
        this.picHeight = bArr;
    }

    public void setPicStartAddress(byte[] bArr) {
        this.picStartAddress = bArr;
    }

    public void setPicWidth(byte[] bArr) {
        this.picWidth = bArr;
    }

    public void setReserved(byte[] bArr) {
        this.reserved = bArr;
    }

    public void setType(byte[] bArr) {
        this.type = bArr;
    }

    public void setX(byte[] bArr) {
        this.f24959x = bArr;
    }

    public void setY(byte[] bArr) {
        this.f24960y = bArr;
    }
}
