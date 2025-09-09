package com.squareup.okhttp.internal.http;

import anet.channel.request.Request;

/* loaded from: classes4.dex */
public final class HttpMethod {
    private HttpMethod() {
    }

    public static boolean invalidatesCache(String str) {
        return str.equals("POST") || str.equals("PATCH") || str.equals(Request.Method.PUT) || str.equals("DELETE");
    }

    public static boolean permitsRequestBody(String str) {
        return requiresRequestBody(str) || str.equals("DELETE");
    }

    public static boolean requiresRequestBody(String str) {
        return str.equals("POST") || str.equals(Request.Method.PUT) || str.equals("PATCH");
    }
}
