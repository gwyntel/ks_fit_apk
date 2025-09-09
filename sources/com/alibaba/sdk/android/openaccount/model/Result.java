package com.alibaba.sdk.android.openaccount.model;

import com.alibaba.sdk.android.openaccount.message.Message;

/* loaded from: classes2.dex */
public class Result<T> extends ResultCode {
    public T data;
    public String type;

    public Result() {
    }

    public static <T> Result<T> result(T t2) {
        return result(100, null, t2);
    }

    @Override // com.alibaba.sdk.android.openaccount.model.ResultCode
    public boolean isSuccess() {
        int i2 = this.code;
        return i2 == 100 || i2 == 1;
    }

    public String toString() {
        return "Result [code=" + this.code + ", message=" + this.message + ", data=" + this.data + "]";
    }

    public Result(Message message) {
        super(message);
    }

    public static <T> Result<T> result(int i2, String str, T t2) {
        return new Result<>(i2, str, t2);
    }

    public Result(int i2, String str, T t2) {
        super(i2, str);
        this.data = t2;
    }

    public static <T> Result<T> result(int i2, String str) {
        return result(i2, str, null);
    }

    public static <T> Result<T> result(Result result) {
        return result(result.code, result.message);
    }

    public static <T> Result<T> result(Message message) {
        return result(message.code, message.message);
    }
}
