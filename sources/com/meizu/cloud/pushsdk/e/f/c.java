package com.meizu.cloud.pushsdk.e.f;

import com.meizu.cloud.pushsdk.e.b.e;
import com.meizu.cloud.pushsdk.e.d.k;

/* loaded from: classes4.dex */
public final class c {
    public static <T> com.meizu.cloud.pushsdk.e.b.c<T> a(com.meizu.cloud.pushsdk.e.b.b bVar) {
        int iL = bVar.l();
        return iL != 0 ? iL != 1 ? iL != 2 ? new com.meizu.cloud.pushsdk.e.b.c<>(new com.meizu.cloud.pushsdk.e.c.a()) : d(bVar) : b(bVar) : c(bVar);
    }

    private static <T> com.meizu.cloud.pushsdk.e.b.c<T> b(com.meizu.cloud.pushsdk.e.b.b bVar) {
        try {
            k kVarA = a.a(bVar);
            if (kVarA == null) {
                return new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(new com.meizu.cloud.pushsdk.e.c.a()));
            }
            if (kVarA.b() >= 400) {
                com.meizu.cloud.pushsdk.e.b.c<T> cVar = new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(new com.meizu.cloud.pushsdk.e.c.a(kVarA), bVar, kVarA.b()));
                cVar.a(kVarA);
                return cVar;
            }
            com.meizu.cloud.pushsdk.e.b.c<T> cVar2 = new com.meizu.cloud.pushsdk.e.b.c<>("success");
            cVar2.a(kVarA);
            return cVar2;
        } catch (com.meizu.cloud.pushsdk.e.c.a e2) {
            return new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(new com.meizu.cloud.pushsdk.e.c.a(e2)));
        } catch (Exception e3) {
            return new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(e3));
        }
    }

    private static <T> com.meizu.cloud.pushsdk.e.b.c<T> c(com.meizu.cloud.pushsdk.e.b.b bVar) {
        try {
            try {
                k kVarB = a.b(bVar);
                if (kVarB == null) {
                    com.meizu.cloud.pushsdk.e.b.c<T> cVar = new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(new com.meizu.cloud.pushsdk.e.c.a()));
                    com.meizu.cloud.pushsdk.e.i.a.a(kVarB, bVar);
                    return cVar;
                }
                if (bVar.m() == e.OK_HTTP_RESPONSE) {
                    com.meizu.cloud.pushsdk.e.b.c<T> cVar2 = new com.meizu.cloud.pushsdk.e.b.c<>(kVarB);
                    cVar2.a(kVarB);
                    com.meizu.cloud.pushsdk.e.i.a.a(kVarB, bVar);
                    return cVar2;
                }
                if (kVarB.b() >= 400) {
                    com.meizu.cloud.pushsdk.e.b.c<T> cVar3 = new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(new com.meizu.cloud.pushsdk.e.c.a(kVarB), bVar, kVarB.b()));
                    cVar3.a(kVarB);
                    com.meizu.cloud.pushsdk.e.i.a.a(kVarB, bVar);
                    return cVar3;
                }
                com.meizu.cloud.pushsdk.e.b.c<T> cVarA = bVar.a(kVarB);
                cVarA.a(kVarB);
                com.meizu.cloud.pushsdk.e.i.a.a(kVarB, bVar);
                return cVarA;
            } catch (com.meizu.cloud.pushsdk.e.c.a e2) {
                com.meizu.cloud.pushsdk.e.b.c<T> cVar4 = new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(new com.meizu.cloud.pushsdk.e.c.a(e2)));
                com.meizu.cloud.pushsdk.e.i.a.a(null, bVar);
                return cVar4;
            } catch (Exception e3) {
                com.meizu.cloud.pushsdk.e.b.c<T> cVar5 = new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(e3));
                com.meizu.cloud.pushsdk.e.i.a.a(null, bVar);
                return cVar5;
            }
        } catch (Throwable th) {
            com.meizu.cloud.pushsdk.e.i.a.a(null, bVar);
            throw th;
        }
    }

    private static <T> com.meizu.cloud.pushsdk.e.b.c<T> d(com.meizu.cloud.pushsdk.e.b.b bVar) {
        try {
            try {
                k kVarC = a.c(bVar);
                if (kVarC == null) {
                    com.meizu.cloud.pushsdk.e.b.c<T> cVar = new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(new com.meizu.cloud.pushsdk.e.c.a()));
                    com.meizu.cloud.pushsdk.e.i.a.a(kVarC, bVar);
                    return cVar;
                }
                if (bVar.m() == e.OK_HTTP_RESPONSE) {
                    com.meizu.cloud.pushsdk.e.b.c<T> cVar2 = new com.meizu.cloud.pushsdk.e.b.c<>(kVarC);
                    cVar2.a(kVarC);
                    com.meizu.cloud.pushsdk.e.i.a.a(kVarC, bVar);
                    return cVar2;
                }
                if (kVarC.b() >= 400) {
                    com.meizu.cloud.pushsdk.e.b.c<T> cVar3 = new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(new com.meizu.cloud.pushsdk.e.c.a(kVarC), bVar, kVarC.b()));
                    cVar3.a(kVarC);
                    com.meizu.cloud.pushsdk.e.i.a.a(kVarC, bVar);
                    return cVar3;
                }
                com.meizu.cloud.pushsdk.e.b.c<T> cVarA = bVar.a(kVarC);
                cVarA.a(kVarC);
                com.meizu.cloud.pushsdk.e.i.a.a(kVarC, bVar);
                return cVarA;
            } catch (com.meizu.cloud.pushsdk.e.c.a e2) {
                com.meizu.cloud.pushsdk.e.b.c<T> cVar4 = new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(e2));
                com.meizu.cloud.pushsdk.e.i.a.a(null, bVar);
                return cVar4;
            } catch (Exception e3) {
                com.meizu.cloud.pushsdk.e.b.c<T> cVar5 = new com.meizu.cloud.pushsdk.e.b.c<>(com.meizu.cloud.pushsdk.e.i.b.a(e3));
                com.meizu.cloud.pushsdk.e.i.a.a(null, bVar);
                return cVar5;
            }
        } catch (Throwable th) {
            com.meizu.cloud.pushsdk.e.i.a.a(null, bVar);
            throw th;
        }
    }
}
