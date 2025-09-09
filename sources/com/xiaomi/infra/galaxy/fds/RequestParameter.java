package com.xiaomi.infra.galaxy.fds;

import com.alibaba.sdk.android.oss.common.RequestParameters;

/* loaded from: classes4.dex */
public enum RequestParameter {
    RESPONSE_CONTENT_TYPE(RequestParameters.RESPONSE_HEADER_CONTENT_TYPE, "Content-Type"),
    RESPONSE_CACHE_CONTROL(RequestParameters.RESPONSE_HEADER_CACHE_CONTROL, "Cache-Control"),
    RESPONSE_EXPIRES(RequestParameters.RESPONSE_HEADER_EXPIRES, "Expires"),
    RESPONSE_CONTENT_ENCODING(RequestParameters.RESPONSE_HEADER_CONTENT_ENCODING, "Content-Encoding"),
    RESPONSE_CONTENT_DISPOSITION(RequestParameters.RESPONSE_HEADER_CONTENT_DISPOSITION, "Content-Disposition");

    private final String header;
    private final String param;

    RequestParameter(String str, String str2) {
        this.param = str;
        this.header = str2;
    }

    public String getHeader() {
        return this.header;
    }

    public String getParam() {
        return this.param;
    }
}
