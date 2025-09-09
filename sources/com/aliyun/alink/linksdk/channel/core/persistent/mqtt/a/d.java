package com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a;

import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class d {
    public static void a(String str, Map<String, String> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("TrackUtils", "sendEvent() called with: eventName = [" + str + "], trackDataMap = [" + map + "]");
        if (map != null) {
            try {
                if (!map.isEmpty()) {
                    HashMap<String, String> mapA = a(map);
                    map.clear();
                    AUserTrack.record(str, mapA);
                    return;
                }
            } catch (Exception unused) {
                ALog.w("TrackUtils", "sendEvent AUserTrack.record failed.");
                return;
            }
        }
        ALog.d("TrackUtils", "sendEvent ignore, data empty.");
    }

    private static HashMap<String, String> a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        HashMap<String, String> map2 = new HashMap<>();
        map2.putAll(map);
        return map2;
    }
}
