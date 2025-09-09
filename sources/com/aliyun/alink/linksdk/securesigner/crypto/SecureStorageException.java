package com.aliyun.alink.linksdk.securesigner.crypto;

/* loaded from: classes2.dex */
public class SecureStorageException extends Exception {
    private String detailedInfo;

    public SecureStorageException() {
    }

    public String getDetailedInfo() {
        return this.detailedInfo;
    }

    public void setDetailedInfo(String str) {
        this.detailedInfo = str;
    }

    public SecureStorageException(String str) {
        super(str);
    }

    public SecureStorageException(String str, Throwable th) {
        super(str, th);
    }

    public SecureStorageException(Throwable th) {
        super(th);
    }
}
