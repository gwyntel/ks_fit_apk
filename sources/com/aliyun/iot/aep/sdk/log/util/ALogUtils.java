package com.aliyun.iot.aep.sdk.log.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import okhttp3.OkHttpClient;

/* loaded from: classes3.dex */
public class ALogUtils {
    public static final String HOST = "iot-alog.aliyun.test:8080";
    private static final String ONLINE = "iot-alog.aliyun.test:8080";
    public static final String STSHOST = "iot-alog.aliyun.test:3000";
    private static final String STS_ONLINE = "iot-alog.aliyun.test:3000";
    private static final String STS_TEST = "30.6.52.56:3000";
    private static final String TEST = "30.6.52.56:8080";
    private static final int TIMEOUT_IN_MILLIONS = 5000;
    private static OkHttpClient sHttpClient;

    /* JADX WARN: Removed duplicated region for block: B:38:0x00aa A[Catch: IOException -> 0x009b, TRY_ENTER, TryCatch #6 {IOException -> 0x009b, blocks: (B:30:0x0097, B:33:0x009d, B:38:0x00aa, B:40:0x00af), top: B:56:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00af A[Catch: IOException -> 0x009b, TRY_LEAVE, TryCatch #6 {IOException -> 0x009b, blocks: (B:30:0x0097, B:33:0x009d, B:38:0x00aa, B:40:0x00af), top: B:56:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00be A[Catch: IOException -> 0x00ba, TRY_LEAVE, TryCatch #0 {IOException -> 0x00ba, blocks: (B:44:0x00b6, B:48:0x00be), top: B:52:0x00b6 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String doPost(java.lang.String r4, java.lang.String r5) throws java.lang.Throwable {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            java.net.URLConnection r4 = r2.openConnection()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            java.lang.String r2 = "accept"
        */
        //  java.lang.String r3 = "*/*"
        /*
            r4.setRequestProperty(r2, r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            java.lang.String r2 = "connection"
            java.lang.String r3 = "Keep-Alive"
            r4.setRequestProperty(r2, r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            java.lang.String r2 = "POST"
            r4.setRequestMethod(r2)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            java.lang.String r2 = "Content-Type"
            java.lang.String r3 = "application/x-www-form-urlencoded"
            r4.setRequestProperty(r2, r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            java.lang.String r2 = "charset"
            java.lang.String r3 = "utf-8"
            r4.setRequestProperty(r2, r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r2 = 0
            r4.setUseCaches(r2)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r2 = 1
            r4.setDoOutput(r2)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r4.setDoInput(r2)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r2 = 5000(0x1388, float:7.006E-42)
            r4.setReadTimeout(r2)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r4.setConnectTimeout(r2)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            if (r5 == 0) goto L6c
            java.lang.String r2 = r5.trim()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            boolean r2 = r2.equals(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            if (r2 != 0) goto L6c
            java.io.PrintWriter r2 = new java.io.PrintWriter     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            java.io.OutputStream r3 = r4.getOutputStream()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r2.print(r5)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L62
            r2.flush()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L62
            goto L6d
        L5e:
            r4 = move-exception
            r5 = r1
        L60:
            r1 = r2
            goto Lb4
        L62:
            r4 = move-exception
            r5 = r1
        L64:
            r1 = r2
            goto La5
        L66:
            r4 = move-exception
            r5 = r1
            goto Lb4
        L69:
            r4 = move-exception
            r5 = r1
            goto La5
        L6c:
            r2 = r1
        L6d:
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L62
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L62
            java.io.InputStream r4 = r4.getInputStream()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L62
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L62
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L62
        L7b:
            java.lang.String r4 = r5.readLine()     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L93
            if (r4 == 0) goto L95
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L93
            r1.<init>()     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L93
            r1.append(r0)     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L93
            r1.append(r4)     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L93
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L93
            goto L7b
        L91:
            r4 = move-exception
            goto L60
        L93:
            r4 = move-exception
            goto L64
        L95:
            if (r2 == 0) goto L9d
            r2.close()     // Catch: java.io.IOException -> L9b
            goto L9d
        L9b:
            r4 = move-exception
            goto La1
        L9d:
            r5.close()     // Catch: java.io.IOException -> L9b
            goto Lb2
        La1:
            r4.printStackTrace()
            goto Lb2
        La5:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> Lb3
            if (r1 == 0) goto Lad
            r1.close()     // Catch: java.io.IOException -> L9b
        Lad:
            if (r5 == 0) goto Lb2
            r5.close()     // Catch: java.io.IOException -> L9b
        Lb2:
            return r0
        Lb3:
            r4 = move-exception
        Lb4:
            if (r1 == 0) goto Lbc
            r1.close()     // Catch: java.io.IOException -> Lba
            goto Lbc
        Lba:
            r5 = move-exception
            goto Lc2
        Lbc:
            if (r5 == 0) goto Lc5
            r5.close()     // Catch: java.io.IOException -> Lba
            goto Lc5
        Lc2:
            r5.printStackTrace()
        Lc5:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.iot.aep.sdk.log.util.ALogUtils.doPost(java.lang.String, java.lang.String):java.lang.String");
    }

    public static void doPostAsyn(final String str, final String str2) {
        new Thread() { // from class: com.aliyun.iot.aep.sdk.log.util.ALogUtils.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws Throwable {
                try {
                    ALogUtils.doPost(str, str2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }.start();
    }

    public static OkHttpClient getHttpClient() {
        if (sHttpClient == null) {
            sHttpClient = new OkHttpClient();
        }
        return sHttpClient;
    }

    public static String getPath(Context context, Uri uri) throws IllegalArgumentException {
        if (!"content".equalsIgnoreCase(uri.getScheme())) {
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        }
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("_data");
            if (cursorQuery.moveToFirst()) {
                return cursorQuery.getString(columnIndexOrThrow);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
