package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;

/* loaded from: classes4.dex */
public final class o2 extends q7 {

    /* renamed from: j, reason: collision with root package name */
    public static final int[] f17626j = {0, 11, 13, 14, 19, 25, 28, 21, 22, 26};

    /* renamed from: i, reason: collision with root package name */
    private String f17628i = "";

    /* renamed from: h, reason: collision with root package name */
    private final int[] f17627h = new int[4];

    @Override // com.huawei.hms.scankit.p.q7
    boolean a(int i2, int i3, r rVar) {
        return rVar.a(i3, (i3 - i2) + i3, false, false);
    }

    @Override // com.huawei.hms.scankit.p.q7
    protected int a(r rVar, int[] iArr, StringBuilder sb) throws a {
        int[] iArr2 = this.f17627h;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int iE = rVar.e();
        int i2 = iArr[1];
        int i3 = 0;
        for (int i4 = 0; i4 < 6 && i2 < iE; i4++) {
            int iA = q7.a(rVar, iArr2, i2, q7.f17705g);
            sb.append((char) ((iA % 10) + 48));
            for (int i5 : iArr2) {
                i2 += i5;
            }
            if (iA >= 10) {
                i3 |= 1 << (5 - i4);
            }
        }
        a(sb, i3);
        this.f17628i = sb.substring(0, 1);
        int i6 = q7.a(rVar, i2, true, q7.f17702d)[1];
        for (int i7 = 0; i7 < 6 && i6 < iE; i7++) {
            sb.append((char) (q7.a(rVar, iArr2, i6, q7.f17704f) + 48));
            for (int i8 : iArr2) {
                i6 += i8;
            }
        }
        if (a(sb)) {
            return i6;
        }
        throw a.a();
    }

    @Override // com.huawei.hms.scankit.p.q7
    BarcodeFormat a() {
        return BarcodeFormat.EAN_13;
    }

    private static void a(StringBuilder sb, int i2) throws a {
        for (int i3 = 0; i3 < 10; i3++) {
            if (i2 == f17626j[i3]) {
                sb.insert(0, (char) (i3 + 48));
                return;
            }
        }
        throw a.a();
    }

    private static boolean a(StringBuilder sb) {
        int iCharAt = sb.charAt(sb.length() - 1) - '0';
        int iCharAt2 = 0;
        for (int i2 = 0; i2 < sb.length() - 1; i2++) {
            iCharAt2 += i2 % 2 == 0 ? sb.charAt(i2) - '0' : (sb.charAt(i2) - '0') * 3;
        }
        return (iCharAt2 + iCharAt) % 10 == 0;
    }

    @Override // com.huawei.hms.scankit.p.q7
    boolean a(int[] iArr, int[] iArr2) throws a {
        int i2 = iArr2[1] - iArr2[0];
        int i3 = iArr[1];
        int i4 = iArr[0];
        int iRound = (int) Math.round((r1 - i4) / ((i2 + (i3 - i4)) / 6.0d));
        return this.f17628i.equals("0") ? ((double) Math.abs(iRound + (-95))) <= 18.05d || ((double) Math.abs(iRound + (-113))) <= 21.47d : ((double) Math.abs(iRound + (-95))) <= 18.05d;
    }
}
