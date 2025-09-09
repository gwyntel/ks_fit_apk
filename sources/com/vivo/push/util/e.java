package com.vivo.push.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.heytap.mcssdk.constant.IntentConstant;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class e {
    public static boolean a(Context context, long j2, long j3) throws PackageManager.NameNotFoundException {
        p.d("ClientReportUtil", "report message: " + j2 + ", reportType: " + j3);
        com.vivo.push.b.x xVar = new com.vivo.push.b.x(j3);
        HashMap<String, String> map = new HashMap<>();
        map.put(IntentConstant.MESSAGE_ID, String.valueOf(j2));
        String strB = z.b(context, context.getPackageName());
        if (!TextUtils.isEmpty(strB)) {
            map.put("remoteAppId", strB);
        }
        xVar.a(map);
        com.vivo.push.e.a().a(xVar);
        return true;
    }

    public static boolean a(long j2, HashMap<String, String> map) {
        com.vivo.push.b.x xVar = new com.vivo.push.b.x(j2);
        xVar.a(map);
        xVar.d();
        com.vivo.push.e.a().a(xVar);
        return true;
    }
}
