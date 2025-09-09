package com.xiaomi.mipush.sdk;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.push.ah;
import com.xiaomi.push.fd;
import com.xiaomi.push.ff;
import com.xiaomi.push.is;
import com.xiaomi.push.ix;
import com.xiaomi.push.jm;
import com.xiaomi.push.jx;
import com.xiaomi.push.jy;
import com.xiaomi.push.service.az;
import com.xiaomi.push.service.bc;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class o {
    public static void a(Context context, String str) {
        com.xiaomi.channel.commonutils.logger.b.m91a("aw_ping : send aw_ping cmd and content to push service from 3rd app");
        HashMap map = new HashMap();
        map.put("awake_info", str);
        map.put("event_type", String.valueOf(9999));
        map.put("description", "ping message");
        jm jmVar = new jm();
        jmVar.b(b.m140a(context).m141a());
        jmVar.d(context.getPackageName());
        jmVar.c(ix.AwakeAppResponse.f620a);
        jmVar.a(bc.a());
        jmVar.f760a = map;
        a(context, jmVar);
    }

    private static void a(Context context, jm jmVar) {
        boolean zA = az.a(context).a(is.AwakeAppPingSwitch.a(), false);
        int iA = az.a(context).a(is.AwakeAppPingFrequency.a(), 0);
        if (iA >= 0 && iA < 30) {
            com.xiaomi.channel.commonutils.logger.b.c("aw_ping: frquency need > 30s.");
            iA = 30;
        }
        boolean z2 = iA >= 0 ? zA : false;
        if (!com.xiaomi.push.j.m549a()) {
            a(context, jmVar, z2, iA);
        } else if (z2) {
            com.xiaomi.push.ah.a(context.getApplicationContext()).a((ah.a) new p(jmVar, context), iA);
        }
    }

    public static void a(Context context, Intent intent, Uri uri) {
        if (context == null) {
            return;
        }
        ao.a(context).m130a();
        if (fd.a(context.getApplicationContext()).m401a() == null) {
            fd.a(context.getApplicationContext()).a(b.m140a(context.getApplicationContext()).m141a(), context.getPackageName(), az.a(context.getApplicationContext()).a(is.AwakeInfoUploadWaySwitch.a(), 0), new c());
            az.a(context).a(new q(102, "awake online config", context));
        }
        if ((context instanceof Activity) && intent != null) {
            fd.a(context.getApplicationContext()).a(ff.ACTIVITY, context, intent, (String) null);
            return;
        }
        if ((context instanceof Service) && intent != null) {
            if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
                fd.a(context.getApplicationContext()).a(ff.SERVICE_COMPONENT, context, intent, (String) null);
                return;
            } else {
                fd.a(context.getApplicationContext()).a(ff.SERVICE_ACTION, context, intent, (String) null);
                return;
            }
        }
        if (uri == null || TextUtils.isEmpty(uri.toString())) {
            return;
        }
        fd.a(context.getApplicationContext()).a(ff.PROVIDER, context, (Intent) null, uri.toString());
    }

    public static final <T extends jy<T, ?>> void a(Context context, T t2, boolean z2, int i2) {
        byte[] bArrA = jx.a(t2);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("send message fail, because msgBytes is null.");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("action_help_ping");
        intent.putExtra("extra_help_ping_switch", z2);
        intent.putExtra("extra_help_ping_frequency", i2);
        intent.putExtra("mipush_payload", bArrA);
        intent.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
        ao.a(context).m132a(intent);
    }

    public static void a(Context context, String str, int i2, String str2) {
        jm jmVar = new jm();
        jmVar.b(str);
        jmVar.a(new HashMap());
        jmVar.m609a().put("extra_aw_app_online_cmd", String.valueOf(i2));
        jmVar.m609a().put("extra_help_aw_info", str2);
        jmVar.a(bc.a());
        byte[] bArrA = jx.a(jmVar);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("send message fail, because msgBytes is null.");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("action_aw_app_logic");
        intent.putExtra("mipush_payload", bArrA);
        ao.a(context).m132a(intent);
    }
}
