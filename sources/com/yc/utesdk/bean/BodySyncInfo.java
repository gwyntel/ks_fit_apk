package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class BodySyncInfo {
    private byte[] firstSectionData;
    private byte[] secondSectionData;

    public BodySyncInfo() {
    }

    public byte[] getFirstSectionData() {
        return this.firstSectionData;
    }

    public byte[] getSecondSectionData() {
        return this.secondSectionData;
    }

    public void setFirstSectionData(byte[] bArr) {
        this.firstSectionData = bArr;
    }

    public void setSecondSectionData(byte[] bArr) {
        this.secondSectionData = bArr;
    }

    public BodySyncInfo(byte[] bArr, byte[] bArr2) {
        setFirstSectionData(bArr);
        setSecondSectionData(bArr2);
    }
}
