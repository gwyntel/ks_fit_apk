package com.aliyun.alink.apiclient;

import com.aliyun.alink.apiclient.constants.Constants;
import com.aliyun.alink.apiclient.constants.MethodType;
import com.aliyun.alink.apiclient.constants.Schema;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class CommonRequest {
    private String domain;
    private MethodType method;
    private String path = null;
    public Map<String, Object> queryParams;
    private Schema schema;

    public CommonRequest() {
        this.domain = null;
        this.method = null;
        this.schema = null;
        this.queryParams = null;
        this.method = MethodType.POST;
        this.schema = Schema.HTTPS;
        this.domain = Constants.IOT_DOMAIN_DEFAULT;
        this.queryParams = new HashMap();
    }

    public void addQueryParam(String str, Object obj) {
        this.queryParams.put(str, obj);
    }

    public String getDomain() {
        return this.domain;
    }

    public MethodType getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public Map<String, Object> getQueryParams() {
        return this.queryParams;
    }

    public Schema getSchema() {
        return this.schema;
    }

    public void setDomain(String str) {
        this.domain = str;
    }

    public void setMethod(MethodType methodType) {
        this.method = methodType;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public void setQueryParams(Map<String, Object> map) {
        this.queryParams = map;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}
