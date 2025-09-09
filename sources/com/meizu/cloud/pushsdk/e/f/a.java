package com.meizu.cloud.pushsdk.e.f;

import com.meizu.cloud.pushsdk.e.d.e;
import com.meizu.cloud.pushsdk.e.d.i;
import com.meizu.cloud.pushsdk.e.d.k;
import java.io.File;
import java.io.IOException;

/* loaded from: classes4.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private static String f19473a;

    public static k a(com.meizu.cloud.pushsdk.e.b.b bVar) throws com.meizu.cloud.pushsdk.e.c.a {
        try {
            i.b bVarA = new i.b().a(bVar.o());
            a(bVarA, bVar);
            i iVarA = bVarA.b().a();
            bVar.a(new e());
            k kVarA = bVar.e().a(iVarA);
            com.meizu.cloud.pushsdk.e.i.b.a(kVarA, bVar.f(), bVar.g());
            return kVarA;
        } catch (IOException e2) {
            try {
                File file = new File(bVar.f() + File.separator + bVar.g());
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            throw new com.meizu.cloud.pushsdk.e.c.a(e2);
        }
    }

    public static k b(com.meizu.cloud.pushsdk.e.b.b bVar) throws com.meizu.cloud.pushsdk.e.c.a {
        try {
            i.b bVarA = new i.b().a(bVar.o());
            a(bVarA, bVar);
            int i2 = bVar.i();
            if (i2 == 0) {
                bVarA = bVarA.b();
            } else if (i2 == 1) {
                bVarA = bVarA.c(bVar.k());
            } else if (i2 == 2) {
                bVarA = bVarA.d(bVar.k());
            } else if (i2 == 3) {
                bVarA = bVarA.a(bVar.k());
            } else if (i2 == 4) {
                bVarA = bVarA.c();
            } else if (i2 == 5) {
                bVarA = bVarA.b(bVar.k());
            }
            i iVarA = bVarA.a();
            bVar.a(new e());
            return bVar.e().a(iVarA);
        } catch (IOException e2) {
            throw new com.meizu.cloud.pushsdk.e.c.a(e2);
        }
    }

    public static k c(com.meizu.cloud.pushsdk.e.b.b bVar) throws com.meizu.cloud.pushsdk.e.c.a {
        try {
            i.b bVarA = new i.b().a(bVar.o());
            a(bVarA, bVar);
            i iVarA = bVarA.c(new b(bVar.j(), bVar.n())).a();
            bVar.a(new e());
            return bVar.e().a(iVarA);
        } catch (IOException e2) {
            throw new com.meizu.cloud.pushsdk.e.c.a(e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(com.meizu.cloud.pushsdk.e.d.i.b r3, com.meizu.cloud.pushsdk.e.b.b r4) {
        /*
            java.lang.String r0 = r4.p()
            java.lang.String r1 = "User-Agent"
            if (r0 == 0) goto L10
            java.lang.String r0 = r4.p()
        Lc:
            r3.a(r1, r0)
            goto L1a
        L10:
            java.lang.String r0 = com.meizu.cloud.pushsdk.e.f.a.f19473a
            if (r0 == 0) goto L1a
            r4.a(r0)
            java.lang.String r0 = com.meizu.cloud.pushsdk.e.f.a.f19473a
            goto Lc
        L1a:
            com.meizu.cloud.pushsdk.e.d.c r0 = r4.h()
            if (r0 == 0) goto L3a
            r3.a(r0)
            java.lang.String r2 = r4.p()
            if (r2 == 0) goto L3a
            java.util.Set r0 = r0.a()
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L3a
            java.lang.String r4 = r4.p()
            r3.a(r1, r4)
        L3a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.e.f.a.a(com.meizu.cloud.pushsdk.e.d.i$b, com.meizu.cloud.pushsdk.e.b.b):void");
    }
}
