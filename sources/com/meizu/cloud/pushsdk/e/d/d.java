package com.meizu.cloud.pushsdk.e.d;

import anet.channel.request.Request;

/* loaded from: classes4.dex */
public class d {
    public static boolean a(String str) {
        return b(str) || Request.Method.OPTION.equals(str) || "DELETE".equals(str) || "PROPFIND".equals(str) || "MKCOL".equals(str) || "LOCK".equals(str);
    }

    public static boolean b(String str) {
        return "POST".equals(str) || Request.Method.PUT.equals(str) || "PATCH".equals(str) || "PROPPATCH".equals(str) || "REPORT".equals(str);
    }
}
