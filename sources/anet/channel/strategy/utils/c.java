package anet.channel.strategy.utils;

import android.text.TextUtils;
import androidx.media3.common.C;
import anet.channel.util.ALog;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class c {
    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length < 7 || charArray.length > 15) {
            return false;
        }
        int i2 = 0;
        int i3 = 0;
        for (char c2 : charArray) {
            if (c2 >= '0' && c2 <= '9') {
                i3 = ((i3 * 10) + c2) - 48;
                if (i3 > 255) {
                    return false;
                }
            } else {
                if (c2 != '.' || (i2 = i2 + 1) > 3) {
                    return false;
                }
                i3 = 0;
            }
        }
        return true;
    }

    public static boolean b(String str) {
        int i2;
        boolean z2;
        int i3;
        int i4;
        boolean z3;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length < 2) {
            return false;
        }
        if (charArray[0] != ':') {
            i2 = 0;
            z2 = false;
            i3 = 0;
            i4 = 0;
            z3 = true;
        } else {
            if (charArray[1] != ':') {
                return false;
            }
            z2 = false;
            i4 = 0;
            i2 = 1;
            i3 = 1;
            z3 = true;
        }
        while (i2 < charArray.length) {
            char c2 = charArray[i2];
            int iDigit = Character.digit(c2, 16);
            if (iDigit != -1) {
                i4 = (i4 << 4) + iDigit;
                if (i4 > 65535) {
                    return false;
                }
                z3 = false;
            } else {
                if (c2 != ':' || (i3 = i3 + 1) > 7) {
                    return false;
                }
                if (!z3) {
                    i4 = 0;
                    z3 = true;
                } else {
                    if (z2) {
                        return false;
                    }
                    z2 = true;
                }
            }
            i2++;
        }
        return z2 || i3 >= 7;
    }

    public static boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length <= 0 || charArray.length > 255) {
            return false;
        }
        boolean z2 = false;
        for (char c2 : charArray) {
            if ((c2 >= 'A' && c2 <= 'Z') || ((c2 >= 'a' && c2 <= 'z') || c2 == '*')) {
                z2 = true;
            } else if ((c2 < '0' || c2 > '9') && c2 != '.' && c2 != '-') {
                return false;
            }
        }
        return z2;
    }

    public static String d(String str) {
        return str == null ? "" : str;
    }

    public static String a(long j2) {
        StringBuilder sb = new StringBuilder(16);
        long j3 = C.NANOS_PER_SECOND;
        do {
            sb.append(j2 / j3);
            sb.append('.');
            j2 %= j3;
            j3 /= 1000;
        } while (j3 > 0);
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static String a(Map<String, String> map, String str) {
        if (map != null && !map.isEmpty()) {
            StringBuilder sb = new StringBuilder(64);
            try {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (entry.getKey() != null) {
                        sb.append(URLEncoder.encode(entry.getKey(), str));
                        sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                        sb.append(URLEncoder.encode(d(entry.getValue()), str).replace(MqttTopic.SINGLE_LEVEL_WILDCARD, "%20"));
                        sb.append("&");
                    }
                }
                sb.deleteCharAt(sb.length() - 1);
            } catch (UnsupportedEncodingException e2) {
                ALog.e("Request", "format params failed", null, e2, new Object[0]);
            }
            return sb.toString();
        }
        return "";
    }
}
