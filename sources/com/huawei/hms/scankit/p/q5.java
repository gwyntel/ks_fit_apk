package com.huawei.hms.scankit.p;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.huawei.hms.hmsscankit.WriterException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* loaded from: classes4.dex */
final class q5 {

    /* renamed from: c, reason: collision with root package name */
    private static final byte[] f17695c;

    /* renamed from: a, reason: collision with root package name */
    private static final byte[] f17693a = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 13, 9, 44, 58, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, 45, 46, Constants.CMD_TYPE.CMD_OTA_RES, Constants.CMD_TYPE.CMD_OTA, 43, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, 61, 94, 0, 32, 0, 0, 0};

    /* renamed from: b, reason: collision with root package name */
    private static final byte[] f17694b = {59, Constants.CMD_TYPE.CMD_BUSINESS_DOWNSTREAM, 62, 64, 91, 92, 93, 95, 96, 126, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, 13, 9, 44, 58, 10, 45, 46, Constants.CMD_TYPE.CMD_OTA_RES, Constants.CMD_TYPE.CMD_OTA, Constants.CMD_TYPE.CMD_REQUEST_OTA, 124, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, 40, 41, 63, 123, 125, 39, 0};

    /* renamed from: d, reason: collision with root package name */
    private static final byte[] f17696d = new byte[128];

    /* renamed from: e, reason: collision with root package name */
    private static final Charset f17697e = StandardCharsets.ISO_8859_1;

    static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f17698a;

        static {
            int[] iArr = new int[y0.values().length];
            f17698a = iArr;
            try {
                iArr[y0.TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f17698a[y0.BYTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f17698a[y0.NUMERIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static {
        int i2 = 0;
        byte[] bArr = new byte[128];
        f17695c = bArr;
        Arrays.fill(bArr, (byte) -1);
        int i3 = 0;
        while (true) {
            byte[] bArr2 = f17693a;
            if (i3 >= bArr2.length) {
                break;
            }
            byte b2 = bArr2[i3];
            if (b2 > 0) {
                f17695c[b2] = (byte) i3;
            }
            i3++;
        }
        Arrays.fill(f17696d, (byte) -1);
        while (true) {
            byte[] bArr3 = f17694b;
            if (i2 >= bArr3.length) {
                return;
            }
            byte b3 = bArr3[i2];
            if (b3 > 0) {
                f17696d[b3] = (byte) i2;
            }
            i2++;
        }
    }

    private static boolean a(char c2) {
        return c2 == ' ' || (c2 >= 'a' && c2 <= 'z');
    }

    private static boolean b(char c2) {
        return c2 == ' ' || (c2 >= 'A' && c2 <= 'Z');
    }

    private static boolean c(char c2) {
        return c2 >= '0' && c2 <= '9';
    }

    private static boolean d(char c2) {
        byte[] bArr = f17695c;
        if (w7.a(bArr, (int) c2)) {
            return bArr[c2] != -1;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private static boolean e(char c2) {
        if (w7.a(f17695c, (int) c2)) {
            return f17696d[c2] != -1;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private static boolean f(char c2) {
        return c2 == '\t' || c2 == '\n' || c2 == '\r' || (c2 >= ' ' && c2 <= '~');
    }

    static String a(String str, y0 y0Var, Charset charset) throws WriterException {
        StringBuilder sb = new StringBuilder(str.length());
        if (charset == null) {
            charset = f17697e;
        } else if (f17697e.equals(charset)) {
            o4.a("PDF417", "else");
        } else {
            o0 o0VarA = o0.a(charset.name());
            if (o0VarA != null) {
                a(o0VarA.a(), sb);
            }
        }
        int length = str.length();
        int i2 = a.f17698a[y0Var.ordinal()];
        if (i2 == 1) {
            a(str, 0, length, sb, 0);
        } else if (i2 == 2) {
            byte[] bytes = str.getBytes(charset);
            a(bytes, 0, bytes.length, 1, sb);
        } else if (i2 != 3) {
            int i3 = 0;
            int i4 = 0;
            int iA = 0;
            while (i3 < length) {
                int iA2 = a(str, i3);
                if (iA2 >= 13) {
                    sb.append((char) 902);
                    a(str, i3, iA2, sb);
                    i3 += iA2;
                    iA = 0;
                    i4 = 2;
                } else {
                    int iB = b(str, i3);
                    if (iB >= 5 || iA2 == length) {
                        if (i4 != 0) {
                            sb.append((char) 900);
                            i4 = 0;
                            iA = 0;
                        }
                        iA = a(str, i3, iB, sb, iA);
                        i3 += iB;
                    } else {
                        int iA3 = a(str, i3, charset);
                        if (iA3 == 0) {
                            iA3 = 1;
                        }
                        int i5 = iA3 + i3;
                        byte[] bytes2 = str.substring(i3, i5).getBytes(charset);
                        if (bytes2.length == 1 && i4 == 0) {
                            a(bytes2, 0, 1, 0, sb);
                        } else {
                            a(bytes2, 0, bytes2.length, i4, sb);
                            i4 = 1;
                            iA = 0;
                        }
                        i3 = i5;
                    }
                }
            }
        } else {
            sb.append((char) 902);
            a(str, 0, length, sb);
        }
        return sb.toString();
    }

    private static int b(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        int i3 = i2;
        while (i3 < length) {
            char cCharAt = charSequence.charAt(i3);
            int i4 = 0;
            while (i4 < 13 && c(cCharAt) && i3 < length) {
                i4++;
                i3++;
                if (i3 < length) {
                    cCharAt = charSequence.charAt(i3);
                }
            }
            if (i4 >= 13) {
                return (i3 - i2) - i4;
            }
            if (i4 <= 0) {
                if (!f(charSequence.charAt(i3))) {
                    break;
                }
                i3++;
            }
        }
        return i3 - i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x00f4 A[EDGE_INSN: B:73:0x00f4->B:55:0x00f4 BREAK  A[LOOP:0: B:3:0x000f->B:90:0x000f], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x000f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int a(java.lang.CharSequence r16, int r17, int r18, java.lang.StringBuilder r19, int r20) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.q5.a(java.lang.CharSequence, int, int, java.lang.StringBuilder, int):int");
    }

    private static void a(byte[] bArr, int i2, int i3, int i4, StringBuilder sb) {
        int i5;
        if (i3 == 1 && i4 == 0) {
            sb.append((char) 913);
        } else if (i3 % 6 == 0) {
            sb.append((char) 924);
        } else {
            sb.append((char) 901);
        }
        if (i3 >= 6) {
            char[] cArr = new char[5];
            i5 = i2;
            while ((i2 + i3) - i5 >= 6) {
                long j2 = 0;
                for (int i6 = 0; i6 < 6; i6++) {
                    j2 = (j2 << 8) + (bArr[i5 + i6] & 255);
                }
                for (int i7 = 0; i7 < 5; i7++) {
                    cArr[i7] = (char) (j2 % 900);
                    j2 /= 900;
                }
                for (int i8 = 4; i8 >= 0; i8--) {
                    sb.append(cArr[i8]);
                }
                i5 += 6;
            }
        } else {
            i5 = i2;
        }
        while (i5 < i2 + i3) {
            sb.append((char) (bArr[i5] & 255));
            i5++;
        }
    }

    private static void a(String str, int i2, int i3, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder((i3 / 3) + 1);
        BigInteger bigIntegerValueOf = BigInteger.valueOf(900L);
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(0L);
        int i4 = 0;
        while (i4 < i3) {
            sb2.setLength(0);
            int iMin = Math.min(44, i3 - i4);
            if (str.length() > 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append('1');
                int i5 = i2 + i4;
                sb3.append(str.substring(i5, i5 + iMin));
                BigInteger bigInteger = new BigInteger(sb3.toString());
                do {
                    sb2.append((char) bigInteger.mod(bigIntegerValueOf).intValue());
                    bigInteger = bigInteger.divide(bigIntegerValueOf);
                } while (!bigInteger.equals(bigIntegerValueOf2));
            }
            for (int length = sb2.length() - 1; length >= 0; length--) {
                sb.append(sb2.charAt(length));
            }
            i4 += iMin;
        }
    }

    private static int a(CharSequence charSequence, int i2) {
        return d4.a(charSequence, i2);
    }

    private static int a(String str, int i2, Charset charset) throws WriterException {
        int i3;
        CharsetEncoder charsetEncoderNewEncoder = charset.newEncoder();
        int length = str.length();
        int i4 = i2;
        while (i4 < length) {
            char cCharAt = str.charAt(i4);
            int i5 = 0;
            while (i5 < 13 && c(cCharAt) && (i3 = i4 + (i5 = i5 + 1)) < length) {
                cCharAt = str.charAt(i3);
            }
            if (i5 >= 13) {
                return i4 - i2;
            }
            char cCharAt2 = str.charAt(i4);
            if (!charsetEncoderNewEncoder.canEncode(cCharAt2)) {
                throw new WriterException("Non-encodable character detected: " + cCharAt2 + " (Unicode: " + ((int) cCharAt2) + ')');
            }
            i4++;
        }
        return i4 - i2;
    }

    private static void a(int i2, StringBuilder sb) throws WriterException {
        if (i2 >= 0 && i2 < 900) {
            sb.append((char) 927);
            sb.append((char) i2);
            return;
        }
        if (i2 < 810900) {
            sb.append((char) 926);
            sb.append((char) ((i2 / TypedValues.Custom.TYPE_INT) - 1));
            sb.append((char) (i2 % TypedValues.Custom.TYPE_INT));
        } else if (i2 < 811800) {
            sb.append((char) 925);
            sb.append((char) (810900 - i2));
        } else {
            throw new WriterException("ECI number not in valid range from 0..811799, but was " + i2);
        }
    }
}
