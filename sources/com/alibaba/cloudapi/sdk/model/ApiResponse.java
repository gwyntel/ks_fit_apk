package com.alibaba.cloudapi.sdk.model;

import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.fastjson.JSONObject;

/* loaded from: classes2.dex */
public class ApiResponse extends ApiHttpMessage {
    int code;
    String contentType;
    Exception ex;
    String message;

    public ApiResponse(int i2) {
        this.code = i2;
    }

    public int getCode() {
        return this.code;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Exception getEx() {
        return this.ex;
    }

    public String getMessage() {
        return this.message;
    }

    @Override // com.alibaba.cloudapi.sdk.model.ApiHttpMessage
    public void parse(JSONObject jSONObject) {
        super.parse(jSONObject);
        this.code = Integer.parseInt(jSONObject.get("status").toString());
        this.contentType = getFirstHeaderValue("content-type");
        if (getFirstHeaderValue(SdkConstant.CLOUDAPI_X_CA_ERROR_MESSAGE) != null) {
            this.message = getFirstHeaderValue(SdkConstant.CLOUDAPI_X_CA_ERROR_MESSAGE);
        }
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setContentType(String str) {
        this.contentType = str;
    }

    public void setEx(Exception exc) {
        this.ex = exc;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public ApiResponse(int i2, String str, Exception exc) {
        this.code = i2;
        this.message = str;
        this.ex = exc;
    }

    public ApiResponse(JSONObject jSONObject) {
        parse(jSONObject);
    }
}
