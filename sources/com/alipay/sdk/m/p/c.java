package com.alipay.sdk.m.p;

import com.alipay.sdk.m.u.n;
import java.io.ByteArrayInputStream;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    public boolean f9604a;

    /* renamed from: b, reason: collision with root package name */
    public String f9605b = n.a(24);

    public c(boolean z2) {
        this.f9604a = z2;
    }

    public static byte[] b(String str, byte[] bArr, String str2) {
        return com.alipay.sdk.m.n.e.b(str, bArr, str2);
    }

    public d a(b bVar, boolean z2, String str) {
        if (bVar == null) {
            return null;
        }
        byte[] bytes = bVar.b().getBytes();
        byte[] bytes2 = bVar.a().getBytes();
        if (z2) {
            try {
                bytes2 = com.alipay.sdk.m.n.b.a(bytes2);
            } catch (Exception unused) {
                z2 = false;
            }
        }
        return new d(z2, this.f9604a ? a(bytes, a(this.f9605b, com.alipay.sdk.m.l.a.f9412e), b(this.f9605b, bytes2, str)) : a(bytes, bytes2));
    }

    public b a(d dVar, String str) {
        ByteArrayInputStream byteArrayInputStream;
        String str2;
        String str3;
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(dVar.a());
            try {
                try {
                    byte[] bArr = new byte[5];
                    byteArrayInputStream.read(bArr);
                    byte[] bArr2 = new byte[a(new String(bArr))];
                    byteArrayInputStream.read(bArr2);
                    str2 = new String(bArr2);
                    try {
                        byte[] bArr3 = new byte[5];
                        byteArrayInputStream.read(bArr3);
                        int iA = a(new String(bArr3));
                        if (iA > 0) {
                            byte[] bArrB = new byte[iA];
                            byteArrayInputStream.read(bArrB);
                            if (this.f9604a) {
                                bArrB = a(this.f9605b, bArrB, str);
                            }
                            if (dVar.b()) {
                                bArrB = com.alipay.sdk.m.n.b.b(bArrB);
                            }
                            str3 = new String(bArrB);
                        } else {
                            str3 = null;
                        }
                        try {
                            byteArrayInputStream.close();
                        } catch (Exception unused) {
                        }
                    } catch (Exception e2) {
                        e = e2;
                        com.alipay.sdk.m.u.e.a(e);
                        if (byteArrayInputStream != null) {
                            try {
                                byteArrayInputStream.close();
                            } catch (Exception unused2) {
                            }
                        }
                        str3 = null;
                        if (str2 == null) {
                        }
                        return new b(str2, str3);
                    }
                } catch (Throwable th) {
                    th = th;
                    byteArrayInputStream2 = byteArrayInputStream;
                    if (byteArrayInputStream2 != null) {
                        try {
                            byteArrayInputStream2.close();
                        } catch (Exception unused3) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                str2 = null;
            }
        } catch (Exception e4) {
            e = e4;
            byteArrayInputStream = null;
            str2 = null;
        } catch (Throwable th2) {
            th = th2;
        }
        if (str2 == null || str3 != null) {
            return new b(str2, str3);
        }
        return null;
    }

    public static byte[] a(String str, String str2) {
        return com.alipay.sdk.m.n.d.a(str, str2);
    }

    public static byte[] a(String str, byte[] bArr, String str2) {
        return com.alipay.sdk.m.n.e.a(str, bArr, str2);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:62|7|64|8|(7:51|9|(1:11)|66|16|56|17)|53|36|37) */
    /* JADX WARN: Removed duplicated region for block: B:49:0x005a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x005f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] a(byte[]... r7) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r7 == 0) goto L63
            int r1 = r7.length
            if (r1 != 0) goto L8
            goto L63
        L8:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L44
            r1.<init>()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L44
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            int r3 = r7.length     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            r4 = 0
        L14:
            if (r4 >= r3) goto L2e
            r5 = r7[r4]     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            int r6 = r5.length     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            java.lang.String r6 = a(r6)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            byte[] r6 = r6.getBytes()     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            r2.write(r6)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            r2.write(r5)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            int r4 = r4 + 1
            goto L14
        L2a:
            r7 = move-exception
            goto L3e
        L2c:
            r7 = move-exception
            goto L4a
        L2e:
            r2.flush()     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            byte[] r0 = r1.toByteArray()     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            r1.close()     // Catch: java.lang.Exception -> L54
            goto L54
        L39:
            r7 = move-exception
            goto L3d
        L3b:
            r7 = move-exception
            goto L40
        L3d:
            r2 = r0
        L3e:
            r0 = r1
            goto L58
        L40:
            r2 = r0
            goto L4a
        L42:
            r7 = move-exception
            goto L46
        L44:
            r7 = move-exception
            goto L48
        L46:
            r2 = r0
            goto L58
        L48:
            r1 = r0
            r2 = r1
        L4a:
            com.alipay.sdk.m.u.e.a(r7)     // Catch: java.lang.Throwable -> L2a
            if (r1 == 0) goto L52
            r1.close()     // Catch: java.lang.Exception -> L52
        L52:
            if (r2 == 0) goto L57
        L54:
            r2.close()     // Catch: java.lang.Exception -> L57
        L57:
            return r0
        L58:
            if (r0 == 0) goto L5d
            r0.close()     // Catch: java.lang.Exception -> L5d
        L5d:
            if (r2 == 0) goto L62
            r2.close()     // Catch: java.lang.Exception -> L62
        L62:
            throw r7
        L63:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.p.c.a(byte[][]):byte[]");
    }

    public static String a(int i2) {
        return String.format(Locale.getDefault(), "%05d", Integer.valueOf(i2));
    }

    public static int a(String str) {
        return Integer.parseInt(str);
    }
}
