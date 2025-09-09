package com.alibaba.sdk.android.oss.model;

import java.util.Map;

/* loaded from: classes2.dex */
public class OSSResult {
    private Long clientCRC;
    private String requestId;
    private Map<String, String> responseHeader;
    private Long serverCRC;
    private int statusCode;

    public Long getClientCRC() {
        return this.clientCRC;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public Map<String, String> getResponseHeader() {
        return this.responseHeader;
    }

    public Long getServerCRC() {
        return this.serverCRC;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setClientCRC(Long l2) {
        if (l2 == null || l2.longValue() == 0) {
            return;
        }
        this.clientCRC = l2;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public void setResponseHeader(Map<String, String> map) {
        this.responseHeader = map;
    }

    public void setServerCRC(Long l2) {
        if (l2 == null || l2.longValue() == 0) {
            return;
        }
        this.serverCRC = l2;
    }

    public void setStatusCode(int i2) {
        this.statusCode = i2;
    }

    public String toString() {
        return String.format("OSSResult<%s>: \nstatusCode:%d,\nresponseHeader:%s,\nrequestId:%s", super.toString(), Integer.valueOf(this.statusCode), this.responseHeader.toString(), this.requestId);
    }
}
