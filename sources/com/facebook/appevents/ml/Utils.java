package com.facebook.appevents.ml;

import android.text.TextUtils;
import java.nio.charset.Charset;

/* loaded from: classes3.dex */
public class Utils {
    static String normalizeString(String str) {
        return TextUtils.join(" ", str.trim().split("\\s+"));
    }

    static int[] vectorize(String str, int i2) {
        int[] iArr = new int[i2];
        byte[] bytes = normalizeString(str).getBytes(Charset.forName("UTF-8"));
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 < bytes.length) {
                iArr[i3] = bytes[i3] & 255;
            } else {
                iArr[i3] = 0;
            }
        }
        return iArr;
    }
}
