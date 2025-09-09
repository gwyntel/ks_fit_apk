package com.huawei.hms.scankit.p;

import com.yc.utesdk.ble.close.KeyType;
import java.util.Map;

/* loaded from: classes4.dex */
public class e2 {

    /* renamed from: g, reason: collision with root package name */
    private static final int[] f17189g = {210, 236, KeyType.SEND_MESSAGE_REPLY_DATA_COMMAND, 244, KeyType.QUERY_BRIGHT_SCREEN_PARAM, KeyType.SET_AI_WATCH_VOICE_CONTENT};

    /* renamed from: h, reason: collision with root package name */
    private static final int[] f17190h = {21, 25, 29, 33, 37, 41};

    /* renamed from: a, reason: collision with root package name */
    private final s f17191a;

    /* renamed from: b, reason: collision with root package name */
    private v6 f17192b;

    /* renamed from: c, reason: collision with root package name */
    private g3 f17193c;

    /* renamed from: d, reason: collision with root package name */
    private g3 f17194d;

    /* renamed from: e, reason: collision with root package name */
    private g3 f17195e;

    /* renamed from: f, reason: collision with root package name */
    private g3 f17196f;

    public e2(s sVar) {
        this.f17191a = sVar;
    }

    private float b(int i2, int i3, s sVar) throws a {
        int iC = sVar.c();
        int[] iArr = new int[5];
        for (int i4 = 0; i4 < 5; i4++) {
            iArr[i4] = 0;
        }
        int i5 = i2;
        while (i5 >= 0 && sVar.b(i3, i5)) {
            iArr[2] = iArr[2] + 1;
            i5--;
        }
        if (i5 < 0) {
            throw a.a();
        }
        while (i5 >= 0 && !sVar.b(i3, i5)) {
            iArr[1] = iArr[1] + 1;
            i5--;
        }
        if (i5 < 0) {
            throw a.a();
        }
        while (i5 >= 0 && sVar.b(i3, i5)) {
            iArr[0] = iArr[0] + 1;
            i5--;
        }
        int i6 = i2 + 1;
        while (i6 < iC && sVar.b(i3, i6)) {
            iArr[2] = iArr[2] + 1;
            i6++;
        }
        if (i6 == iC) {
            throw a.a();
        }
        while (i6 < iC && !sVar.b(i3, i6)) {
            iArr[3] = iArr[3] + 1;
            i6++;
        }
        if (i6 == iC) {
            throw a.a();
        }
        while (i6 < iC && sVar.b(i3, i6)) {
            iArr[4] = iArr[4] + 1;
            i6++;
        }
        return ((((iArr[0] + iArr[1]) + iArr[2]) + iArr[3]) + iArr[4]) / 6.0f;
    }

    public final g3[] a(Map<l1, ?> map) throws a {
        v6 v6Var = map == null ? null : (v6) map.get(l1.f17496j);
        this.f17192b = v6Var;
        return new h3(this.f17191a, v6Var).a(map);
    }

    public final int a(g3[] g3VarArr, g3 g3Var) throws a {
        this.f17193c = g3VarArr[0];
        this.f17194d = g3VarArr[1];
        g3 g3Var2 = g3VarArr[2];
        this.f17195e = g3Var2;
        if (g3Var == null) {
            this.f17196f = new g3((g3Var2.b() - this.f17194d.b()) + this.f17193c.b(), (this.f17195e.c() - this.f17194d.c()) + this.f17193c.c(), 6.0f);
        } else {
            this.f17196f = g3Var;
        }
        float fA = a(this.f17194d, this.f17195e, this.f17193c, this.f17191a);
        if (fA >= 1.0f) {
            int iA = a(this.f17194d, this.f17195e, this.f17193c, fA);
            if (iA < 0 || iA > 7) {
                throw a.a();
            }
            return iA;
        }
        throw a.a();
    }

