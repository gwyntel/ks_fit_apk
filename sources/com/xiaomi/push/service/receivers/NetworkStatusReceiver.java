package com.xiaomi.push.service.receivers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.mipush.sdk.COSPushHelper;
import com.xiaomi.mipush.sdk.FTOSPushHelper;
import com.xiaomi.mipush.sdk.HWPushHelper;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.af;
import com.xiaomi.mipush.sdk.ao;
import com.xiaomi.mipush.sdk.au;
import com.xiaomi.mipush.sdk.b;
import com.xiaomi.mipush.sdk.e;
import com.xiaomi.push.bg;
import com.xiaomi.push.ig;
import com.xiaomi.push.l;
import com.xiaomi.push.service.ServiceClient;

/* loaded from: classes4.dex */
public class NetworkStatusReceiver extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f24617a = false;

    /* renamed from: b, reason: collision with root package name */
    private boolean f24618b = true;

    public NetworkStatusReceiver() {
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (this.f24618b) {
            return;
        }
        bg.m207a();
        l.a().post(new a(this, context));
    }

    public static boolean a() {
        return f24617a;
    }

    public NetworkStatusReceiver(Object obj) {
        f24617a = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context) {
        if (!ao.a(context).m133a() && b.m140a(context).m149c() && !b.m140a(context).m152f()) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(context, "com.xiaomi.push.service.XMPushService"));
                intent.setAction("com.xiaomi.push.network_status_changed");
                ServiceClient.getInstance(context).startServiceSafely(intent);
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        ig.m509a(context);
        if (bg.b(context) && ao.a(context).m136b()) {
            ao.a(context).m137c();
        }
        if (bg.b(context)) {
            if ("syncing".equals(af.a(context).a(au.DISABLE_PUSH))) {
                MiPushClient.disablePush(context);
            }
            if ("syncing".equals(af.a(context).a(au.ENABLE_PUSH))) {
                MiPushClient.enablePush(context);
            }
            af afVarA = af.a(context);
            au auVar = au.UPLOAD_HUAWEI_TOKEN;
            if ("syncing".equals(afVarA.a(auVar))) {
                ao.a(context).a((String) null, auVar, e.ASSEMBLE_PUSH_HUAWEI, com.alipay.sdk.m.k.b.f9362k);
            }
            if ("syncing".equals(af.a(context).a(au.UPLOAD_FCM_TOKEN))) {
                ao.a(context).a((String) null, auVar, e.ASSEMBLE_PUSH_HUAWEI, com.alipay.sdk.m.k.b.f9362k);
            }
            af afVarA2 = af.a(context);
            au auVar2 = au.UPLOAD_COS_TOKEN;
            if ("syncing".equals(afVarA2.a(auVar2))) {
                ao.a(context).a((String) null, auVar2, e.ASSEMBLE_PUSH_COS, com.alipay.sdk.m.k.b.f9362k);
            }
            af afVarA3 = af.a(context);
            au auVar3 = au.UPLOAD_FTOS_TOKEN;
            if ("syncing".equals(afVarA3.a(auVar3))) {
                ao.a(context).a((String) null, auVar3, e.ASSEMBLE_PUSH_FTOS, com.alipay.sdk.m.k.b.f9362k);
            }
            if (HWPushHelper.needConnect() && HWPushHelper.shouldTryConnect(context)) {
                HWPushHelper.setConnectTime(context);
                HWPushHelper.registerHuaWeiAssemblePush(context);
            }
            COSPushHelper.doInNetworkChange(context);
            FTOSPushHelper.doInNetworkChange(context);
        }
    }
}
