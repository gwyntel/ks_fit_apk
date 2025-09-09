package com.aliyun.private_service;

/* loaded from: classes3.dex */
public class TbDrmDemuxerInfo {
    private long contextAddr;
    private long createAddr;
    private long releaseAddr;

    private void setContextAddr(long j2) {
        this.contextAddr = j2;
    }

    private void setCreateAddr(long j2) {
        this.createAddr = j2;
    }

    private void setReleaseAddr(long j2) {
        this.releaseAddr = j2;
    }

    public long getContextAddr() {
        return this.contextAddr;
    }

    public long getCreateAddr() {
        return this.createAddr;
    }

    public long getReleaseAddr() {
        return this.releaseAddr;
    }
}
