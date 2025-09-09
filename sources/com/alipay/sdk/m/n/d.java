package com.alipay.sdk.m.n;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9568a = "RSA";

    /* JADX WARN: Removed duplicated region for block: B:43:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] a(java.lang.String r5, java.lang.String r6) throws java.lang.Throwable {
        /*
            r0 = 0
            java.lang.String r1 = "RSA"
            java.security.PublicKey r6 = b(r1, r6)     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            java.lang.String r1 = "RSA/ECB/PKCS1Padding"
            javax.crypto.Cipher r1 = javax.crypto.Cipher.getInstance(r1)     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            r2 = 1
            r1.init(r2, r6)     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            java.lang.String r6 = "UTF-8"
            byte[] r5 = r5.getBytes(r6)     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            int r6 = r1.getBlockSize()     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            r2.<init>()     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L46
            r3 = 0
        L21:
            int r4 = r5.length     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2e
            if (r3 >= r4) goto L3a
            int r4 = r5.length     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2e
            int r4 = r4 - r3
            if (r4 >= r6) goto L30
            int r4 = r5.length     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2e
            int r4 = r4 - r3
            goto L31
        L2b:
            r5 = move-exception
            r0 = r2
            goto L55
        L2e:
            r5 = move-exception
            goto L48
        L30:
            r4 = r6
        L31:
            byte[] r4 = r1.doFinal(r5, r3, r4)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2e
            r2.write(r4)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2e
            int r3 = r3 + r6
            goto L21
        L3a:
            byte[] r0 = r2.toByteArray()     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2e
            r2.close()     // Catch: java.io.IOException -> L42
            goto L54
        L42:
            r5 = move-exception
            goto L51
        L44:
            r5 = move-exception
            goto L55
        L46:
            r5 = move-exception
            r2 = r0
        L48:
            com.alipay.sdk.m.u.e.a(r5)     // Catch: java.lang.Throwable -> L2b
            if (r2 == 0) goto L54
            r2.close()     // Catch: java.io.IOException -> L42
            goto L54
        L51:
            com.alipay.sdk.m.u.e.a(r5)
        L54:
            return r0
        L55:
            if (r0 == 0) goto L5f
            r0.close()     // Catch: java.io.IOException -> L5b
            goto L5f
        L5b:
            r6 = move-exception
            com.alipay.sdk.m.u.e.a(r6)
        L5f:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.n.d.a(java.lang.String, java.lang.String):byte[]");
    }

    public static PublicKey b(String str, String str2) throws Exception {
        return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(a.a(str2)));
    }
}
