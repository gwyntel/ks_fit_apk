package com.aliyun.alink.linksdk.tmp.utils;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public class ErrorInfo {
    private int mErrorCode;
    private String mErrorMsg;

    public ErrorInfo(int i2, String str) {
        this.mErrorCode = i2;
        this.mErrorMsg = str;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMsg() {
        return this.mErrorMsg;
    }

    public boolean isSuccess() {
        int i2 = this.mErrorCode;
        if (i2 != 0) {
            return i2 >= 200 && i2 < 300;
        }
        return true;
    }

    public void setErrorCode(int i2) {
        this.mErrorCode = i2;
    }

    public void setErrorMsg(String str) {
        this.mErrorMsg = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mErrorCode);
        sb.append(TextUtils.isEmpty(this.mErrorMsg) ? TmpConstant.GROUP_ROLE_UNKNOWN : this.mErrorMsg);
        return sb.toString();
    }

    public ErrorInfo(AError aError) {
        if (aError != null) {
            this.mErrorCode = aError.getCode();
            this.mErrorMsg = aError.getMsg();
        }
    }
}
