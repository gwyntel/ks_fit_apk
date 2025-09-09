package com.xiaomi.push;

import android.content.Context;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class dx {

    /* renamed from: a, reason: collision with root package name */
    private final String f23622a = "power_consumption_stats";

    /* renamed from: b, reason: collision with root package name */
    private final String f23623b = "off_up_ct";

    /* renamed from: c, reason: collision with root package name */
    private final String f23624c = "off_dn_ct";

    /* renamed from: d, reason: collision with root package name */
    private final String f23625d = "off_ping_ct";

    /* renamed from: e, reason: collision with root package name */
    private final String f23626e = "off_pong_ct";

    /* renamed from: f, reason: collision with root package name */
    private final String f23627f = "off_dur";

    /* renamed from: g, reason: collision with root package name */
    private final String f23628g = "on_up_ct";

    /* renamed from: h, reason: collision with root package name */
    private final String f23629h = "on_dn_ct";

    /* renamed from: i, reason: collision with root package name */
    private final String f23630i = "on_ping_ct";

    /* renamed from: j, reason: collision with root package name */
    private final String f23631j = "on_pong_ct";

    /* renamed from: k, reason: collision with root package name */
    private final String f23632k = "on_dur";

    /* renamed from: l, reason: collision with root package name */
    private final String f23633l = com.umeng.analytics.pro.f.f21694p;

    /* renamed from: m, reason: collision with root package name */
    private final String f23634m = com.umeng.analytics.pro.f.f21695q;

    /* renamed from: n, reason: collision with root package name */
    private final String f23635n = "xmsf_vc";

    /* renamed from: o, reason: collision with root package name */
    private final String f23636o = "android_vc";

    /* renamed from: p, reason: collision with root package name */
    private final String f23637p = DeviceCommonConstants.KEY_DEVICE_ID;

    public void a(Context context, dw dwVar) {
        if (dwVar == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("off_up_ct", Integer.valueOf(dwVar.a()));
        map.put("off_dn_ct", Integer.valueOf(dwVar.b()));
        map.put("off_ping_ct", Integer.valueOf(dwVar.c()));
        map.put("off_pong_ct", Integer.valueOf(dwVar.d()));
        map.put("off_dur", Long.valueOf(dwVar.m295a()));
        map.put("on_up_ct", Integer.valueOf(dwVar.e()));
        map.put("on_dn_ct", Integer.valueOf(dwVar.f()));
        map.put("on_ping_ct", Integer.valueOf(dwVar.g()));
        map.put("on_pong_ct", Integer.valueOf(dwVar.h()));
        map.put("on_dur", Long.valueOf(dwVar.m296b()));
        map.put(com.umeng.analytics.pro.f.f21694p, Long.valueOf(dwVar.m297c()));
        map.put(com.umeng.analytics.pro.f.f21695q, Long.valueOf(dwVar.m298d()));
        map.put("xmsf_vc", Integer.valueOf(dwVar.i()));
        map.put("android_vc", Integer.valueOf(dwVar.j()));
        map.put(DeviceCommonConstants.KEY_DEVICE_ID, com.xiaomi.push.service.v.m803a(context));
        gc.a().a("power_consumption_stats", (Map<String, Object>) map);
    }
}
