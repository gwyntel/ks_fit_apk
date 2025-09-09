package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/* loaded from: classes4.dex */
public class bv implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public static SharedPreferences f23518a;

    public static void a(Context context) {
        if (context == null || !context.getPackageName().equals("com.xiaomi.xmsf")) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = jCurrentTimeMillis % 86400000;
        if (f23518a == null) {
            f23518a = context.getSharedPreferences("mipush_extra", 0);
        }
        String strA = a();
        long j3 = f23518a.getLong(strA, 0L);
        if (j3 <= 0) {
            Random random = new Random(jCurrentTimeMillis);
            f23518a.edit().putLong(strA, jCurrentTimeMillis + random.nextInt(46800000) + (86400000 - j2) + (random.nextInt(15) * 86400000)).apply();
            return;
        }
        long j4 = jCurrentTimeMillis - j3;
        if (j4 >= 0) {
            f23518a.edit().putLong(strA, j3 + (((j4 / 1296000000) + 1) * 1296000000)).apply();
            new Thread(new bv()).start();
        } else if (Math.abs(j4) > 1296000000) {
            f23518a.edit().putLong(strA, jCurrentTimeMillis + new Random(jCurrentTimeMillis).nextInt(46800000) + (86400000 - j2)).apply();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0198  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            Method dump skipped, instructions count: 502
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.bv.run():void");
    }

    public static String a() {
        return "dc_job_result_time_25";
    }

    private void a(br brVar, bq bqVar, Exception exc) {
        HashMap map = new HashMap();
        String strM803a = com.xiaomi.push.service.v.m803a(C0472r.m684a());
        if (!TextUtils.isEmpty(strM803a)) {
            map.put(DeviceCommonConstants.KEY_DEVICE_ID, strM803a);
        }
        map.put("appCount", Long.valueOf(brVar.m222a()));
        map.put("channels", Long.valueOf(brVar.b()));
        map.put("packCount", Long.valueOf(brVar.c()));
        map.put("totalSize", Long.valueOf(brVar.d()));
        map.put("isBatch", Integer.valueOf(brVar.a()));
        map.put("maxCallTime", Long.valueOf(bqVar.a()));
        map.put("minCallTime", Long.valueOf(bqVar.b()));
        map.put("callAvg", Long.valueOf(bqVar.c()));
        map.put("duration", Long.valueOf(bqVar.d()));
        if (exc != null) {
            map.put("exception", exc.toString());
        }
        gc.a().a("app_switch_upload", (Map<String, Object>) map);
    }

    private void a(Context context, bu buVar, br brVar) {
        ir irVar = new ir();
        irVar.d("category_app_channel_info");
        irVar.c("app_channel_info");
        irVar.b(buVar.toString());
        irVar.a(false);
        irVar.a(1L);
        irVar.a("xmsf_channel");
        irVar.b(System.currentTimeMillis());
        irVar.g("com.xiaomi.xmsf");
        irVar.e("com.xiaomi.xmsf");
        irVar.f(com.xiaomi.push.service.ca.a());
        com.xiaomi.push.service.cb.a(context, irVar);
    }
}
