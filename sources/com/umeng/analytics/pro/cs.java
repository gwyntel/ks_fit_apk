package com.umeng.analytics.pro;

import com.umeng.analytics.pro.cj;

/* loaded from: classes4.dex */
public class cs {

    /* renamed from: a, reason: collision with root package name */
    private static int f21642a = Integer.MAX_VALUE;

    public static void a(int i2) {
        f21642a = i2;
    }

    public static void a(cp cpVar, byte b2) throws bw {
        a(cpVar, b2, f21642a);
    }

    public static void a(cp cpVar, byte b2, int i2) throws bw {
        if (i2 > 0) {
            int i3 = 0;
            switch (b2) {
                case 2:
                    cpVar.t();
                    return;
                case 3:
                    cpVar.u();
                    return;
                case 4:
                    cpVar.y();
                    return;
                case 5:
                case 7:
                case 9:
                default:
                    return;
                case 6:
                    cpVar.v();
                    return;
                case 8:
                    cpVar.w();
                    return;
                case 10:
                    cpVar.x();
                    return;
                case 11:
                    cpVar.A();
                    return;
                case 12:
                    cpVar.j();
                    while (true) {
                        byte b3 = cpVar.l().f21619b;
                        if (b3 == 0) {
                            cpVar.k();
                            return;
                        } else {
                            a(cpVar, b3, i2 - 1);
                            cpVar.m();
                        }
                    }
                case 13:
                    cm cmVarN = cpVar.n();
                    while (i3 < cmVarN.f21625c) {
                        int i4 = i2 - 1;
                        a(cpVar, cmVarN.f21623a, i4);
                        a(cpVar, cmVarN.f21624b, i4);
                        i3++;
                    }
                    cpVar.o();
                    return;
                case 14:
                    ct ctVarR = cpVar.r();
                    while (i3 < ctVarR.f21644b) {
                        a(cpVar, ctVarR.f21643a, i2 - 1);
                        i3++;
                    }
                    cpVar.s();
                    return;
                case 15:
                    cl clVarP = cpVar.p();
                    while (i3 < clVarP.f21622b) {
                        a(cpVar, clVarP.f21621a, i2 - 1);
                        i3++;
                    }
                    cpVar.q();
                    return;
            }
        } else {
            throw new bw("Maximum skip depth exceeded");
        }
    }

    public static cr a(byte[] bArr, cr crVar) {
        if (bArr[0] > 16) {
            return new cj.a();
        }
        return (bArr.length <= 1 || (bArr[1] & 128) == 0) ? crVar : new cj.a();
    }
}
