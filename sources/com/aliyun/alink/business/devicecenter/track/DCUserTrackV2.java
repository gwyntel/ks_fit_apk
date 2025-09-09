package com.aliyun.alink.business.devicecenter.track;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class DCUserTrackV2 {

    /* renamed from: a, reason: collision with root package name */
    public Map<String, String> f10625a = new ConcurrentHashMap();

    public final HashMap<String, String> a() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(this.f10625a);
        return map;
    }

    public void addTrackData(String... strArr) {
        String str;
        if (strArr == null || strArr.length < 2 || strArr.length % 2 == 1) {
            return;
        }
        for (int i2 = 0; i2 < strArr.length / 2; i2 += 2) {
            String str2 = strArr[i2];
            if (str2 != null && (str = strArr[i2 + 1]) != null) {
                this.f10625a.put(str2, str);
            }
        }
    }

    public boolean hasKey(String str) {
        Map<String, String> map = this.f10625a;
        return map != null && map.containsKey(str);
    }

    public void removeTrackData(String str) {
        Map<String, String> map;
        if (TextUtils.isEmpty(str) || (map = this.f10625a) == null) {
            return;
        }
        map.remove(str);
    }

    public void resetTrackData() {
        Map<String, String> map = this.f10625a;
        if (map != null) {
            map.clear();
        }
    }

    public void sendEvent() {
        ALog.d("DCUserTrackV2", "sendEvent() called track data = " + this.f10625a);
        if (!this.f10625a.containsKey(AlinkConstants.KEY_PROVISION_STARTED)) {
            ALog.w("DCUserTrackV2", "sendEvent provision not started, do not send event.");
            resetTrackData();
        } else {
            removeTrackData(AlinkConstants.KEY_PROVISION_STARTED);
            AUserTrack.record(AlinkConstants.KEY_DC_PROVISION, a());
            resetTrackData();
        }
    }

    public void sendEvent(String str) {
        ALog.d("DCUserTrackV2", "sendEvent() called with: event = [" + str + "], trackData = [" + this.f10625a + "]");
        AUserTrack.record(AlinkConstants.KEY_DC_PROVISION_DISCOVER, a());
    }
}
