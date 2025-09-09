package com.xiaomi.infra.galaxy.fds.android.model;

import com.huawei.hms.framework.common.ContainerUtils;

/* loaded from: classes4.dex */
public class PutObjectResult {
    private String accessKeyId;
    private String bucketName;
    private String cdnServiceBaseUri;
    private long expires;
    private String fdsServiceBaseUri;
    private String objectName;
    private String signature;

    public String getAbsolutePresignedUri() {
        return this.fdsServiceBaseUri + getRelativePresignedUri();
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public String getCdnPresignedUri() {
        return this.cdnServiceBaseUri + getRelativePresignedUri();
    }

    public long getExpires() {
        return this.expires;
    }

    public String getObjectName() {
        return this.objectName;
    }

    public String getRelativePresignedUri() {
        return "/" + this.bucketName + "/" + this.objectName + "?GalaxyAccessKeyId" + ContainerUtils.KEY_VALUE_DELIMITER + this.accessKeyId + "&Expires" + ContainerUtils.KEY_VALUE_DELIMITER + this.expires + "&Signature" + ContainerUtils.KEY_VALUE_DELIMITER + this.signature;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setAccessKeyId(String str) {
        this.accessKeyId = str;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public void setCdnServiceBaseUri(String str) {
        this.cdnServiceBaseUri = str;
    }

    public void setExpires(long j2) {
        this.expires = j2;
    }

    public void setFdsServiceBaseUri(String str) {
        this.fdsServiceBaseUri = str;
    }

    public void setObjectName(String str) {
        this.objectName = str;
    }

    public void setSignature(String str) {
        this.signature = str;
    }
}
