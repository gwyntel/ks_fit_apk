package com.xiaomi.push;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* loaded from: classes4.dex */
class fk implements fg {
    fk() {
    }

    @Override // com.xiaomi.push.fg
    public void a(Context context, fc fcVar) {
        if (fcVar != null) {
            a(context, fcVar.m399a(), fcVar.c(), fcVar.d());
        }
    }

    @Override // com.xiaomi.push.fg
    public void a(Context context, Intent intent, String str) {
        if (context == null || !(context instanceof Service)) {
            return;
        }
        a((Service) context, intent);
    }

    private void a(Context context, String str, String str2, String str3) {
        if (context != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            if (!com.xiaomi.push.service.m.a(context, str)) {
                ez.a(context, str3, 1003, "B is not ready");
                return;
            }
            ez.a(context, str3, 1002, "B is ready");
            ez.a(context, str3, 1004, "A is ready");
            try {
                Intent intent = new Intent();
                intent.setClassName(str, str2);
                intent.setAction("com.xiaomi.mipush.sdk.WAKEUP");
                intent.putExtra("waker_pkgname", context.getPackageName());
                intent.putExtra("awake_info", ey.a(str3));
                if (context.startService(intent) != null) {
                    ez.a(context, str3, 1005, "A is successful");
                    ez.a(context, str3, 1006, "The job is finished");
                    return;
                } else {
                    ez.a(context, str3, 1008, "A is fail to help B's service");
                    return;
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                ez.a(context, str3, 1008, "A meet a exception when help B's service");
                return;
            }
        }
        if (TextUtils.isEmpty(str3)) {
            ez.a(context, "service", 1008, "argument error");
        } else {
            ez.a(context, str3, 1008, "argument error");
        }
    }

    private void a(Service service, Intent intent) {
        if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra("waker_pkgname");
            String stringExtra2 = intent.getStringExtra("awake_info");
            if (TextUtils.isEmpty(stringExtra)) {
                ez.a(service.getApplicationContext(), "service", 1007, "old version message");
                return;
            }
            if (!TextUtils.isEmpty(stringExtra2)) {
                String strB = ey.b(stringExtra2);
                if (!TextUtils.isEmpty(strB)) {
                    ez.a(service.getApplicationContext(), strB, 1007, "old version message ");
                    return;
                } else {
                    ez.a(service.getApplicationContext(), "service", 1008, "B get a incorrect message");
                    return;
                }
            }
            ez.a(service.getApplicationContext(), stringExtra, 1007, "play with service ");
        }
    }
}
