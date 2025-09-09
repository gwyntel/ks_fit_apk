package com.yc.utesdk.watchface.close;

/* loaded from: classes4.dex */
public class WatchConfigInfo {
    private byte[] snNo = new byte[4];
    private byte[] fileSize = new byte[4];
    private byte[] fileCrc = new byte[4];
    private byte[] pixelWidth = new byte[2];
    private byte[] pixelHeight = new byte[2];
    private byte[] screenType = new byte[1];
    private byte[] hasBg = new byte[1];
    private byte[] isWatchVaild = new byte[1];
    private byte[] reserved = new byte[5];

    public byte[] getFileCrc() {
        return this.fileCrc;
    }

    public byte[] getFileSize() {
        return this.fileSize;
    }

    public byte[] getHasBg() {
        return this.hasBg;
    }

    public byte[] getIsWatchVaild() {
        return this.isWatchVaild;
    }

    public byte[] getPixelHeight() {
        return this.pixelHeight;
    }

    public byte[] getPixelWidth() {
        return this.pixelWidth;
    }

    public byte[] getReserved() {
        return this.reserved;
    }

    public byte[] getScreenType() {
        return this.screenType;
    }

    public byte[] getSnNo() {
        return this.snNo;
    }

    public void setFileCrc(byte[] bArr) {
        this.fileCrc = bArr;
    }

    public void setFileSize(byte[] bArr) {
        this.fileSize = bArr;
    }

    public void setHasBg(byte[] bArr) {
        this.hasBg = bArr;
    }

    public void setIsWatchVaild(byte[] bArr) {
        this.isWatchVaild = bArr;
    }

    public void setPixelHeight(byte[] bArr) {
        this.pixelHeight = bArr;
    }

    public void setPixelWidth(byte[] bArr) {
        this.pixelWidth = bArr;
    }

    public void setReserved(byte[] bArr) {
        this.reserved = bArr;
    }

    public void setScreenType(byte[] bArr) {
        this.screenType = bArr;
    }

    public void setSnNo(byte[] bArr) {
        this.snNo = bArr;
    }
}
