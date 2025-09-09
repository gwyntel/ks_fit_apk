package com.xiaomi.account.http;

import com.alipay.sdk.m.u.i;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class Response {
    public final String body;
    public final int code;
    public final Map<String, List<String>> headers;
    public final String location;
    public final String setCookie;

    public static class Builder {
        String body;
        int code;
        Map<String, List<String>> headers;
        String location;
        String setCookie;

        public Builder() {
        }

        public Builder body(String str) {
            this.body = str;
            return this;
        }

        public Response build() {
            return new Response(this);
        }

        public Builder code(int i2) {
            this.code = i2;
            return this;
        }

        public Builder headers(Map<String, List<String>> map) {
            this.headers = map;
            return this;
        }

        public Builder location(String str) {
            this.location = str;
            return this;
        }

        public Builder setCookie(String str) {
            this.setCookie = str;
            return this;
        }

        public Builder(Response response) {
            this.code = response.code;
            this.body = response.body;
            this.headers = response.headers;
            this.setCookie = response.setCookie;
            this.location = response.location;
        }
    }

    public Response(Builder builder) {
        this.code = builder.code;
        this.body = builder.body;
        this.headers = builder.headers;
        this.setCookie = builder.setCookie;
        this.location = builder.location;
    }

    public String toString() {
        return "{code:" + this.code + ", body:" + this.body + i.f9804d;
    }
}
