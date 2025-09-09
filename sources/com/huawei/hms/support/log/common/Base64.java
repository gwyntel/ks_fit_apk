package com.huawei.hms.support.log.common;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.alipay.sdk.m.n.a;
import com.google.common.base.Ascii;
import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
public final class Base64 {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f18148a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', IOUtils.DIR_SEPARATOR_UNIX, a.f9565h};

    /* renamed from: b, reason: collision with root package name */
    private static final byte[] f18149b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 58, 59, Constants.CMD_TYPE.CMD_BUSINESS_DOWNSTREAM, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM, -1, -1, -1, -1, -1, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_REQUEST_OTA, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, Constants.CMD_TYPE.CMD_OTA_RES, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 39, 40, 41, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, 43, 44, 45, 46, Constants.CMD_TYPE.CMD_OTA, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    private Base64() {
    }

    private static int a(String str) {
        int length = str.length();
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt > 255 || f18149b[cCharAt] < 0) {
                length--;
            }
        }
        return length;
    }

    public static byte[] decode(String str) {
        int iA = a(str);
        int i2 = (iA / 4) * 3;
        int i3 = iA % 4;
        if (i3 == 3) {
            i2 += 2;
        }
        if (i3 == 2) {
            i2++;
        }
        byte[] bArr = new byte[i2];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < str.length(); i7++) {
            char cCharAt = str.charAt(i7);
            byte b2 = cCharAt > 255 ? (byte) -1 : f18149b[cCharAt];
            if (b2 >= 0) {
                int i8 = i6 + 6;
                i5 = (i5 << 6) | b2;
                if (i8 >= 8) {
                    i6 -= 2;
                    bArr[i4] = (byte) (255 & (i5 >> i6));
                    i4++;
                } else {
                    i6 = i8;
                }
            }
        }
        return i4 != i2 ? new byte[0] : bArr;
    }

    public static String encode(byte[] bArr) {
        return encode(bArr, bArr.length);
    }

    public static String encode(byte[] bArr, int i2) {
        boolean z2;
        char[] cArr = new char[((i2 + 2) / 3) * 4];
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = (bArr[i3] & 255) << 8;
            int i6 = i3 + 1;
            boolean z3 = true;
            if (i6 < i2) {
                i5 |= bArr[i6] & 255;
                z2 = true;
            } else {
                z2 = false;
            }
            int i7 = i5 << 8;
            int i8 = i3 + 2;
            if (i8 < i2) {
                i7 |= bArr[i8] & 255;
            } else {
                z3 = false;
            }
            int i9 = i4 + 3;
            char[] cArr2 = f18148a;
            int i10 = 64;
            cArr[i9] = cArr2[z3 ? i7 & 63 : 64];
            int i11 = i7 >> 6;
            int i12 = i4 + 2;
            if (z2) {
                i10 = i11 & 63;
            }
            cArr[i12] = cArr2[i10];
            cArr[i4 + 1] = cArr2[(i7 >> 12) & 63];
            cArr[i4] = cArr2[(i7 >> 18) & 63];
            i3 += 3;
            i4 += 4;
        }
        return new String(cArr);
    }
}
