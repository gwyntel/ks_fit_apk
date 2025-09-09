package com.xiaomi.push;

import android.content.Context;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.xiaomi.push.do, reason: invalid class name */
/* loaded from: classes4.dex */
public class Cdo {

    /* renamed from: a, reason: collision with root package name */
    private final String f23590a = "disconnection_event";

    /* renamed from: b, reason: collision with root package name */
    private final String f23591b = "count";

    /* renamed from: c, reason: collision with root package name */
    private final String f23592c = "host";

    /* renamed from: d, reason: collision with root package name */
    private final String f23593d = "network_state";

    /* renamed from: e, reason: collision with root package name */
    private final String f23594e = "reason";

    /* renamed from: f, reason: collision with root package name */
    private final String f23595f = "ping_interval";

    /* renamed from: g, reason: collision with root package name */
    private final String f23596g = com.umeng.analytics.pro.bc.T;

    /* renamed from: h, reason: collision with root package name */
    private final String f23597h = "wifi_digest";

    /* renamed from: i, reason: collision with root package name */
    private final String f23598i = "duration";

    /* renamed from: j, reason: collision with root package name */
    private final String f23599j = "disconnect_time";

    /* renamed from: k, reason: collision with root package name */
    private final String f23600k = "connect_time";

    /* renamed from: l, reason: collision with root package name */
    private final String f23601l = "xmsf_vc";

    /* renamed from: m, reason: collision with root package name */
    private final String f23602m = "android_vc";

    /* renamed from: n, reason: collision with root package name */
    private final String f23603n = DeviceCommonConstants.KEY_DEVICE_ID;

    public void a(Context context, List<dn> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        dm.a("upload size = " + list.size());
        String strM803a = com.xiaomi.push.service.v.m803a(context);
        for (dn dnVar : list) {
            HashMap map = new HashMap();
            map.put("count", Integer.valueOf(dnVar.a()));
            map.put("host", dnVar.m285a());
            map.put("network_state", Integer.valueOf(dnVar.b()));
            map.put("reason", Integer.valueOf(dnVar.c()));
            map.put("ping_interval", Long.valueOf(dnVar.m284a()));
            map.put(com.umeng.analytics.pro.bc.T, Integer.valueOf(dnVar.d()));
            map.put("wifi_digest", dnVar.m287b());
            map.put("connected_network_type", Integer.valueOf(dnVar.e()));
            map.put("duration", Long.valueOf(dnVar.m286b()));
            map.put("disconnect_time", Long.valueOf(dnVar.m288c()));
            map.put("connect_time", Long.valueOf(dnVar.m289d()));
            map.put("xmsf_vc", Integer.valueOf(dnVar.f()));
            map.put("android_vc", Integer.valueOf(dnVar.g()));
            map.put(DeviceCommonConstants.KEY_DEVICE_ID, strM803a);
            gc.a().a("disconnection_event", (Map<String, Object>) map);
        }
    }
}
