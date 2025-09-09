package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.BuildConfig;
import com.xiaomi.push.ax;
import com.xiaomi.push.bp;
import com.xiaomi.push.in;
import com.xiaomi.push.ix;
import com.xiaomi.push.ja;
import com.xiaomi.push.jm;
import com.xiaomi.push.service.bc;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
class aw implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23395a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f136a;

    aw(Context context, boolean z2) {
        this.f23395a = context;
        this.f136a = z2;
    }

    @Override // java.lang.Runnable
    public void run() throws NoSuchAlgorithmException {
        com.xiaomi.channel.commonutils.logger.b.m91a("do sync info");
        jm jmVar = new jm(bc.a(), false);
        b bVarM140a = b.m140a(this.f23395a);
        jmVar.c(ix.SyncInfo.f620a);
        jmVar.b(bVarM140a.m141a());
        jmVar.d(this.f23395a.getPackageName());
        HashMap map = new HashMap();
        jmVar.f760a = map;
        Context context = this.f23395a;
        com.xiaomi.push.k.a(map, "app_version", com.xiaomi.push.g.m422a(context, context.getPackageName()));
        Map<String, String> map2 = jmVar.f760a;
        Context context2 = this.f23395a;
        com.xiaomi.push.k.a(map2, Constants.EXTRA_KEY_APP_VERSION_CODE, Integer.toString(com.xiaomi.push.g.a(context2, context2.getPackageName())));
        com.xiaomi.push.k.a(jmVar.f760a, "push_sdk_vn", BuildConfig.VERSION_NAME);
        com.xiaomi.push.k.a(jmVar.f760a, "push_sdk_vc", Integer.toString(BuildConfig.VERSION_CODE));
        com.xiaomi.push.k.a(jmVar.f760a, "token", bVarM140a.b());
        if (!com.xiaomi.push.j.m555d()) {
            String strA = bp.a(com.xiaomi.push.i.c(this.f23395a));
            String strE = com.xiaomi.push.i.e(this.f23395a);
            if (!TextUtils.isEmpty(strE)) {
                strA = strA + "," + strE;
            }
            if (!TextUtils.isEmpty(strA)) {
                com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_IMEI_MD5, strA);
            }
        }
        ax.a(this.f23395a).a(jmVar.f760a);
        com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_REG_ID, bVarM140a.m148c());
        com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_REG_SECRET, bVarM140a.d());
        com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_ACCEPT_TIME, MiPushClient.getAcceptTime(this.f23395a).replace(",", Constants.ACCEPT_TIME_SEPARATOR_SERVER));
        if (this.f136a) {
            com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_ALIASES_MD5, av.c(MiPushClient.getAllAlias(this.f23395a)));
            com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_TOPICS_MD5, av.c(MiPushClient.getAllTopic(this.f23395a)));
            com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_ACCOUNTS_MD5, av.c(MiPushClient.getAllUserAccount(this.f23395a)));
        } else {
            com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_ALIASES, av.d(MiPushClient.getAllAlias(this.f23395a)));
            com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_TOPICS, av.d(MiPushClient.getAllTopic(this.f23395a)));
            com.xiaomi.push.k.a(jmVar.f760a, Constants.EXTRA_KEY_ACCOUNTS, av.d(MiPushClient.getAllUserAccount(this.f23395a)));
        }
        ao.a(this.f23395a).a((ao) jmVar, in.Notification, false, (ja) null);
    }
}
