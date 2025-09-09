package com.xiaomi.push;

/* loaded from: classes4.dex */
public class km {

    /* renamed from: a, reason: collision with root package name */
    private static int f24385a = Integer.MAX_VALUE;

    public static void a(kj kjVar, byte b2) {
        a(kjVar, b2, f24385a);
    }

    public static void a(kj kjVar, byte b2, int i2) throws kd {
        if (i2 <= 0) {
            throw new kd("Maximum skip depth exceeded");
        }
        int i3 = 0;
        switch (b2) {
            case 2:
                kjVar.mo680a();
                return;
            case 3:
                kjVar.a();
                return;
            case 4:
                kjVar.mo667a();
                return;
            case 5:
            case 7:
            case 9:
            default:
                return;
            case 6:
                kjVar.mo677a();
                return;
            case 8:
                kjVar.mo668a();
                return;
            case 10:
                kjVar.mo669a();
                return;
            case 11:
                kjVar.mo676a();
                return;
            case 12:
                kjVar.mo674a();
                while (true) {
                    byte b3 = kjVar.mo670a().f24379a;
                    if (b3 == 0) {
                        kjVar.f();
                        return;
                    } else {
                        a(kjVar, b3, i2 - 1);
                        kjVar.g();
                    }
                }
            case 13:
                ki kiVarMo672a = kjVar.mo672a();
                while (i3 < kiVarMo672a.f924a) {
                    int i4 = i2 - 1;
                    a(kjVar, kiVarMo672a.f24381a, i4);
                    a(kjVar, kiVarMo672a.f24382b, i4);
                    i3++;
                }
                kjVar.h();
                return;
            case 14:
                kn knVarMo673a = kjVar.mo673a();
                while (i3 < knVarMo673a.f925a) {
                    a(kjVar, knVarMo673a.f24386a, i2 - 1);
                    i3++;
                }
                kjVar.j();
                return;
            case 15:
                kh khVarMo671a = kjVar.mo671a();
                while (i3 < khVarMo671a.f923a) {
                    a(kjVar, khVarMo671a.f24380a, i2 - 1);
                    i3++;
                }
                kjVar.i();
                return;
        }
    }
}
