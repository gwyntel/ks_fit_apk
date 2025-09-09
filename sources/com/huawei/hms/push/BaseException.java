package com.huawei.hms.push;

import com.huawei.hms.aaid.constant.ErrorEnum;

/* loaded from: classes4.dex */
public class BaseException extends Exception {

    /* renamed from: a, reason: collision with root package name */
    private final int f16630a;

    /* renamed from: b, reason: collision with root package name */
    private final ErrorEnum f16631b;

    public BaseException(int i2) {
        ErrorEnum errorEnumFromCode = ErrorEnum.fromCode(i2);
        this.f16631b = errorEnumFromCode;
        this.f16630a = errorEnumFromCode.getExternalCode();
    }

    public int getErrorCode() {
        return this.f16630a;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.f16631b.getMessage();
    }
}
