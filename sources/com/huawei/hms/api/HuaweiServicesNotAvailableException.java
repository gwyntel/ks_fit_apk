package com.huawei.hms.api;

/* loaded from: classes.dex */
public final class HuaweiServicesNotAvailableException extends Exception {
    public final int errorCode;

    public HuaweiServicesNotAvailableException(int i2) {
        this.errorCode = i2;
    }
}
