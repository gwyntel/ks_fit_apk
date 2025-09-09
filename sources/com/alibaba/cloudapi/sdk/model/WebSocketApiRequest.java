package com.alibaba.cloudapi.sdk.model;

import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class WebSocketApiRequest {
    String body;
    Map<String, List<String>> headers;
    String host;
    int isBase64 = 0;
    String method;
    String path;
    Map<String, String> querys;

    public String getBody() {
        return this.body;
    }

    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    public String getHost() {
        return this.host;
    }

    public int getIsBase64() {
        return this.isBase64;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public Map<String, String> getQuerys() {
        return this.querys;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public void setHeaders(Map<String, List<String>> map) {
        this.headers = map;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setIsBase64(int i2) {
        this.isBase64 = i2;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public void setQuerys(Map<String, String> map) {
        this.querys = map;
    }
}
