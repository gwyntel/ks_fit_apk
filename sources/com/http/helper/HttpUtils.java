package com.http.helper;

import androidx.browser.trusted.sharing.ShareTarget;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class HttpUtils {
    public static HashMap<String, String> getDefaultRequestHeader() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Content-Type", ShareTarget.ENCODING_TYPE_URL_ENCODED);
        return map;
    }

    public static HashMap<String, String> getParamMap(String... strArr) {
        if (strArr == null) {
            return null;
        }
        int length = strArr.length;
        HashMap<String, String> map = new HashMap<>();
        for (int i2 = 0; i2 < length; i2 += 2) {
            int i3 = i2 + 1;
            if (i3 >= length) {
                break;
            }
            if (!isEmpty(strArr[i2])) {
                map.put(strArr[i2], strArr[i3]);
            }
        }
        return map;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private static String makeUA() {
        return System.getProperty("os.version", "JavaUtil UA");
    }
}
