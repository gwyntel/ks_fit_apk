package com.xiaomi.push;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class dh {

    public static class a extends dg {
        public a() {
            super(1);
        }

        @Override // com.xiaomi.push.dg
        public String a(Context context, String str, List<bf> list) {
            if (list == null) {
                return bg.a(context, new URL(str));
            }
            Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
            for (bf bfVar : list) {
                builderBuildUpon.appendQueryParameter(bfVar.a(), bfVar.b());
            }
            return bg.a(context, new URL(builderBuildUpon.toString()));
        }
    }

    public static String a(Context context, String str, List<bf> list) {
        return a(context, str, list, new a(), true);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00ad A[Catch: MalformedURLException -> 0x0026, TRY_ENTER, TryCatch #3 {MalformedURLException -> 0x0026, blocks: (B:4:0x000f, B:6:0x0016, B:8:0x0020, B:13:0x002a, B:15:0x0030, B:16:0x0033, B:17:0x0038, B:19:0x003e, B:21:0x0047, B:23:0x004f, B:50:0x00ad, B:51:0x00bf), top: B:62:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r20, java.lang.String r21, java.util.List<com.xiaomi.push.bf> r22, com.xiaomi.push.dg r23, boolean r24) {
        /*
            r1 = r20
            r0 = r21
            r2 = r22
            r3 = r23
            boolean r4 = com.xiaomi.push.bg.b(r20)
            r5 = 0
            if (r4 == 0) goto Lc9
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch: java.net.MalformedURLException -> L26
            r4.<init>()     // Catch: java.net.MalformedURLException -> L26
            if (r24 == 0) goto L29
            com.xiaomi.push.dd r6 = com.xiaomi.push.dd.a()     // Catch: java.net.MalformedURLException -> L26
            com.xiaomi.push.cz r6 = r6.m270a(r0)     // Catch: java.net.MalformedURLException -> L26
            if (r6 == 0) goto L24
            java.util.ArrayList r4 = r6.a(r0)     // Catch: java.net.MalformedURLException -> L26
        L24:
            r13 = r6
            goto L2a
        L26:
            r0 = move-exception
            goto Lc6
        L29:
            r13 = r5
        L2a:
            boolean r6 = r4.contains(r0)     // Catch: java.net.MalformedURLException -> L26
            if (r6 != 0) goto L33
            r4.add(r0)     // Catch: java.net.MalformedURLException -> L26
        L33:
            java.util.Iterator r4 = r4.iterator()     // Catch: java.net.MalformedURLException -> L26
            r6 = r5
        L38:
            boolean r0 = r4.hasNext()     // Catch: java.net.MalformedURLException -> L26
            if (r0 == 0) goto Lc5
            java.lang.Object r0 = r4.next()     // Catch: java.net.MalformedURLException -> L26
            r14 = r0
            java.lang.String r14 = (java.lang.String) r14     // Catch: java.net.MalformedURLException -> L26
            if (r2 == 0) goto L4e
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: java.net.MalformedURLException -> L26
            r0.<init>(r2)     // Catch: java.net.MalformedURLException -> L26
            r15 = r0
            goto L4f
        L4e:
            r15 = r5
        L4f:
            long r16 = java.lang.System.currentTimeMillis()     // Catch: java.net.MalformedURLException -> L26
            boolean r0 = r3.m280a(r1, r14, r15)     // Catch: java.io.IOException -> La7
            if (r0 != 0) goto L5b
            goto Lc5
        L5b:
            java.lang.String r12 = r3.a(r1, r14, r15)     // Catch: java.io.IOException -> La7
            boolean r0 = android.text.TextUtils.isEmpty(r12)     // Catch: java.io.IOException -> L9e
            if (r0 != 0) goto L7f
            if (r13 == 0) goto L7d
            long r6 = java.lang.System.currentTimeMillis()     // Catch: java.io.IOException -> L78
            long r8 = r6 - r16
            int r0 = a(r3, r14, r15, r12)     // Catch: java.io.IOException -> L78
            long r10 = (long) r0     // Catch: java.io.IOException -> L78
            r6 = r13
            r7 = r14
            r6.a(r7, r8, r10)     // Catch: java.io.IOException -> L78
            goto L7d
        L78:
            r0 = move-exception
            r18 = r0
            r0 = r12
            goto Lab
        L7d:
            r6 = r12
            goto Lc5
        L7f:
            if (r13 == 0) goto La2
            long r6 = java.lang.System.currentTimeMillis()     // Catch: java.io.IOException -> L9e
            long r8 = r6 - r16
            int r0 = a(r3, r14, r15, r12)     // Catch: java.io.IOException -> L9e
            long r10 = (long) r0
            r0 = 0
            r6 = r13
            r7 = r14
            r18 = r12
            r12 = r0
            r6.a(r7, r8, r10, r12)     // Catch: java.io.IOException -> L96
            goto La4
        L96:
            r0 = move-exception
        L97:
            r19 = r18
            r18 = r0
            r0 = r19
            goto Lab
        L9e:
            r0 = move-exception
            r18 = r12
            goto L97
        La2:
            r18 = r12
        La4:
            r6 = r18
            goto L38
        La7:
            r0 = move-exception
            r18 = r0
            r0 = r6
        Lab:
            if (r13 == 0) goto Lbf
            long r6 = java.lang.System.currentTimeMillis()     // Catch: java.net.MalformedURLException -> L26
            long r8 = r6 - r16
            int r6 = a(r3, r14, r15, r0)     // Catch: java.net.MalformedURLException -> L26
            long r10 = (long) r6     // Catch: java.net.MalformedURLException -> L26
            r6 = r13
            r7 = r14
            r12 = r18
            r6.a(r7, r8, r10, r12)     // Catch: java.net.MalformedURLException -> L26
        Lbf:
            r18.printStackTrace()     // Catch: java.net.MalformedURLException -> L26
            r6 = r0
            goto L38
        Lc5:
            return r6
        Lc6:
            r0.printStackTrace()
        Lc9:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.dh.a(android.content.Context, java.lang.String, java.util.List, com.xiaomi.push.dg, boolean):java.lang.String");
    }

    private static int a(dg dgVar, String str, List<bf> list, String str2) {
        if (dgVar.a() == 1) {
            return a(str.length(), a(str2));
        }
        if (dgVar.a() != 2) {
            return -1;
        }
        return a(str.length(), a(list), a(str2));
    }

    static int a(List<bf> list) {
        int length = 0;
        for (bf bfVar : list) {
            if (!TextUtils.isEmpty(bfVar.a())) {
                length += bfVar.a().length();
            }
            if (!TextUtils.isEmpty(bfVar.b())) {
                length += bfVar.b().length();
            }
        }
        return length * 2;
    }

    static int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            return 0;
        }
    }

    static int a(int i2, int i3) {
        return (((i3 + 243) / 1448) * 132) + 1080 + i2 + i3;
    }

    static int a(int i2, int i3, int i4) {
        return (((i3 + 200) / 1448) * 132) + 1011 + i3 + i2 + i4;
    }
}
