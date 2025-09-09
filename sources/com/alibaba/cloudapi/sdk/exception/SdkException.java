package com.alibaba.cloudapi.sdk.exception;

/* loaded from: classes2.dex */
public class SdkException extends RuntimeException {
    public SdkException(String str) {
        super(str);
    }

    public SdkException(String str, Throwable th) {
        super(str, th);
    }

    public SdkException(Throwable th) {
        super(th);
    }
}
