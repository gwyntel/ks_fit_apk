package com.aliyun.alink.business.devicecenter.utils;

/* loaded from: classes2.dex */
public class FileUtils {
    /* JADX WARN: Removed duplicated region for block: B:36:0x0044 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String ReadFile(java.io.InputStream r5) throws java.lang.Throwable {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2c
            java.lang.String r3 = "UTF-8"
            r2.<init>(r5, r3)     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2c
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2c
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2c
        L12:
            java.lang.String r1 = r5.readLine()     // Catch: java.lang.Throwable -> L1c java.io.IOException -> L1f
            if (r1 == 0) goto L21
            r0.append(r1)     // Catch: java.lang.Throwable -> L1c java.io.IOException -> L1f
            goto L12
        L1c:
            r0 = move-exception
            r1 = r5
            goto L41
        L1f:
            r1 = move-exception
            goto L30
        L21:
            r5.close()     // Catch: java.lang.Throwable -> L1c java.io.IOException -> L1f
            r5.close()     // Catch: java.io.IOException -> L28
            goto L3c
        L28:
            r5 = move-exception
            goto L39
        L2a:
            r5 = move-exception
            goto L42
        L2c:
            r5 = move-exception
            r4 = r1
            r1 = r5
            r5 = r4
        L30:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L1c
            if (r5 == 0) goto L3c
            r5.close()     // Catch: java.io.IOException -> L28
            goto L3c
        L39:
            r5.printStackTrace()
        L3c:
            java.lang.String r5 = r0.toString()
            return r5
        L41:
            r5 = r0
        L42:
            if (r1 == 0) goto L4c
            r1.close()     // Catch: java.io.IOException -> L48
            goto L4c
        L48:
            r0 = move-exception
            r0.printStackTrace()
        L4c:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.business.devicecenter.utils.FileUtils.ReadFile(java.io.InputStream):java.lang.String");
    }
}
