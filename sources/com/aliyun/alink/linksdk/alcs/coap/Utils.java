package com.aliyun.alink.linksdk.alcs.coap;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes2.dex */
public class Utils {
    private Utils() {
    }

    public static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            for (byte b2 : bArr) {
                sb.append(String.format("%02x", Integer.valueOf(b2 & 255)));
            }
        }
        return sb.toString();
    }

    public static String toHexText(byte[] bArr, int i2) {
        if (bArr == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (i2 > bArr.length) {
            i2 = bArr.length;
        }
        StringBuilder sb = new StringBuilder();
        if (16 < i2) {
            sb.append(System.lineSeparator());
        }
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(String.format("%02x", Integer.valueOf(bArr[i3] & 255)));
            if (31 == (i3 & 31)) {
                sb.append(System.lineSeparator());
            } else {
                sb.append(' ');
            }
        }
        if (i2 < bArr.length) {
            sb.append(" .. ");
            sb.append(bArr.length);
            sb.append(" bytes");
        }
        return sb.toString();
    }
}
