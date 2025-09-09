package com.aliyun.alink.business.devicecenter.track;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class DCUserTrack {

    /* renamed from: a, reason: collision with root package name */
    public static Map<String, String> f10624a = new ConcurrentHashMap();

    public static HashMap<String, String> a() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(f10624a);
        return map;
    }

    public static void addTrackData(String... strArr) {
        String str;
        if (strArr == null || strArr.length < 2 || strArr.length % 2 == 1) {
            return;
        }
        for (int i2 = 0; i2 < strArr.length / 2; i2 += 2) {
            String str2 = strArr[i2];
            if (str2 != null && (str = strArr[i2 + 1]) != null) {
                f10624a.put(str2, str);
            }
        }
    }

    public static boolean hasKey(String str) {
        Map<String, String> map = f10624a;
        return map != null && map.containsKey(str);
    }

    public static void removeTrackData(String str) {
        Map<String, String> map;
        if (TextUtils.isEmpty(str) || (map = f10624a) == null) {
            return;
        }
        map.remove(str);
    }

    public static void resetTrackData() {
        Map<String, String> map = f10624a;
        if (map != null) {
            map.clear();
        }
    }

    public static void sendEvent() {
        ALog.d("DCUserTrack", "sendEvent() called track data = " + f10624a);
        if (!f10624a.containsKey(AlinkConstants.KEY_PROVISION_STARTED)) {
            ALog.w("DCUserTrack", "sendEvent provision not started, do not send event.");
            resetTrackData();
        } else {
            removeTrackData(AlinkConstants.KEY_PROVISION_STARTED);
            AUserTrack.record(AlinkConstants.KEY_DC_PROVISION, a());
            resetTrackData();
        }
    }

    public static void sendEvent(String str) {
        ALog.d("DCUserTrack", "sendEvent() called with: event = [" + str + "], trackData = [" + f10624a + "]");
        AUserTrack.record(AlinkConstants.KEY_DC_PROVISION_DISCOVER, a());
    }
}
