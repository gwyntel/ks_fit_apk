package com.yc.utesdk.bean;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class BaseResult {
    private String error_code;
    private String error_message;
    private boolean status;

    public BaseResult() {
    }

    public String getError_code() {
        return this.error_code;
    }

    public String getError_message() {
        return this.error_message;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setError_code(String str) {
        this.error_code = str;
    }

    public void setError_message(String str) {
        this.error_message = str;
    }

    public void setStatus(boolean z2) {
        this.status = z2;
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    public BaseResult(boolean z2) {
        this.status = z2;
    }

    public BaseResult(boolean z2, String str, String str2) {
        this.status = z2;
        this.error_code = str;
        this.error_message = str2;
    }
}
