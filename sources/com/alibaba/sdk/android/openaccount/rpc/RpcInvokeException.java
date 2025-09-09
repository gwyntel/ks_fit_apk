package com.alibaba.sdk.android.openaccount.rpc;

/* loaded from: classes2.dex */
public class RpcInvokeException extends Exception {
    private int errorCode;

    public RpcInvokeException(int i2, String str) {
        super(str);
        this.errorCode = i2;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public RpcInvokeException(int i2, String str, Throwable th) {
        super(str, th);
        this.errorCode = i2;
    }
}