    public final j2 a(int i2) throws a {
        s sVarA;
        int i3 = i2 - 1;
        double d2 = f17189g[i3];
        float fCos = (float) ((Math.cos(0.7853981633974483d) * d2) + 300.0d);
        float fCos2 = (float) (300.0d - (d2 * Math.cos(0.7853981633974483d)));
        s sVarA2 = a(this.f17191a, a(this.f17194d, this.f17195e, this.f17193c, this.f17196f, new u6(fCos2, fCos2), new u6(fCos, fCos2), new u6(fCos2, fCos), new u6(fCos, fCos)), 600);
        int i4 = f17190h[i3];
        s sVar = new s(i4, i4);
        switch (i2) {
            case 1:
                sVarA = e8.a(sVar, sVarA2, i4, 300.0d);
                break;
            case 2:
                sVarA = h8.a(sVar, sVarA2, i4, 300.0d);
                break;
            case 3:
                sVarA = g8.a(sVar, sVarA2, i4, 300.0d);
                break;
            case 4:
                sVarA = d8.a(sVar, sVarA2, i4, 300.0d);
                break;
            case 5:
                sVarA = c8.a(sVar, sVarA2, i4, 300.0d);
                break;
            case 6:
                sVarA = f8.a(sVar, sVarA2, i4, 300.0d);
                break;
            default:
                throw a.a();
        }
        return new j2(sVarA, new u6[]{this.f17193c, this.f17194d, this.f17195e, this.f17196f});
    }

    private static d6 a(u6 u6Var, u6 u6Var2, u6 u6Var3, u6 u6Var4, u6 u6Var5, u6 u6Var6, u6 u6Var7, u6 u6Var8) throws a {
        return d6.a(u6Var5.b(), u6Var5.c(), u6Var6.b(), u6Var6.c(), u6Var8.b(), u6Var8.c(), u6Var7.b(), u6Var7.c(), u6Var.b(), u6Var.c(), u6Var2.b(), u6Var2.c(), u6Var4.b(), u6Var4.c(), u6Var3.b(), u6Var3.c());
    }

    private static s a(s sVar, d6 d6Var, int i2) throws a {
        return s3.a().a(sVar, i2, i2, d6Var, false);
    }

    private static int a(u6 u6Var, u6 u6Var2, u6 u6Var3, float f2) throws a {
        float fA = ((u6.a(u6Var, u6Var2) / f2) + (u6.a(u6Var, u6Var3) / f2)) / 2.0f;
        if (fA >= 28.1f && fA <= 31.1f) {
            return 1;
        }
        if (fA >= 31.7f && fA <= 34.7f) {
            return 2;
        }
        if (fA >= 35.9f && fA <= 38.9f) {
            return 3;
        }
        if (fA >= 41.7f && fA <= 44.7f) {
            return 4;
        }
        if (fA >= 46.3f && fA <= 49.3f) {
            return 5;
        }
        if (fA < 54.4f || fA > 57.4f) {
            return Math.round((fA - 25.0f) / 4.0f);
        }
        return 6;
    }

    protected final float a(u6 u6Var, u6 u6Var2, u6 u6Var3, s sVar) throws a {
        return (((((a((int) u6Var.b(), (int) u6Var.c(), sVar) + a((int) u6Var2.b(), (int) u6Var2.c(), sVar)) + a((int) u6Var3.b(), (int) u6Var3.c(), sVar)) + b((int) u6Var.c(), (int) u6Var.b(), sVar)) + b((int) u6Var2.c(), (int) u6Var2.b(), sVar)) + b((int) u6Var3.c(), (int) u6Var3.b(), sVar)) / 6.0f;
    }

    private float a(int i2, int i3, s sVar) throws a {
        int iE = sVar.e();
        int[] iArr = new int[5];
        for (int i4 = 0; i4 < 5; i4++) {
            iArr[i4] = 0;
        }
        int i5 = i2;
        while (i5 >= 0 && sVar.b(i5, i3)) {
            iArr[2] = iArr[2] + 1;
            i5--;
        }
        if (i5 >= 0) {
            while (i5 >= 0 && !sVar.b(i5, i3)) {
                iArr[1] = iArr[1] + 1;
                i5--;
            }
            if (i5 >= 0) {
                while (i5 >= 0 && sVar.b(i5, i3)) {
                    iArr[0] = iArr[0] + 1;
                    i5--;
                }
                int i6 = i2 + 1;
                while (i6 < iE && sVar.b(i6, i3)) {
                    iArr[2] = iArr[2] + 1;
                    i6++;
                }
                if (i6 != iE) {
                    while (i6 < iE && !sVar.b(i6, i3)) {
                        iArr[3] = iArr[3] + 1;
                        i6++;
                    }
                    if (i6 != iE) {
                        while (i6 < iE && sVar.b(i6, i3)) {
                            iArr[4] = iArr[4] + 1;
                            i6++;
                        }
                        return ((((iArr[0] + iArr[1]) + iArr[2]) + iArr[3]) + iArr[4]) / 6.0f;
                    }
                    throw a.a();
                }
                throw a.a();
            }
            throw a.a();
        }
        throw a.a();
    }
}
