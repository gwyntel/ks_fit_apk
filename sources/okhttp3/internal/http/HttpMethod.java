package okhttp3.internal.http;

import anet.channel.request.Request;

/* loaded from: classes5.dex */
public final class HttpMethod {
    private HttpMethod() {
    }

    public static boolean invalidatesCache(String str) {
        return str.equals("POST") || str.equals("PATCH") || str.equals(Request.Method.PUT) || str.equals("DELETE") || str.equals("MOVE");
    }

    public static boolean permitsRequestBody(String str) {
        return (str.equals("GET") || str.equals(Request.Method.HEAD)) ? false : true;
    }

    public static boolean redirectsToGet(String str) {
        return !str.equals("PROPFIND");
    }

    public static boolean redirectsWithBody(String str) {
        return str.equals("PROPFIND");
    }

    public static boolean requiresRequestBody(String str) {
        return str.equals("POST") || str.equals(Request.Method.PUT) || str.equals("PATCH") || str.equals("PROPPATCH") || str.equals("REPORT");
    }
}
