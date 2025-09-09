package com.huawei.hms.hmsscankit;

/* loaded from: classes4.dex */
public final class WriterException extends Exception {
    public WriterException() {
    }

    public WriterException(String str, String str2) {
        super(str2 + " Format | " + str);
    }

    public WriterException(String str) {
        super(str);
    }

    public WriterException(Throwable th) {
        super(th);
    }
}
