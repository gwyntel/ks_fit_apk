package com.alibaba.sdk.android.openaccount.model;

import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;

/* loaded from: classes2.dex */
public class ResultCode {
    public int code;
    public String message;

    public ResultCode() {
    }

    public static ResultCode create(Message message) {
        return new ResultCode(message.code, message.message);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.code == ((ResultCode) obj).code;
    }

    public int hashCode() {
        return 31 + this.code;
    }

    public boolean isSuccess() {
        return this.code == 100;
    }

    public ResultCode(Message message) {
        this(message.code, message.message);
    }

    public static ResultCode create(int i2, Object... objArr) {
        return new ResultCode(i2, MessageUtils.getMessageContent(i2, objArr));
    }

    public ResultCode(int i2, String str) {
        this.code = i2;
        this.message = str;
    }
}
