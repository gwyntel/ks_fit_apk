package com.huawei.hms.scankit.p;

import com.google.common.base.Ascii;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import kotlin.text.Typography;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharUtils;

/* loaded from: classes4.dex */
final class o1 {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f17611a = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /* renamed from: b, reason: collision with root package name */
    private static final char[] f17612b;

    /* renamed from: c, reason: collision with root package name */
    private static final char[] f17613c;

    /* renamed from: d, reason: collision with root package name */
    private static final char[] f17614d;

    /* renamed from: e, reason: collision with root package name */
    private static final char[] f17615e;

    static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f17616a;

        static {
            int[] iArr = new int[b.values().length];
            f17616a = iArr;
            try {
                iArr[b.C40_ENCODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f17616a[b.TEXT_ENCODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f17616a[b.ANSIX12_ENCODE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f17616a[b.EDIFACT_ENCODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f17616a[b.BASE256_ENCODE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private enum b {
        PAD_ENCODE,
        ASCII_ENCODE,
        C40_ENCODE,
        TEXT_ENCODE,
        ANSIX12_ENCODE,
        EDIFACT_ENCODE,
        BASE256_ENCODE,
        UPPER_ENCODE
    }

    static {
        char[] cArr = {'!', Typography.quote, '#', '$', '%', Typography.amp, '\'', '(', ')', '*', '+', ',', '-', '.', IOUtils.DIR_SEPARATOR_UNIX, ':', ';', Typography.less, com.alipay.sdk.m.n.a.f9565h, Typography.greater, '?', '@', '[', IOUtils.DIR_SEPARATOR_WINDOWS, ']', '^', '_'};
        f17612b = cArr;
        f17613c = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        f17614d = cArr;
        f17615e = new char[]{'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', Ascii.MAX};
    }

    static w1 a(byte[] bArr, Map<l1, ?> map) throws com.huawei.hms.scankit.p.a {
        w wVar = new w(bArr);
        StringBuilder sb = new StringBuilder(100);
        StringBuilder sb2 = new StringBuilder(0);
        ArrayList arrayList = new ArrayList(1);
        b bVarA = b.ASCII_ENCODE;
        do {
            b bVar = b.ASCII_ENCODE;
            if (bVarA == bVar) {
                bVarA = a(wVar, sb, sb2);
            } else {
                int i2 = a.f17616a[bVarA.ordinal()];
                if (i2 == 1) {
                    b(wVar, sb);
                } else if (i2 == 2) {
                    d(wVar, sb);
                } else if (i2 == 3) {
                    a(wVar, sb);
                } else if (i2 == 4) {
                    c(wVar, sb);
                } else {
                    if (i2 != 5) {
                        throw com.huawei.hms.scankit.p.a.a("AIScanException");
                    }
                    a(wVar, sb, arrayList);
                }
                bVarA = bVar;
            }
            if (bVarA == b.PAD_ENCODE) {
                break;
            }
        } while (wVar.a() > 0);
        if (sb2.length() > 0) {
            sb.append((CharSequence) sb2);
        }
        int length = sb.length();
        byte[] bArr2 = new byte[length];
        for (int i3 = 0; i3 < length; i3++) {
            bArr2[i3] = (byte) sb.charAt(i3);
        }
        try {
            String str = new String(bArr2, c7.a(bArr2, map));
            if (arrayList.isEmpty()) {
                arrayList = null;
            }
            return new w1(bArr, str, arrayList, null);
        } catch (UnsupportedEncodingException unused) {
            throw com.huawei.hms.scankit.p.a.a();
        }
    }

    private static void b(w wVar, StringBuilder sb) throws com.huawei.hms.scankit.p.a {
        int iA;
        int[] iArr = new int[3];
        boolean z2 = false;
        int i2 = 0;
        while (wVar.a() != 8 && (iA = wVar.a(8)) != 254) {
            a(iA, wVar.a(8), iArr);
            for (int i3 = 0; i3 < 3; i3++) {
                int[] iArrB = b(sb, i2, iArr[i3], z2);
                i2 = iArrB[0];
                z2 = iArrB[1] == 1;
            }
            if (wVar.a() <= 0) {
                return;
            }
        }
    }

    private static void c(w wVar, StringBuilder sb) {
        while (wVar.a() > 16) {
            for (int i2 = 0; i2 < 4; i2++) {
                int iA = wVar.a(6);
                if (iA == 31) {
                    int iB = 8 - wVar.b();
                    if (iB != 8) {
                        wVar.a(iB);
                        return;
                    }
                    return;
                }
                if ((iA & 32) == 0) {
                    iA |= 64;
                }
                sb.append((char) iA);
            }
            if (wVar.a() <= 0) {
                return;
            }
        }
    }

    private static void d(w wVar, StringBuilder sb) throws com.huawei.hms.scankit.p.a {
        int iA;
        int[] iArr = new int[3];
        boolean z2 = false;
        int i2 = 0;
        while (wVar.a() != 8 && (iA = wVar.a(8)) != 254) {
            a(iA, wVar.a(8), iArr);
            for (int i3 = 0; i3 < 3; i3++) {
                int[] iArrA = a(sb, i2, iArr[i3], z2);
                i2 = iArrA[0];
                z2 = iArrA[1] == 1;
            }
            if (wVar.a() <= 0) {
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int[] b(StringBuilder sb, int i2, int i3, boolean z2) throws com.huawei.hms.scankit.p.a {
        int i4;
        boolean z3;
        if (i2 != 0) {
            if (i2 == 1) {
                if (z2) {
                    i3 += 128;
                }
                sb.append((char) i3);
            } else if (i2 == 2) {
                char[] cArr = f17612b;
                if (i3 < cArr.length) {
                    char c2 = cArr[i3];
                    if (z2) {
                        c2 = (char) (c2 + 128);
                    }
                    sb.append(c2);
                    z3 = false;
                } else if (i3 == 27) {
                    sb.append((char) 29);
                    z3 = z2;
                } else {
                    if (i3 != 30) {
                        throw com.huawei.hms.scankit.p.a.a("AIScanException");
                    }
                    z3 = true;
                }
                i2 = 0;
                i4 = z3;
            } else if (i2 == 3) {
                sb.append((char) (z2 ? i3 + 224 : i3 + 96));
            } else {
                throw com.huawei.hms.scankit.p.a.a("AIScanException");
            }
            i2 = 0;
            i4 = 0;
        } else if (i3 < 3) {
            i2 = i3 + 1;
            i4 = z2;
        } else {
            char[] cArr2 = f17611a;
            if (i3 < cArr2.length) {
                char c3 = cArr2[i3];
                if (z2) {
                    c3 = (char) (c3 + 128);
                }
                sb.append(c3);
                i4 = 0;
            } else {
                throw com.huawei.hms.scankit.p.a.a("AIScanException");
            }
        }
        return new int[]{i2, i4};
    }

    private static b a(w wVar, StringBuilder sb, StringBuilder sb2) throws com.huawei.hms.scankit.p.a {
        boolean z2 = false;
        do {
            int iA = wVar.a(8);
            if (iA == 0) {
                throw com.huawei.hms.scankit.p.a.a("AIScanException");
            }
            if (iA <= 128) {
                if (z2) {
                    iA += 128;
                }
                sb.append((char) (iA - 1));
                return b.ASCII_ENCODE;
            }
            if (iA == 129) {
                return b.PAD_ENCODE;
            }
            if (iA <= 229) {
                int i2 = iA - 130;
                if (i2 < 10) {
                    sb.append('0');
                }
                sb.append(i2);
            } else {
                b bVarA = a(iA, sb, sb2, wVar);
                if (bVarA != null) {
                    if (bVarA != b.UPPER_ENCODE) {
                        return bVarA;
                    }
                    z2 = true;
                }
            }
        } while (wVar.a() > 0);
        return b.ASCII_ENCODE;
    }

    private static b a(int i2, StringBuilder sb, StringBuilder sb2, w wVar) throws com.huawei.hms.scankit.p.a {
        switch (i2) {
            case 230:
                return b.C40_ENCODE;
            case 231:
                return b.BASE256_ENCODE;
            case 232:
                sb.append((char) 29);
                return null;
            case 233:
            case 234:
            case 241:
                return null;
            case 235:
                return b.UPPER_ENCODE;
            case 236:
                sb.append("[)>\u001e05\u001d");
                sb2.insert(0, "\u001e\u0004");
                return null;
            case 237:
                sb.append("[)>\u001e06\u001d");
                sb2.insert(0, "\u001e\u0004");
                return null;
            case 238:
                return b.ANSIX12_ENCODE;
            case 239:
                return b.TEXT_ENCODE;
            case 240:
                return b.EDIFACT_ENCODE;
            default:
                if (i2 == 254 && wVar.a() == 0) {
                    return null;
                }
                throw com.huawei.hms.scankit.p.a.a("AIScanException");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int[] a(StringBuilder sb, int i2, int i3, boolean z2) throws com.huawei.hms.scankit.p.a {
        int i4;
        boolean z3;
        if (i2 != 0) {
            if (i2 == 1) {
                if (z2) {
                    i3 = (char) (i3 + 128);
                }
                sb.append(i3);
            } else if (i2 == 2) {
                char[] cArr = f17614d;
                if (i3 < cArr.length) {
                    char c2 = cArr[i3];
                    if (z2) {
                        c2 = (char) (c2 + 128);
                    }
                    sb.append(c2);
                    z3 = false;
                } else if (i3 == 27) {
                    sb.append((char) 29);
                    z3 = z2;
                } else {
                    if (i3 != 30) {
                        throw com.huawei.hms.scankit.p.a.a("AIScanException");
                    }
                    z3 = true;
                }
                i2 = 0;
                i4 = z3;
            } else if (i2 == 3) {
                char[] cArr2 = f17615e;
                if (i3 < cArr2.length) {
                    char c3 = cArr2[i3];
                    if (z2) {
                        c3 = (char) (c3 + 128);
                    }
                    sb.append(c3);
                } else {
                    throw com.huawei.hms.scankit.p.a.a("AIScanException");
                }
            } else {
                throw com.huawei.hms.scankit.p.a.a("AIScanException");
            }
            i2 = 0;
            i4 = 0;
        } else if (i3 < 3) {
            i2 = i3 + 1;
            i4 = z2;
        } else {
            char[] cArr3 = f17613c;
            if (i3 < cArr3.length) {
                char c4 = cArr3[i3];
                if (z2) {
                    c4 = (char) (c4 + 128);
                }
                sb.append(c4);
                i4 = 0;
            } else {
                throw com.huawei.hms.scankit.p.a.a("AIScanException");
            }
        }
        return new int[]{i2, i4};
    }

    private static void a(w wVar, StringBuilder sb) throws com.huawei.hms.scankit.p.a {
        int iA;
        int[] iArr = new int[3];
        while (wVar.a() != 8 && (iA = wVar.a(8)) != 254) {
            a(iA, wVar.a(8), iArr);
            for (int i2 = 0; i2 < 3; i2++) {
                int i3 = iArr[i2];
                if (i3 == 0) {
                    sb.append(CharUtils.CR);
                } else if (i3 == 1) {
                    sb.append('*');
                } else if (i3 == 2) {
                    sb.append(Typography.greater);
                } else if (i3 == 3) {
                    sb.append(' ');
                } else if (i3 < 14) {
                    sb.append((char) (i3 + 44));
                } else if (i3 < 40) {
                    sb.append((char) (i3 + 51));
                } else {
                    throw com.huawei.hms.scankit.p.a.a("AIScanException");
                }
            }
            if (wVar.a() <= 0) {
                return;
            }
        }
    }

    private static void a(int i2, int i3, int[] iArr) {
        int i4 = ((i2 << 8) + i3) - 1;
        int i5 = i4 / 1600;
        iArr[0] = i5;
        int i6 = i4 - (i5 * 1600);
        int i7 = i6 / 40;
        iArr[1] = i7;
        iArr[2] = i6 - (i7 * 40);
    }

    private static void a(w wVar, StringBuilder sb, Collection<byte[]> collection) throws com.huawei.hms.scankit.p.a {
        int iC = wVar.c();
        int i2 = iC + 2;
        int iA = a(wVar.a(8), iC + 1);
        if (iA == 0) {
            iA = wVar.a() / 8;
        } else if (iA >= 250) {
            iA = ((iA - 249) * 250) + a(wVar.a(8), i2);
            i2 = iC + 3;
        }
        if (iA >= 0) {
            byte[] bArr = new byte[iA];
            int i3 = 0;
            while (i3 < iA) {
                if (wVar.a() >= 8) {
                    bArr[i3] = (byte) a(wVar.a(8), i2);
                    i3++;
                    i2++;
                } else {
                    throw com.huawei.hms.scankit.p.a.a("AIScanException");
                }
            }
            collection.add(bArr);
            try {
                sb.append(new String(bArr, "ISO8859_1"));
                return;
            } catch (UnsupportedEncodingException e2) {
                throw new IllegalStateException("Platform does not support required encoding: " + e2);
            }
        }
        throw com.huawei.hms.scankit.p.a.a("AIScanException");
    }

    private static int a(int i2, int i3) {
        int i4 = i2 - (((i3 * 149) % 255) + 1);
        return i4 >= 0 ? i4 : i4 + 256;
    }
}
