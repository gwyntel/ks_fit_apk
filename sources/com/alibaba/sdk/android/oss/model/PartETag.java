package com.alibaba.sdk.android.oss.model;

/* loaded from: classes2.dex */
public class PartETag {
    private long crc64;
    private String eTag;
    private int partNumber;
    private long partSize;

    public PartETag(int i2, String str) {
        setPartNumber(i2);
        setETag(str);
    }

    public long getCRC64() {
        return this.crc64;
    }

    public String getETag() {
        return this.eTag;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public long getPartSize() {
        return this.partSize;
    }

    public void setCRC64(long j2) {
        this.crc64 = j2;
    }

    public void setETag(String str) {
        this.eTag = str;
    }

    public void setPartNumber(int i2) {
        this.partNumber = i2;
    }

    public void setPartSize(long j2) {
        this.partSize = j2;
    }
}
