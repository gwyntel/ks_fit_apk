package com.huawei.hms.scankit.p;

import androidx.exifinterface.media.ExifInterface;
import androidx.webkit.ProxyConfig;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.huawei.hms.framework.common.ContainerUtils;
import com.umeng.analytics.pro.bc;
import com.xiaomi.mipush.sdk.Constants;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import org.android.agoo.message.MessageService;
import org.apache.commons.io.IOUtils;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public final class s1 {

    /* renamed from: b, reason: collision with root package name */
    private static final String[] f17751b = {"CTRL_PS", " ", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D", ExifInterface.LONGITUDE_EAST, "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS"};

    /* renamed from: c, reason: collision with root package name */
    private static final String[] f17752c = {"CTRL_PS", " ", "a", "b", bc.aL, "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", bc.aN, "v", "w", "x", "y", bc.aJ, "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS"};

    /* renamed from: d, reason: collision with root package name */
    private static final String[] f17753d = {"CTRL_PS", " ", "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b", "\t", "\n", "\u000b", "\f", "\r", "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@", "\\", "^", OpenAccountUIConstants.UNDER_LINE, "`", "|", Constants.WAVE_SEPARATOR, "\u007f", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"};

    /* renamed from: e, reason: collision with root package name */
    private static final String[] f17754e = {"", "\r", IOUtils.LINE_SEPARATOR_WINDOWS, ". ", ", ", ": ", "!", "\"", MqttTopic.MULTI_LEVEL_WILDCARD, "$", "%", "&", "'", "(", ")", ProxyConfig.MATCH_ALL_SCHEMES, MqttTopic.SINGLE_LEVEL_WILDCARD, ",", Constants.ACCEPT_TIME_SEPARATOR_SERVER, ".", "/", ":", com.alipay.sdk.m.u.i.f9802b, "<", ContainerUtils.KEY_VALUE_DELIMITER, ">", "?", "[", "]", "{", com.alipay.sdk.m.u.i.f9804d, "CTRL_UL"};

    /* renamed from: f, reason: collision with root package name */
    private static final String[] f17755f = {"CTRL_PS", " ", "0", "1", "2", "3", "4", AlcsPalConst.MODEL_TYPE_TGMESH, "6", "7", MessageService.MSG_ACCS_NOTIFY_CLICK, MessageService.MSG_ACCS_NOTIFY_DISMISS, ",", ".", "CTRL_UL", "CTRL_US"};

    /* renamed from: a, reason: collision with root package name */
    private g f17756a;

    static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f17757a;

        static {
            int[] iArr = new int[b.values().length];
            f17757a = iArr;
            try {
                iArr[b.UPPER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f17757a[b.LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f17757a[b.MIXED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f17757a[b.PUNCT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f17757a[b.DIGIT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private enum b {
        UPPER,
        LOWER,
        MIXED,
        DIGIT,
        PUNCT,
        BINARY
    }

    private static int a(int i2, boolean z2) {
        return ((z2 ? 88 : 112) + (i2 * 16)) * i2;
    }

    private boolean[] b(boolean[] zArr) throws com.huawei.hms.scankit.p.a {
        int i2;
        o3 o3Var;
        g gVar = this.f17756a;
        if (gVar == null) {
            throw com.huawei.hms.scankit.p.a.a();
        }
        if (gVar.f() <= 2) {
            o3Var = o3.f17631j;
            i2 = 6;
        } else {
            i2 = 8;
            if (this.f17756a.f() <= 8) {
                o3Var = o3.f17635n;
            } else if (this.f17756a.f() <= 22) {
                o3Var = o3.f17630i;
                i2 = 10;
            } else {
                o3Var = o3.f17629h;
                i2 = 12;
            }
        }
        int iE = this.f17756a.e();
        int length = zArr.length / i2;
        if (length < iE) {
            throw com.huawei.hms.scankit.p.a.a();
        }
        int length2 = zArr.length % i2;
        int[] iArr = new int[length];
        int i3 = 0;
        while (i3 < length) {
            iArr[i3] = a(zArr, length2, i2);
            i3++;
            length2 += i2;
        }
        try {
            new p6(o3Var).a(iArr, length - iE);
            return a(iE, i2, iArr);
        } catch (com.huawei.hms.scankit.p.a e2) {
            throw com.huawei.hms.scankit.p.a.a(e2.getMessage());
        }
    }

    public w1 a(g gVar, Map<l1, ?> map) throws com.huawei.hms.scankit.p.a {
        this.f17756a = gVar;
        boolean[] zArrB = b(a(gVar.a()));
        w1 w1Var = new w1(a(zArrB), a(zArrB, map), null, null);
        w1Var.a(zArrB.length);
        return w1Var;
    }

    private static String a(boolean[] zArr, Map<l1, ?> map) throws com.huawei.hms.scankit.p.a {
        b bVar = b.UPPER;
        StringBuilder sbA = a(zArr, bVar, bVar);
        int length = sbA.length();
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2] = (byte) sbA.charAt(i2);
        }
        try {
            return new String(bArr, c7.a(bArr, map));
        } catch (UnsupportedEncodingException unused) {
            throw com.huawei.hms.scankit.p.a.a();
        }
    }

    private static StringBuilder a(boolean[] zArr, b bVar, b bVar2) {
        int length = zArr.length;
        StringBuilder sb = new StringBuilder(20);
        int i2 = 0;
        while (i2 < length) {
            if (bVar2 != b.BINARY) {
                int i3 = bVar2 == b.DIGIT ? 4 : 5;
                if (length - i2 < i3) {
                    break;
                }
                int iA = a(zArr, i2, i3);
                i2 += i3;
                String strA = a(bVar2, iA);
                if (strA.startsWith("CTRL_")) {
                    bVar = a(strA.charAt(5));
                    if (strA.charAt(6) != 'L') {
                        b bVar3 = bVar2;
                        bVar2 = bVar;
                        bVar = bVar3;
                    }
                } else {
                    sb.append(strA);
                }
                bVar2 = bVar;
            } else {
                if (length - i2 < 5) {
                    break;
                }
                int iA2 = a(zArr, i2, 5);
                int i4 = i2 + 5;
                if (iA2 == 0) {
                    if (length - i4 < 11) {
                        break;
                    }
                    iA2 = a(zArr, i4, 11) + 31;
                    i4 = i2 + 16;
                }
                int i5 = 0;
                while (true) {
                    if (i5 >= iA2) {
                        i2 = i4;
                        break;
                    }
                    if (length - i4 < 8) {
                        i2 = length;
                        break;
                    }
                    sb.append((char) a(zArr, i4, 8));
                    i4 += 8;
                    i5++;
                }
                bVar2 = bVar;
            }
        }
        return sb;
    }

    private static b a(char c2) {
        if (c2 == 'B') {
            return b.BINARY;
        }
        if (c2 == 'D') {
            return b.DIGIT;
        }
        if (c2 == 'P') {
            return b.PUNCT;
        }
        if (c2 == 'L') {
            return b.LOWER;
        }
        if (c2 != 'M') {
            return b.UPPER;
        }
        return b.MIXED;
    }

    private static String a(b bVar, int i2) {
        int i3 = a.f17757a[bVar.ordinal()];
        if (i3 == 1) {
            return f17751b[i2];
        }
        if (i3 == 2) {
            return f17752c[i2];
        }
        if (i3 == 3) {
            return f17753d[i2];
        }
        if (i3 == 4) {
            return f17754e[i2];
        }
        if (i3 == 5) {
            return f17755f[i2];
        }
        throw new IllegalStateException("Bad table");
    }

    private boolean[] a(int i2, int i3, int[] iArr) throws com.huawei.hms.scankit.p.a {
        int i4 = 1 << i3;
        int i5 = i4 - 1;
        int i6 = 0;
        for (int i7 = 0; i7 < i2; i7++) {
            int i8 = iArr[i7];
            if (i8 == 0 || i8 == i5) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            if (i8 == 1 || i8 == i4 - 2) {
                i6++;
            }
        }
        boolean[] zArr = new boolean[(i2 * i3) - i6];
        int i9 = 0;
        for (int i10 = 0; i10 < i2; i10++) {
            int i11 = iArr[i10];
            if (i11 == 1 || i11 == i4 - 2) {
                Arrays.fill(zArr, i9, (i9 + i3) - 1, i11 > 1);
                i9 += i3 - 1;
            } else {
                int i12 = i3 - 1;
                while (i12 >= 0) {
                    int i13 = i9 + 1;
                    zArr[i9] = ((1 << i12) & i11) != 0;
                    i12--;
                    i9 = i13;
                }
            }
        }
        return zArr;
    }

    private boolean[] a(s sVar) {
        g gVar = this.f17756a;
        boolean z2 = gVar != null && gVar.g();
        g gVar2 = this.f17756a;
        int iF = gVar2 != null ? gVar2.f() : 0;
        int i2 = (z2 ? 11 : 14) + (iF * 4);
        int[] iArr = new int[i2];
        boolean[] zArr = new boolean[a(iF, z2)];
        int i3 = 2;
        if (z2) {
            for (int i4 = 0; i4 < i2; i4++) {
                iArr[i4] = i4;
            }
        } else {
            int i5 = i2 / 2;
            int i6 = ((i2 + 1) + (((i5 - 1) / 15) * 2)) / 2;
            for (int i7 = 0; i7 < i5; i7++) {
                int i8 = (i7 / 15) + i7;
                iArr[(i5 - i7) - 1] = (i6 - i8) - 1;
                iArr[i5 + i7] = i8 + i6 + 1;
            }
        }
        int i9 = 0;
        int i10 = 0;
        while (i9 < iF) {
            int i11 = ((iF - i9) * 4) + (z2 ? 9 : 12);
            int i12 = i9 * 2;
            int i13 = (i2 - 1) - i12;
            int i14 = 0;
            while (i14 < i11) {
                int i15 = i14 * 2;
                int i16 = 0;
                while (i16 < i3) {
                    int i17 = i12 + i16;
                    int i18 = i12 + i14;
                    zArr[i10 + i15 + i16] = sVar.b(iArr[i17], iArr[i18]);
                    int i19 = i13 - i16;
                    zArr[(i11 * 2) + i10 + i15 + i16] = sVar.b(iArr[i18], iArr[i19]);
                    int i20 = i13 - i14;
                    zArr[(i11 * 4) + i10 + i15 + i16] = sVar.b(iArr[i19], iArr[i20]);
                    zArr[(i11 * 6) + i10 + i15 + i16] = sVar.b(iArr[i20], iArr[i17]);
                    i16++;
                    z2 = z2;
                    i3 = 2;
                }
                i14++;
                i3 = 2;
            }
            i10 += i11 * 8;
            i9++;
            i3 = 2;
        }
        return zArr;
    }

    private static int a(boolean[] zArr, int i2, int i3) {
        int i4 = 0;
        for (int i5 = i2; i5 < i2 + i3; i5++) {
            i4 <<= 1;
            if (zArr[i5]) {
                i4 |= 1;
            }
        }
        return i4;
    }

    private static byte a(boolean[] zArr, int i2) {
        int iA;
        int length = zArr.length - i2;
        if (length >= 8) {
            iA = a(zArr, i2, 8);
        } else {
            iA = a(zArr, i2, length) << (8 - length);
        }
        return (byte) iA;
    }

    static byte[] a(boolean[] zArr) {
        int length = (zArr.length + 7) / 8;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2] = a(zArr, i2 * 8);
        }
        return bArr;
    }
}
