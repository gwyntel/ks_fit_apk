package com.aliyun.utils;

import java.io.InputStream;

/* loaded from: classes3.dex */
public abstract class a {
    private static final int CONNECTION_TIMEOUT = 10000;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0061 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.net.HttpURLConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void doHttpGet(java.lang.String r4) throws java.lang.Throwable {
        /*
            r3 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            r1.<init>(r4)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            java.net.URLConnection r4 = r1.openConnection()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            boolean r4 = r4 instanceof java.net.HttpURLConnection     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            if (r4 != 0) goto Lf
            return
        Lf:
            java.net.URLConnection r4 = r1.openConnection()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            java.lang.String r1 = "GET"
            r4.setRequestMethod(r1)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r1 = 10000(0x2710, float:1.4013E-41)
            r4.setConnectTimeout(r1)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r4.setReadTimeout(r1)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r4.connect()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            int r1 = r4.getResponseCode()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 != r2) goto L39
            java.io.InputStream r0 = r4.getInputStream()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r3.handleOKInputStream(r0)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            goto L40
        L35:
            r1 = move-exception
            goto L5f
        L37:
            r1 = move-exception
            goto L4b
        L39:
            java.io.InputStream r0 = r4.getErrorStream()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r3.handleErrorInputStream(r0)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
        L40:
            if (r0 == 0) goto L5b
            r0.close()     // Catch: java.io.IOException -> L5b
            goto L5b
        L46:
            r1 = move-exception
            r4 = r0
            goto L5f
        L49:
            r1 = move-exception
            r4 = r0
        L4b:
            java.lang.String r2 = "HttpClientUtil"
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L35
            com.cicada.player.utils.Logger.w(r2, r1)     // Catch: java.lang.Throwable -> L35
            if (r0 == 0) goto L59
            r0.close()     // Catch: java.io.IOException -> L59
        L59:
            if (r4 == 0) goto L5e
        L5b:
            r4.disconnect()
        L5e:
            return
        L5f:
            if (r0 == 0) goto L64
            r0.close()     // Catch: java.io.IOException -> L64
        L64:
            if (r4 == 0) goto L69
            r4.disconnect()
        L69:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.a.doHttpGet(java.lang.String):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0061 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.net.HttpURLConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void doHttpsGet(java.lang.String r4) throws java.lang.Throwable {
        /*
            r3 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            r1.<init>(r4)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            java.net.URLConnection r4 = r1.openConnection()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            boolean r4 = r4 instanceof javax.net.ssl.HttpsURLConnection     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            if (r4 != 0) goto Lf
            return
        Lf:
            java.net.URLConnection r4 = r1.openConnection()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            javax.net.ssl.HttpsURLConnection r4 = (javax.net.ssl.HttpsURLConnection) r4     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            java.lang.String r1 = "GET"
            r4.setRequestMethod(r1)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r1 = 10000(0x2710, float:1.4013E-41)
            r4.setConnectTimeout(r1)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r4.setReadTimeout(r1)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r4.connect()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            int r1 = r4.getResponseCode()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 != r2) goto L39
            java.io.InputStream r0 = r4.getInputStream()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r3.handleOKInputStream(r0)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            goto L40
        L35:
            r1 = move-exception
            goto L5f
        L37:
            r1 = move-exception
            goto L4b
        L39:
            java.io.InputStream r0 = r4.getErrorStream()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r3.handleErrorInputStream(r0)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
        L40:
            if (r0 == 0) goto L5b
            r0.close()     // Catch: java.io.IOException -> L5b
            goto L5b
        L46:
            r1 = move-exception
            r4 = r0
            goto L5f
        L49:
            r1 = move-exception
            r4 = r0
        L4b:
            java.lang.String r2 = "HttpClientUtil"
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L35
            com.cicada.player.utils.Logger.d(r2, r1)     // Catch: java.lang.Throwable -> L35
            if (r0 == 0) goto L59
            r0.close()     // Catch: java.io.IOException -> L59
        L59:
            if (r4 == 0) goto L5e
        L5b:
            r4.disconnect()
        L5e:
            return
        L5f:
            if (r0 == 0) goto L64
            r0.close()     // Catch: java.io.IOException -> L64
        L64:
            if (r4 == 0) goto L69
            r4.disconnect()
        L69:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.a.doHttpsGet(java.lang.String):void");
    }

    public void doGet(String str) throws Throwable {
        if (str.startsWith("https://")) {
            doHttpsGet(str);
        } else if (str.startsWith("http://")) {
            doHttpGet(str);
        }
    }

    protected abstract void handleErrorInputStream(InputStream inputStream);

    protected abstract void handleOKInputStream(InputStream inputStream);
}
