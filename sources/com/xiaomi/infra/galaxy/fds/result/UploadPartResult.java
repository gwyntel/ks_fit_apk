package com.xiaomi.infra.galaxy.fds.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes4.dex */
public class UploadPartResult {
    private String etag;
    private int partNumber;
    private long partSize;

    public UploadPartResult() {
    }

    public String getEtag() {
        return this.etag;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public long getPartSize() {
        return this.partSize;
    }

    public void setEtag(String str) {
        this.etag = str;
    }

    public void setPartNumber(int i2) {
        this.partNumber = i2;
    }

    public void setPartSize(long j2) {
        this.partSize = j2;
    }

    public UploadPartResult(int i2, long j2, String str) {
        this.partNumber = i2;
        this.etag = str;
        this.partSize = j2;
    }
}
