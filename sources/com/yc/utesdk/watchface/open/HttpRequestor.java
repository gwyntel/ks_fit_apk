package com.yc.utesdk.watchface.open;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: classes4.dex */
public class HttpRequestor {
    private static HttpRequestor instance;
    private String charset = "utf-8";
    private Integer connectTimeout = null;
    private Integer socketTimeout = null;
    private String proxyHost = null;
    private Integer proxyPort = null;

    public static HttpRequestor getInstance() {
        if (instance == null) {
            instance = new HttpRequestor();
        }
        return instance;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String doGet(java.lang.String r5) throws java.lang.Exception {
        /*
            r4 = this;
            java.net.URL r0 = new java.net.URL
            r0.<init>(r5)
            java.net.URLConnection r5 = r4.utendo(r0)
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5
            java.lang.String r0 = r4.charset
            java.lang.String r1 = "Accept-Charset"
            r5.setRequestProperty(r1, r0)
            java.lang.String r0 = "Content-Type"
            java.lang.String r1 = "application/x-www-form-urlencoded"
            r5.setRequestProperty(r0, r1)
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            int r1 = r5.getResponseCode()
            r2 = 300(0x12c, float:4.2E-43)
            if (r1 >= r2) goto L6b
            r1 = 0
            java.io.InputStream r5 = r5.getInputStream()     // Catch: java.lang.Throwable -> L57
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L55
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L55
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L53
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L53
        L35:
            java.lang.String r1 = r3.readLine()     // Catch: java.lang.Throwable -> L3f
            if (r1 == 0) goto L41
            r0.append(r1)     // Catch: java.lang.Throwable -> L3f
            goto L35
        L3f:
            r0 = move-exception
            goto L51
        L41:
            r3.close()
            r2.close()
            if (r5 == 0) goto L4c
            r5.close()
        L4c:
            java.lang.String r5 = r0.toString()
            return r5
        L51:
            r1 = r3
            goto L5b
        L53:
            r0 = move-exception
            goto L5b
        L55:
            r0 = move-exception
            goto L5a
        L57:
            r5 = move-exception
            r0 = r5
            r5 = r1
        L5a:
            r2 = r1
        L5b:
            if (r1 == 0) goto L60
            r1.close()
        L60:
            if (r2 == 0) goto L65
            r2.close()
        L65:
            if (r5 == 0) goto L6a
            r5.close()
        L6a:
            throw r0
        L6b:
            java.lang.Exception r0 = new java.lang.Exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "HTTP Request is not success, Response code is "
            r1.append(r2)
            int r5 = r5.getResponseCode()
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.<init>(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.watchface.open.HttpRequestor.doGet(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011c  */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String doPost(java.lang.String r4, java.lang.String r5) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.watchface.open.HttpRequestor.doPost(java.lang.String, java.lang.String):java.lang.String");
    }

    public String getCharset() {
        return this.charset;
    }

    public Integer getConnectTimeout() {
        return this.connectTimeout;
    }

    public String getProxyHost() {
        return this.proxyHost;
    }

    public Integer getProxyPort() {
        return this.proxyPort;
    }

    public Integer getSocketTimeout() {
        return this.socketTimeout;
    }

    public void setCharset(String str) {
        this.charset = str;
    }

    public void setConnectTimeout(Integer num) {
        this.connectTimeout = num;
    }

    public void setProxyHost(String str) {
        this.proxyHost = str;
    }

    public void setProxyPort(Integer num) {
        this.proxyPort = num;
    }

    public void setSocketTimeout(Integer num) {
        this.socketTimeout = num;
    }

    public final URLConnection utendo(URL url) {
        return (this.proxyHost == null || this.proxyPort == null) ? url.openConnection() : url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.proxyHost, this.proxyPort.intValue())));
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0137  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String doPost(java.lang.String r6, java.util.Map r7) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.watchface.open.HttpRequestor.doPost(java.lang.String, java.util.Map):java.lang.String");
    }
}
