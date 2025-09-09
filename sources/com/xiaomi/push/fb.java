package com.xiaomi.push;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes4.dex */
class fb implements fg {
    fb() {
    }

    private void b(Context context, fc fcVar) {
        String strM399a = fcVar.m399a();
        String strB = fcVar.b();
        String strD = fcVar.d();
        int iA = fcVar.a();
        if (context == null || TextUtils.isEmpty(strM399a) || TextUtils.isEmpty(strB) || TextUtils.isEmpty(strD)) {
            if (TextUtils.isEmpty(strD)) {
                ez.a(context, PushConstants.INTENT_ACTIVITY_NAME, 1008, "argument error");
                return;
            } else {
                ez.a(context, strD, 1008, "argument error");
                return;
            }
        }
        if (!com.xiaomi.push.service.m.b(context, strM399a, strB)) {
            ez.a(context, strD, 1003, "B is not ready");
            return;
        }
        ez.a(context, strD, 1002, "B is ready");
        ez.a(context, strD, 1004, "A is ready");
        Intent intent = new Intent(strB);
        intent.setPackage(strM399a);
        intent.putExtra("awake_info", ey.a(strD));
        intent.addFlags(276824064);
        intent.setAction(strB);
        if (iA == 1) {
            try {
                if (!fd.m400a(context)) {
                    ez.a(context, strD, 1008, "A not in foreground");
                    return;
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                ez.a(context, strD, 1008, "A meet a exception when help B's activity");
                return;
            }
        }
        context.startActivity(intent);
        ez.a(context, strD, 1005, "A is successful");
        ez.a(context, strD, 1006, "The job is finished");
    }

    @Override // com.xiaomi.push.fg
    public void a(Context context, fc fcVar) {
        if (fcVar != null) {
            b(context, fcVar);
        } else {
            ez.a(context, PushConstants.INTENT_ACTIVITY_NAME, 1008, "A receive incorrect message");
        }
    }

    @Override // com.xiaomi.push.fg
    public void a(Context context, Intent intent, String str) {
        if (context != null && (context instanceof Activity) && intent != null) {
            a((Activity) context, intent);
        } else {
            ez.a(context, PushConstants.INTENT_ACTIVITY_NAME, 1008, "B receive incorrect message");
        }
    }

    private void a(Activity activity, Intent intent) {
        String stringExtra = intent.getStringExtra("awake_info");
        if (!TextUtils.isEmpty(stringExtra)) {
            String strB = ey.b(stringExtra);
            if (!TextUtils.isEmpty(strB)) {
                ez.a(activity.getApplicationContext(), strB, 1007, "play with activity successfully");
                return;
            } else {
                ez.a(activity.getApplicationContext(), PushConstants.INTENT_ACTIVITY_NAME, 1008, "B get incorrect message");
                return;
            }
        }
        ez.a(activity.getApplicationContext(), PushConstants.INTENT_ACTIVITY_NAME, 1008, "B get incorrect message");
    }
}
