package com.huawei.hms.scankit.p;

import android.util.Log;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import com.huawei.hms.scankit.util.LoadOpencvJNIUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class n1 {

    /* renamed from: a, reason: collision with root package name */
    private p4 f17563a;

    /* renamed from: b, reason: collision with root package name */
    private p f17564b;

    /* renamed from: c, reason: collision with root package name */
    private p f17565c;

    /* renamed from: d, reason: collision with root package name */
    private p f17566d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f17567e;

    /* renamed from: g, reason: collision with root package name */
    private float f17569g;

    /* renamed from: f, reason: collision with root package name */
    private float f17568f = 0.0f;

    /* renamed from: h, reason: collision with root package name */
    private float f17570h = 0.0f;

    /* renamed from: j, reason: collision with root package name */
    private float f17572j = 1.778f;

    /* renamed from: k, reason: collision with root package name */
    private int f17573k = 0;

    /* renamed from: l, reason: collision with root package name */
    private int f17574l = 0;

    /* renamed from: i, reason: collision with root package name */
    public m4 f17571i = new m4();

    n1(p4 p4Var) {
        this.f17567e = false;
        this.f17569g = 0.0f;
        this.f17563a = p4Var;
        this.f17564b = new p(new q3(this.f17563a));
        this.f17565c = new p(new e4(this.f17563a));
        this.f17567e = false;
        this.f17569g = 0.0f;
    }

    static s6 a(List<i2> list, n1 n1Var) {
        for (i2 i2Var : list) {
            if (r3.f17715b || i2Var.h() > 0.4d) {
                int iJ = (int) i2Var.j();
                int iK = (int) i2Var.k();
                if (iJ > n1Var.f17563a.c() / 3 && iJ < (n1Var.f17563a.c() * 2) / 3 && iK > n1Var.f17563a.a() / 3 && iK < (n1Var.f17563a.a() * 2) / 3) {
                    float fC = n1Var.c(n1Var.f17566d);
                    s6 s6Var = new s6(1.0f);
                    s6Var.a(fC);
                    s6Var.a(i2Var);
                    return s6Var;
                }
            }
        }
        return null;
    }

    public s6 b(List<BarcodeFormat> list, i2 i2Var) {
        s6 s6VarB;
        a5 a5Var = new a5();
        HashMap map = new HashMap();
        map.put(l1.f17489c, list);
        try {
            s6VarB = i2Var != null ? a5Var.b(this.f17564b, this.f17565c, this.f17566d, map, this.f17571i, i2Var) : a5Var.b(this.f17564b, this.f17565c, null, map, this.f17571i, null);
            try {
                if (!r3.f17716c && s6VarB != null && s6VarB.k() == null && s6VarB.j() != null && s6VarB.j().length >= 3) {
                    float fB = o8.b(this.f17563a.c(), this.f17563a.a(), s6VarB.j());
                    if (Math.abs(1.0f - fB) > 0.001d) {
                        this.f17570h = fB;
                        this.f17567e = true;
                    }
                }
            } catch (a unused) {
                Log.e("DecodeProcessor", "decode2d AIScanException");
                return s6VarB;
            }
        } catch (a unused2) {
            s6VarB = null;
        }
        return s6VarB;
    }

    public s6 c(List<BarcodeFormat> list, i2 i2Var) {
        a5 a5Var = new a5();
        HashMap map = new HashMap();
        map.put(l1.f17489c, list);
        s6 s6VarA = null;
        if (i2Var != null) {
            try {
                Log.i("DecodeProcessor", "decodeHarm start.");
                s6VarA = a5Var.a(this.f17564b, this.f17565c, this.f17566d, map, this.f17571i, i2Var);
            } catch (a unused) {
                Log.e("DecodeProcessor", "decodeHarm AIScanException");
            }
        }
        if (!r3.f17716c && s6VarA != null && s6VarA.k() == null && s6VarA.j() != null && s6VarA.j().length >= 3) {
            float fB = o8.b(this.f17563a.c(), this.f17563a.a(), new u6[]{new u6(i2Var.d(), i2Var.e()), new u6(i2Var.d() + i2Var.f(), i2Var.e()), new u6(i2Var.d(), i2Var.e() + i2Var.c())});
            if (Math.abs(1.0f - fB) > 0.001d) {
                this.f17570h = fB;
                this.f17567e = true;
            }
            if (this.f17567e) {
                Log.i("DecodeProcessor", "decodeHarm need zoom");
            }
        }
        return s6VarA;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x008f A[Catch: a -> 0x00e5, TryCatch #4 {a -> 0x00e5, blocks: (B:10:0x0039, B:41:0x00f1, B:18:0x005d, B:20:0x006b, B:26:0x0085, B:28:0x008f, B:34:0x009f, B:32:0x0098, B:17:0x0058, B:13:0x0047, B:15:0x004b), top: B:65:0x0039, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f1 A[Catch: a -> 0x00e5, TRY_ENTER, TRY_LEAVE, TryCatch #4 {a -> 0x00e5, blocks: (B:10:0x0039, B:41:0x00f1, B:18:0x005d, B:20:0x006b, B:26:0x0085, B:28:0x008f, B:34:0x009f, B:32:0x0098, B:17:0x0058, B:13:0x0047, B:15:0x004b), top: B:65:0x0039, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.huawei.hms.scankit.p.s6 d(java.util.List<com.huawei.hms.scankit.aiscan.common.BarcodeFormat> r28, com.huawei.hms.scankit.p.i2 r29) {
        /*
            Method dump skipped, instructions count: 319
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.n1.d(java.util.List, com.huawei.hms.scankit.p.i2):com.huawei.hms.scankit.p.s6");
    }

    public s6 e(List<BarcodeFormat> list, i2 i2Var) {
        HashMap map = new HashMap();
        map.put(l1.f17489c, list);
        if (i2Var == null) {
            s6 s6VarA = a(map);
            if (s6VarA != null && s6VarA.k() == null && r3.f17730q) {
                r3.f17726m = true;
                s6VarA = a(map);
                r3.f17726m = false;
            }
            s6 s6Var = s6VarA;
            if (s6Var == null || s6Var.k() != null || !r3.f17731r) {
                return s6Var;
            }
            r3.f17727n = true;
            s6 s6VarA2 = a(map);
            r3.f17727n = false;
            return s6VarA2;
        }
        r3.f17725l = true;
        s6 s6VarA3 = a(map, i2Var);
        r3.f17725l = false;
        if (s6VarA3 != null && s6VarA3.k() == null && r3.f17729p) {
            r3.f17726m = true;
            s6VarA3 = g(list, i2Var);
            r3.f17726m = false;
        }
        if (s6VarA3 != null && s6VarA3.k() == null && r3.f17730q) {
            r3.f17727n = true;
            s6VarA3 = a(map, i2Var);
            r3.f17727n = false;
        }
        if ((s6VarA3 != null && s6VarA3.k() != null) || !r3.f17731r) {
            return s6VarA3;
        }
        r3.f17728o = true;
        s6 s6VarA4 = a(map, i2Var);
        r3.f17728o = false;
        return s6VarA4;
    }

    public s6 f(List<BarcodeFormat> list, i2 i2Var) throws a {
        float fE;
        p pVar;
        HashMap map = new HashMap();
        map.put(l1.f17489c, list);
        try {
            p4 p4VarC = i2Var != null ? this.f17566d.a().c() : this.f17563a;
            if (!r3.f17714a || (this.f17564b.e() <= 800 && this.f17564b.c() <= 800)) {
                fE = 1.0f;
            } else {
                fE = (this.f17564b.e() > this.f17564b.c() ? this.f17564b.e() : this.f17564b.c()) / 800.0f;
                p4VarC = this.f17571i.h(new p(new q3(p4VarC)), fE).a().c();
            }
            p4 p4Var = p4VarC;
            float f2 = fE;
            if (p4Var == null) {
                throw a.a();
            }
            if (!r3.f17714a || r3.f17715b) {
                pVar = new p(new q3(p4Var));
            } else {
                s sVarA = a(p4Var.b(), p4Var.c(), p4Var.a(), false);
                pVar = new p(new q3(p4Var));
                pVar.a(sVarA);
            }
            a5 a5Var = new a5();
            try {
                s6 s6VarA = a5Var.a(pVar, map);
                if (s6VarA == null || s6VarA.k() == null) {
                    throw a.a();
                }
                k2.a(s6VarA.j(), f2, i2Var);
                return s6VarA;
            } catch (a unused) {
                return a(a5Var, p4Var, pVar, map, f2, i2Var);
            }
        } catch (a unused2) {
            Log.e("DecodeProcessor", "decodeQRMulti AIScanException");
            return null;
        }
    }

    public s6 g(List<BarcodeFormat> list, i2 i2Var) {
        p pVarG;
        p pVar;
        s6 s6VarA;
        a5 a5Var = new a5();
        HashMap map = new HashMap();
        map.put(l1.f17489c, list);
        float fE = 1.0f;
        if (i2Var == null) {
            if (!r3.f17714a || (this.f17564b.e() <= 500 && this.f17564b.c() <= 500)) {
                pVarG = this.f17564b;
            } else {
                fE = (this.f17564b.e() > this.f17564b.c() ? this.f17564b.e() : this.f17564b.c()) / 500.0f;
                pVarG = this.f17571i.c(this.f17564b, fE);
            }
        } else if (!r3.f17714a || (pVar = this.f17566d) == null || (pVar.e() <= 500 && this.f17566d.c() <= 500)) {
            pVarG = this.f17566d;
        } else {
            fE = (this.f17566d.e() > this.f17566d.c() ? this.f17566d.e() : this.f17566d.c()) / 500.0f;
            pVarG = this.f17571i.g(this.f17566d, fE);
        }
        try {
            s6VarA = a5Var.a(pVarG, map);
            if (s6VarA != null) {
                try {
                    if (s6VarA.k() != null) {
                        k2.a(s6VarA.j(), fE, i2Var);
                        return s6VarA;
                    }
                } catch (a unused) {
                    Log.e("DecodeProcessor", "decodeQRSimple AIScanException");
                    return s6VarA;
                }
            }
        } catch (a unused2) {
            s6VarA = null;
        }
        return s6VarA;
    }

    public s6 h(List<BarcodeFormat> list, i2 i2Var) {
        a5 a5Var = new a5();
        HashMap map = new HashMap();
        map.put(l1.f17489c, list);
        s6 s6VarC = null;
        if (i2Var != null) {
            try {
                s6VarC = a5Var.c(this.f17566d, this.f17571i, map, i2Var);
            } catch (a unused) {
                Log.e("DecodeProcessor", "decode2d AIScanException");
            }
        }
        if (!r3.f17716c && s6VarC != null && s6VarC.k() == null && s6VarC.j() != null && s6VarC.j().length >= 3) {
            float fB = o8.b(this.f17563a.c(), this.f17563a.a(), s6VarC.j());
            if (Math.abs(1.0f - fB) > 0.001d) {
                this.f17570h = fB;
                this.f17567e = true;
            }
        }
        return s6VarC;
    }

    private p b(p pVar) {
        int iE = pVar.e();
        int iC = pVar.c();
        if (iE < iC) {
            if (iC / iE <= 1.2d) {
                return pVar;
            }
            int i2 = (int) (iE * 1.2d);
            int i3 = (iC - i2) / 2;
            this.f17574l = i3;
            return pVar.a(0, i3, iE, i2);
        }
        if (iE / iC <= 1.2d) {
            return pVar;
        }
        int i4 = (int) (iC * 1.2d);
        int i5 = (iE - i4) / 2;
        this.f17573k = i5;
        return pVar.a(i5, 0, i4, iC);
    }

    public p a() {
        return this.f17564b;
    }

    static s6 a(n1 n1Var) {
        float fC = n1Var.c(n1Var.f17564b);
        s6 s6Var = new s6(1.0f);
        s6Var.b(fC);
        s6Var.b(new i2(false, 0.0f, 0.0f, n1Var.f17564b.e(), n1Var.f17564b.e(), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        return s6Var;
    }

    public void b(i2 i2Var) {
        try {
            if (r3.f17714a) {
                k2.a(r3.f17715b, this.f17564b, i2Var);
                this.f17566d = i2Var.f17369l;
            }
        } catch (a unused) {
            Log.e("DecodeProcessor", "cropAndRotate AIScanException");
        }
    }

    public float c(p pVar) {
        if (((pVar == null || (pVar.a() == null && pVar.a().c() == null)) ? null : pVar.a().c().b()) == null) {
            return 1.0f;
        }
        int iE = pVar.e();
        int iC = pVar.c();
        long j2 = 0;
        for (int i2 = iC / 4; i2 < (iC * 3) / 4; i2++) {
            for (int i3 = iE / 4; i3 < (iE * 3) / 4; i3++) {
                j2 += r0[(i2 * iE) + i3] & 255;
            }
        }
        return (j2 / r0.length) * 4;
    }

    public static boolean a(List<i2> list, boolean z2) {
        if (!z2 && !r3.f17715b) {
            float[] fArrA = a(list.get(0));
            float fA = a(fArrA);
            r3.f17738y = fArrA;
            if (fA >= 0.6f) {
                r3.f17739z++;
            } else {
                r3.f17739z = 1;
            }
            o4.d("DecodeProcessor", "iou: " + fA + " focusAreaFrameCount: " + r3.f17739z);
            if (r3.f17739z < 8) {
                return false;
            }
            o4.d("DecodeProcessor", "need area focus");
            r3.f17739z = 1;
            return true;
        }
        r3.f17738y = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        r3.f17739z = 0;
        return false;
    }

    public boolean b() {
        return this.f17567e;
    }

    public boolean b(List<i2> list) {
        int i2;
        for (i2 i2Var : list) {
            boolean z2 = i2Var.g() == 1.0f && ((double) i2Var.h()) > 0.7d;
            boolean z3 = i2Var.g() == 1.0f && ((double) i2Var.h()) > 0.4d;
            boolean z4 = i2Var.g() == 2.0f && ((double) i2Var.h()) > 0.7d;
            boolean z5 = i2Var.g() == 3.0f && ((double) i2Var.h()) > 0.7d;
            boolean z6 = i2Var.g() == 6.0f && ((double) i2Var.h()) > 0.7d;
            boolean z7 = i2Var.g() == 7.0f && ((double) i2Var.h()) > 0.7d;
            if (z3 || z4 || z5 || z6 || z7) {
                if (!z2 && !a(this.f17564b, i2Var) && (i2 = r3.f17724k) <= 4) {
                    r3.f17724k = i2 + 2;
                } else {
                    r3.f17724k = 0;
                    float fB = o8.b(this.f17563a.c(), this.f17563a.a(), new u6[]{new u6(i2Var.d(), i2Var.e()), new u6(i2Var.d() + i2Var.f(), i2Var.e()), new u6(i2Var.d(), i2Var.e() + i2Var.c())});
                    if (fB > 1.001f) {
                        this.f17569g = fB;
                        this.f17567e = true;
                    }
                }
            }
        }
        return this.f17567e;
    }

    public float c() {
        return this.f17568f;
    }

    public boolean c(List<i2> list) {
        int i2;
        for (i2 i2Var : list) {
            boolean z2 = i2Var.g() == 1.0f && ((double) i2Var.h()) > 0.7d;
            boolean z3 = i2Var.g() == 1.0f && ((double) i2Var.h()) > 0.4d;
            boolean z4 = i2Var.g() == 2.0f && ((double) i2Var.h()) > 0.7d;
            boolean z5 = i2Var.g() == 3.0f && ((double) i2Var.h()) > 0.7d;
            boolean z6 = i2Var.g() == 4.0f && ((double) i2Var.h()) > 0.7d;
            boolean z7 = i2Var.g() == 5.0f && ((double) i2Var.h()) > 0.7d;
            boolean z8 = i2Var.g() == 6.0f && ((double) i2Var.h()) > 0.7d;
            boolean z9 = i2Var.g() == 7.0f && ((double) i2Var.h()) > 0.7d;
            if (z3 || z4 || z5 || z8 || z9 || z6 || z7) {
                if (!z2 && !a(this.f17564b, i2Var) && (i2 = r3.f17724k) <= 4) {
                    r3.f17724k = i2 + 2;
                } else {
                    r3.f17724k = 0;
                    float fB = o8.b(this.f17563a.c(), this.f17563a.a(), new u6[]{new u6(i2Var.d(), i2Var.e()), new u6(i2Var.d() + i2Var.f(), i2Var.e()), new u6(i2Var.d(), i2Var.e() + i2Var.c())});
                    if (fB > 1.001f) {
                        this.f17569g = fB;
                        return true;
                    }
                }
            }
        }
        return this.f17567e;
    }

    public float e() {
        return this.f17569g;
    }

    public float d() {
        return this.f17570h;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0102  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.huawei.hms.scankit.p.s6 a(java.util.Map<com.huawei.hms.scankit.p.l1, java.lang.Object> r14, com.huawei.hms.scankit.p.i2 r15) throws com.huawei.hms.scankit.p.a {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.n1.a(java.util.Map, com.huawei.hms.scankit.p.i2):com.huawei.hms.scankit.p.s6");
    }

    static boolean b(p pVar, i2 i2Var) {
        if (r3.f17714a && !r3.f17715b) {
            float fD = i2Var.d();
            float fE = i2Var.e();
            float f2 = i2Var.f();
            float fC = i2Var.c();
            float f3 = fD - ((f2 * 0.2f) / 2.0f);
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            float f4 = fE - ((0.2f * fC) / 2.0f);
            float f5 = f4 >= 0.0f ? f4 : 0.0f;
            float fE2 = (f2 * 1.2f) + f3;
            if (fE2 > pVar.e()) {
                fE2 = pVar.e();
            }
            float fC2 = (fC * 1.2f) + f5;
            if (fC2 > pVar.c()) {
                fC2 = pVar.c();
            }
            List<i2> listA = k2.a(r3.f17715b, pVar.a((int) f3, (int) f5, (int) (fE2 - f3), (int) (fC2 - f5)), 0, true);
            if (!listA.isEmpty() && listA.get(0).g() == 6.0f) {
                return true;
            }
        }
        return false;
    }

    public static float a(float[] fArr) {
        float[] fArr2 = r3.f17738y;
        float f2 = fArr2[2] - fArr2[0];
        float f3 = fArr2[3];
        float f4 = fArr2[1];
        float f5 = f2 * (f3 - f4);
        if (f5 == 0.0d) {
            return 0.0f;
        }
        float f6 = fArr[2] - fArr[0];
        float f7 = fArr[3];
        float f8 = fArr[1];
        float f9 = f5 + (f6 * (f7 - f8));
        float fMax = Math.max(f4, f8);
        float fMin = Math.min(r3.f17738y[3], fArr[3]);
        float fMax2 = Math.max(r3.f17738y[0], fArr[0]);
        float fMin2 = Math.min(r3.f17738y[2], fArr[2]);
        if (fMax >= fMin || fMax2 >= fMin2) {
            return 0.0f;
        }
        float f10 = (fMin - fMax) * (fMin2 - fMax2);
        return (f10 / (f9 - f10)) * 1.0f;
    }

    public static float[] a(i2 i2Var) {
        return new float[]{i2Var.f17376s, i2Var.f17375r, r0 + i2Var.f17374q, r2 + i2Var.f17373p};
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009e A[Catch: a -> 0x00b1, TryCatch #6 {a -> 0x00b1, blocks: (B:35:0x0088, B:37:0x009e, B:39:0x00a4, B:41:0x00ac, B:42:0x00b0), top: B:108:0x0088 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ed A[Catch: a -> 0x0100, TryCatch #5 {a -> 0x0100, blocks: (B:53:0x00ca, B:55:0x00ed, B:57:0x00f3, B:59:0x00fb, B:60:0x00ff), top: B:107:0x00ca }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x015f A[Catch: a -> 0x01ee, TRY_LEAVE, TryCatch #4 {a -> 0x01ee, blocks: (B:69:0x0115, B:71:0x015f, B:85:0x01b6, B:87:0x01c3, B:89:0x01c9, B:91:0x01cf, B:92:0x01e5, B:94:0x01e9, B:95:0x01ed, B:73:0x017f, B:75:0x0185, B:77:0x018b, B:79:0x0190, B:80:0x01aa, B:82:0x01ae, B:83:0x01b2), top: B:106:0x0115, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.huawei.hms.scankit.p.s6 a(com.huawei.hms.scankit.p.p r22, com.huawei.hms.scankit.p.s6 r23, com.huawei.hms.scankit.p.s6 r24, com.huawei.hms.scankit.p.a5 r25, java.util.Map<com.huawei.hms.scankit.p.l1, java.lang.Object> r26, float r27, com.huawei.hms.scankit.p.i2 r28) throws com.huawei.hms.scankit.p.a {
        /*
            Method dump skipped, instructions count: 528
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.n1.a(com.huawei.hms.scankit.p.p, com.huawei.hms.scankit.p.s6, com.huawei.hms.scankit.p.s6, com.huawei.hms.scankit.p.a5, java.util.Map, float, com.huawei.hms.scankit.p.i2):com.huawei.hms.scankit.p.s6");
    }

    public s6 a(Map<l1, Object> map) {
        p4 p4VarC;
        float f2;
        s6 s6VarA;
        a5 a5Var = new a5();
        p4 p4Var = this.f17563a;
        if (!r3.f17714a || (this.f17564b.e() <= 800 && this.f17564b.c() <= 800)) {
            p4VarC = p4Var;
            f2 = 1.0f;
        } else {
            float fMax = Math.max(this.f17564b.e(), this.f17564b.c()) / 800.0f;
            if (r3.f17716c && (this.f17564b.e() > this.f17564b.c() * this.f17572j || this.f17564b.c() > this.f17564b.e() * this.f17572j)) {
                fMax = Math.min(this.f17564b.e(), this.f17564b.c()) / 860.0f;
            }
            p4VarC = this.f17571i.d(this.f17564b, fMax).a().c();
            f2 = fMax;
        }
        if (p4VarC == null) {
            return null;
        }
        p pVar = new p(new q3(p4VarC));
        j6.a(this.f17563a);
        try {
            if (r3.f17714a) {
                pVar.a(a(p4VarC.b(), p4VarC.c(), p4VarC.a(), false));
            }
            s6VarA = a5Var.a(pVar, map);
        } catch (a unused) {
            Log.e("DecodeProcessor", "decodeQRUseFullImg AIScanException");
        }
        if (s6VarA != null && s6VarA.k() != null) {
            return a(s6VarA, f2, 0, 0);
        }
        s6 s6Var = (s6VarA == null || s6VarA.j() == null || s6VarA.j().length < 3) ? null : new s6(null, null, s6VarA.j(), BarcodeFormat.QR_CODE);
        s6 s6Var2 = s6Var == null ? new s6(null, null, null, BarcodeFormat.QR_CODE) : s6Var;
        if (r3.f17716c) {
            s6Var2 = a(a5Var, p4VarC, s6Var2, map, f2, 0, 0);
        }
        if (s6Var2 != null && s6Var2.k() != null) {
            return s6Var2;
        }
        if (s6Var2 != null && s6Var2.j() != null) {
            s6Var = s6Var2;
        }
        if (!r3.f17716c && s6Var != null && s6Var.j() != null && s6Var.j().length >= 3) {
            a(s6Var, f2, 0, 0);
            float fA = o8.a(this.f17563a.c(), this.f17563a.a(), s6Var.j());
            if (Math.abs(1.0f - fA) > 0.001d) {
                this.f17568f = fA;
                this.f17567e = true;
            }
        }
        return s6Var;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.huawei.hms.scankit.p.s6 a(com.huawei.hms.scankit.p.a5 r15, com.huawei.hms.scankit.p.p4 r16, com.huawei.hms.scankit.p.s6 r17, java.util.Map<com.huawei.hms.scankit.p.l1, java.lang.Object> r18, float r19, int r20, int r21) {
        /*
            r14 = this;
            r7 = r14
            r4 = r17
            r5 = r18
            r6 = r19
            r0 = r20
            r1 = r21
            java.lang.String r2 = "decodeQRUseFullImgTryHard AIScanException"
            java.lang.String r3 = "DecodeProcessor"
            boolean r8 = com.huawei.hms.scankit.p.r3.f17714a
            r9 = 0
            if (r8 == 0) goto L91
            r8 = 3
            com.huawei.hms.scankit.p.q3 r10 = new com.huawei.hms.scankit.p.q3     // Catch: com.huawei.hms.scankit.p.a -> L4f
            r11 = r16
            r10.<init>(r11)     // Catch: com.huawei.hms.scankit.p.a -> L4d
            com.huawei.hms.scankit.p.p r12 = new com.huawei.hms.scankit.p.p     // Catch: com.huawei.hms.scankit.p.a -> L4d
            r12.<init>(r10)     // Catch: com.huawei.hms.scankit.p.a -> L4d
            r10 = r15
            com.huawei.hms.scankit.p.s6 r9 = r15.a(r12, r5)     // Catch: com.huawei.hms.scankit.p.a -> L4b
            if (r9 == 0) goto L33
            java.lang.String r13 = r9.k()     // Catch: com.huawei.hms.scankit.p.a -> L4b
            if (r13 == 0) goto L33
            com.huawei.hms.scankit.p.s6 r0 = r14.a(r9, r6, r0, r1)     // Catch: com.huawei.hms.scankit.p.a -> L4b
            return r0
        L33:
            if (r9 == 0) goto L49
            com.huawei.hms.scankit.p.u6[] r13 = r9.j()     // Catch: com.huawei.hms.scankit.p.a -> L4b
            if (r13 == 0) goto L49
            com.huawei.hms.scankit.p.u6[] r13 = r9.j()     // Catch: com.huawei.hms.scankit.p.a -> L4b
            int r13 = r13.length     // Catch: com.huawei.hms.scankit.p.a -> L4b
            if (r13 < r8) goto L49
            com.huawei.hms.scankit.p.u6[] r9 = r9.j()     // Catch: com.huawei.hms.scankit.p.a -> L4b
            r4.b(r9)     // Catch: com.huawei.hms.scankit.p.a -> L4b
        L49:
            r9 = r12
            goto L55
        L4b:
            r9 = r12
            goto L52
        L4d:
            r10 = r15
            goto L52
        L4f:
            r10 = r15
            r11 = r16
        L52:
            android.util.Log.e(r3, r2)
        L55:
            if (r4 == 0) goto L82
            com.huawei.hms.scankit.p.u6[] r12 = r17.j()     // Catch: com.huawei.hms.scankit.p.a -> L7f
            if (r12 == 0) goto L82
            com.huawei.hms.scankit.p.u6[] r12 = r17.j()     // Catch: com.huawei.hms.scankit.p.a -> L7f
            int r12 = r12.length     // Catch: com.huawei.hms.scankit.p.a -> L7f
            if (r12 < r8) goto L82
            boolean r8 = com.huawei.hms.scankit.p.r3.f17726m     // Catch: com.huawei.hms.scankit.p.a -> L7f
            if (r8 != 0) goto L82
            r8 = 6
            double[] r8 = new double[r8]     // Catch: com.huawei.hms.scankit.p.a -> L7f
            r8 = {x0092: FILL_ARRAY_DATA , data: [0, 0, 0, 0, 0, 4607182418800017408} // fill-array     // Catch: com.huawei.hms.scankit.p.a -> L7f
            com.huawei.hms.scankit.p.s6 r8 = r14.a(r9, r5, r4, r8)     // Catch: com.huawei.hms.scankit.p.a -> L7f
            if (r8 == 0) goto L82
            java.lang.String r12 = r8.k()     // Catch: com.huawei.hms.scankit.p.a -> L7f
            if (r12 == 0) goto L82
            com.huawei.hms.scankit.p.s6 r0 = r14.a(r8, r6, r0, r1)     // Catch: com.huawei.hms.scankit.p.a -> L7f
            return r0
        L7f:
            android.util.Log.e(r3, r2)
        L82:
            r0 = r14
            r1 = r9
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            com.huawei.hms.scankit.p.s6 r9 = r0.a(r1, r2, r3, r4, r5, r6)
        L91:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.n1.a(com.huawei.hms.scankit.p.a5, com.huawei.hms.scankit.p.p4, com.huawei.hms.scankit.p.s6, java.util.Map, float, int, int):com.huawei.hms.scankit.p.s6");
    }

    private s6 a(p pVar, a5 a5Var, p4 p4Var, s6 s6Var, Map<l1, Object> map, float f2) {
        s6 s6VarA = null;
        try {
            j6.a(this.f17563a, s6Var);
            if (r3.f17716c && r3.f17735v[1]) {
                r3.f17732s = true;
                s6VarA = a5Var.a(this.f17564b, map);
                r3.f17732s = false;
                if (s6VarA != null && s6VarA.k() != null) {
                    return a(s6VarA, f2, 0, 0);
                }
            }
        } catch (a unused) {
            r3.f17732s = false;
        }
        float fE = pVar.e() / pVar.c();
        if (fE < 1.0f) {
            fE = 1.0f / fE;
        }
        if (!r3.f17726m && !r3.f17727n) {
            double d2 = fE;
            if (d2 > 1.27d && d2 < 1.272d) {
                r3.f17734u = true;
                try {
                    s6VarA = a5Var.a(new p(new e4(p4Var)), map);
                    if (s6VarA != null && s6VarA.k() != null) {
                        return a(s6VarA, f2, 0, 0);
                    }
                } catch (a unused2) {
                    Log.e("DecodeProcessor", "decodeQRUseFullImgTryHardSpecialCase AIScanException");
                }
                r3.f17734u = false;
            }
        }
        return s6VarA;
    }

    private s6 a(a5 a5Var, p4 p4Var, p pVar, Map<l1, Object> map, float f2, i2 i2Var) throws a {
        p pVar2;
        s6 s6VarA;
        p pVar3;
        s6 s6VarA2 = null;
        if (r3.f17714a && !r3.f17715b) {
            try {
                pVar3 = new p(new q3(p4Var));
                try {
                    s6VarA2 = a5Var.a(pVar3, map);
                    if (s6VarA2 != null && s6VarA2.k() != null) {
                        k2.a(s6VarA2.j(), f2, i2Var);
                        return s6VarA2;
                    }
                } catch (a unused) {
                    pVar = pVar3;
                    Log.e("DecodeProcessor", "decodeQRMultiHard AIScanException");
                    pVar3 = pVar;
                    if (s6VarA2 != null) {
                        try {
                            s6VarA2 = a(pVar3, map, s6VarA2, new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d});
                            if (s6VarA2 != null) {
                                k2.a(s6VarA2.j(), f2, i2Var);
                                return s6VarA2;
                            }
                        } catch (a unused2) {
                            Log.e("DecodeProcessor", "decodeQRMultiHard AIScanException");
                        }
                    }
                    pVar2 = new p(new e4(p4Var));
                    s6VarA = a5Var.a(pVar2, map);
                    if (s6VarA == null) {
                    }
                    throw a.a();
                }
            } catch (a unused3) {
            }
            if (s6VarA2 != null && s6VarA2.j() != null && s6VarA2.j().length >= 3) {
                s6VarA2 = a(pVar3, map, s6VarA2, new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d});
                if (s6VarA2 != null && s6VarA2.k() != null) {
                    k2.a(s6VarA2.j(), f2, i2Var);
                    return s6VarA2;
                }
            }
        }
        pVar2 = new p(new e4(p4Var));
        try {
            s6VarA = a5Var.a(pVar2, map);
            if (s6VarA == null && s6VarA.k() != null) {
                k2.a(s6VarA.j(), f2, i2Var);
                return s6VarA;
            }
            throw a.a();
        } catch (a unused4) {
            if (r3.f17714a && !r3.f17715b && s6VarA2 != null && s6VarA2.j() != null && s6VarA2.j().length >= 3 && (s6VarA2 = a(pVar2, map, s6VarA2, new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d})) != null && s6VarA2.k() != null) {
                k2.a(s6VarA2.j(), f2, i2Var);
            }
            return s6VarA2;
        }
    }

    public s6 a(List<BarcodeFormat> list, i2 i2Var) {
        s6 s6VarA;
        a5 a5Var = new a5();
        HashMap map = new HashMap();
        map.put(l1.f17489c, list);
        if (r3.f17716c) {
            map.put(l1.f17490d, Boolean.valueOf(r3.f17716c));
        }
        try {
            if (i2Var != null) {
                s6VarA = a5Var.a(this.f17564b, this.f17566d, map, this.f17571i, i2Var);
            } else {
                s6VarA = a5Var.a(this.f17564b, (p) null, map, this.f17571i, (i2) null);
            }
        } catch (a unused) {
            Log.e("DecodeProcessor", "decode1d AIScanException");
            s6VarA = null;
        }
        s6 s6Var = s6VarA;
        if (s6Var != null || r3.f17715b || i2Var == null || !r3.f17716c || i2Var.h() >= 0.8d) {
            return s6Var;
        }
        float fI = i2Var.i() % 180.0f;
        boolean z2 = false;
        boolean z3 = ((double) i2Var.c()) > ((double) this.f17564b.c()) * 0.97d && ((fI < 5.0f && fI > -5.0f) || fI < -175.0f || fI > 175.0f);
        if (i2Var.b() > this.f17564b.e() * 0.97d && ((fI < 95.0f && fI > 85.0f) || (fI < -85.0f && fI > -95.0f))) {
            z2 = true;
        }
        if (!z3 && !z2) {
            return s6Var;
        }
        this.f17571i.a();
        try {
            return a5Var.a(this.f17564b, (p) null, map, this.f17571i, (i2) null);
        } catch (a unused2) {
            Log.e("DecodeProcessor", "decode1d AIScanException");
            return s6Var;
        }
    }

    public List<i2> a(int i2, boolean z2) {
        List<i2> listA;
        List<i2> arrayList = new ArrayList<>();
        if (r3.f17714a) {
            boolean z3 = r3.f17715b;
            if (!z3) {
                byte[] bArrC = y4.c();
                byte[] bArrA = y4.a();
                byte[] bArrB = y4.b();
                LoadOpencvJNIUtil.setModel(bArrC, bArrC.length, bArrA, bArrA.length, bArrB, bArrB.length);
                p pVarA = this.f17564b;
                long jCurrentTimeMillis = System.currentTimeMillis() % 10;
                boolean z4 = jCurrentTimeMillis % 2 == 0;
                boolean z5 = jCurrentTimeMillis % 3 == 0;
                if (i2 == 0 && !r3.f17716c && z4) {
                    pVarA = b(this.f17564b);
                } else if (i2 == 0 && !r3.f17716c && z5) {
                    pVarA = a(b(this.f17564b));
                }
                listA = k2.a(r3.f17715b, pVarA, i2, z2);
            } else {
                listA = k2.a(z3, this.f17564b, i2, z2);
            }
            arrayList = listA;
            a(arrayList);
        }
        return arrayList;
    }

    private p a(p pVar) {
        int iE = pVar.e();
        int iC = pVar.c();
        int i2 = (int) (iE * 0.75d);
        int i3 = (int) (iC * 0.75d);
        int i4 = (iE - i2) / 2;
        this.f17573k += i4;
        int i5 = (iC - i3) / 2;
        this.f17574l += i5;
        return pVar.a(i4, i5, i2, i3);
    }

    private void a(List<i2> list) {
        for (i2 i2Var : list) {
            i2Var.a(this.f17563a.c(), this.f17563a.a(), this.f17573k, this.f17574l);
            float fMin = Math.min(Math.abs(i2Var.i() % 90.0f), 90.0f - Math.abs(i2Var.i() % 90.0f));
            if (i2Var.c() * i2Var.f() > this.f17563a.a() * 0.9f * this.f17563a.c() && fMin < 5.0f) {
                i2Var.b(this.f17563a.c(), this.f17563a.a());
            }
        }
    }

    private s6 a(p pVar, Map<l1, Object> map, s6 s6Var, double[] dArr) throws a {
        s6 s6VarA;
        if (pVar == null) {
            return null;
        }
        a5 a5Var = new a5();
        int[] iArr = {0, 0};
        byte[] bArrA = k7.a(pVar, map, s6Var, iArr, dArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        e6 e6Var = new e6(bArrA, i2, i3, 0, 0, i2, i3, false);
        try {
            s6VarA = a5Var.a(new p(new q3(e6Var)), map);
            if (s6VarA != null) {
                try {
                    if (s6VarA.k() != null) {
                        u6[] u6VarArrA = k7.a(s6VarA.j(), pVar.e(), pVar.c(), dArr);
                        s6VarA.a();
                        s6VarA.b(u6VarArrA);
                        return s6VarA;
                    }
                } catch (a unused) {
                    p pVar2 = new p(new e4(e6Var));
                    try {
                        s6 s6VarA2 = a5Var.a(pVar2, map);
                        if (s6VarA2 != null && s6VarA2.k() != null) {
                            u6[] u6VarArrA2 = k7.a(s6VarA2.j(), pVar.e(), pVar.c(), dArr);
                            s6VarA2.a();
                            s6VarA2.b(u6VarArrA2);
                            return s6VarA2;
                        }
                        throw a.a();
                    } catch (a unused2) {
                        pVar2.a(a(e6Var.b(), e6Var.c(), e6Var.a(), false));
                        try {
                            s6 s6VarA3 = a5Var.a(pVar2, map);
                            if (s6VarA3 != null && s6VarA3.k() != null) {
                                u6[] u6VarArrA3 = k7.a(s6VarA3.j(), pVar.e(), pVar.c(), dArr);
                                s6VarA3.a();
                                s6VarA3.b(u6VarArrA3);
                                return s6VarA3;
                            }
                            throw a.a();
                        } catch (a unused3) {
                            Log.e("DecodeProcessor", "rotatedQRBinarizer  AIScanException");
                            return s6VarA;
                        }
                    }
                }
            }
            throw a.a();
        } catch (a unused4) {
            s6VarA = s6Var;
        }
    }

    private s6 a(s6 s6Var, float f2, int i2, int i3) {
        if (s6Var != null && s6Var.j().length == 4 && (Math.abs(f2 - 1.0f) >= 1.0E-6f || i2 != 0 || i3 != 0)) {
            u6[] u6VarArr = new u6[4];
            for (int i4 = 0; i4 < 4; i4++) {
                u6VarArr[i4] = new u6((s6Var.j()[i4].b() * f2) + i2, (s6Var.j()[i4].c() * f2) + i3);
            }
            s6Var.a();
            s6Var.a(u6VarArr);
        }
        return s6Var;
    }

    public static s a(byte[] bArr, int i2, int i3, boolean z2) throws a {
        int i4 = i2 / 11;
        byte[] bArrAdaptivebinary = LoadOpencvJNIUtil.adaptivebinary(bArr, i3, i2, (i4 + (i4 % 2)) - 1, z2);
        if (bArrAdaptivebinary != null) {
            s sVar = new s(i2, i3);
            for (int i5 = 0; i5 < i3; i5++) {
                for (int i6 = 0; i6 < i2; i6++) {
                    if (bArrAdaptivebinary[(i5 * i2) + i6] == 0) {
                        sVar.c(i6, i5);
                    }
                }
            }
            return sVar;
        }
        throw a.a();
    }

    public static p a(byte[] bArr, int i2, int i3) throws a {
        byte[] bArrSharpen = LoadOpencvJNIUtil.sharpen(bArr, i3, i2);
        if (bArrSharpen != null) {
            return new p(new e4(new e6(bArrSharpen, i2, i3, 0, 0, i2, i3, false)));
        }
        throw a.a();
    }

    private static boolean a(p pVar, i2 i2Var) {
        if (r3.f17714a && !r3.f17715b) {
            float fD = i2Var.d();
            float fE = i2Var.e();
            float f2 = i2Var.f();
            float fC = i2Var.c();
            float f3 = fD - ((f2 * 0.2f) / 2.0f);
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            float f4 = fE - ((0.2f * fC) / 2.0f);
            float f5 = f4 >= 0.0f ? f4 : 0.0f;
            float fE2 = (f2 * 1.2f) + f3;
            if (fE2 > pVar.e()) {
                fE2 = pVar.e();
            }
            float fC2 = (fC * 1.2f) + f5;
            if (fC2 > pVar.c()) {
                fC2 = pVar.c();
            }
            float f6 = fE2 - f3;
            float f7 = fC2 - f5;
            if (f6 < pVar.e() / 2.0f && f7 < pVar.c() / 2.0f) {
                for (i2 i2Var2 : k2.a(r3.f17715b, pVar.a((int) f3, (int) f5, (int) f6, (int) f7), 0, true)) {
                    boolean z2 = i2Var2.g() == 1.0f && ((double) i2Var2.h()) > 0.5d;
                    boolean z3 = i2Var.g() == 2.0f && i2Var2.g() == 2.0f && ((double) i2Var2.h()) > 0.7d;
                    boolean z4 = i2Var.g() == 3.0f && i2Var2.g() == 3.0f && ((double) i2Var2.h()) > 0.7d;
                    boolean z5 = i2Var.g() == 7.0f && i2Var2.g() == 7.0f && ((double) i2Var2.h()) > 0.7d;
                    boolean z6 = i2Var.g() == 6.0f && i2Var2.g() == 6.0f && ((double) i2Var2.h()) > 0.7d;
                    if (z2 || z3 || z4 || z5 || z6) {
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }
}
