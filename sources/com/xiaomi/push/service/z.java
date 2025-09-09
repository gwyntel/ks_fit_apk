package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.xiaomi.push.fo;
import com.xiaomi.push.gq;
import com.xiaomi.push.hm;
import com.xiaomi.push.hp;
import com.xiaomi.push.hr;
import com.xiaomi.push.hs;
import com.xiaomi.push.ig;
import com.xiaomi.push.in;
import com.xiaomi.push.ix;
import com.xiaomi.push.ja;
import com.xiaomi.push.jd;
import com.xiaomi.push.jj;
import com.xiaomi.push.jm;
import com.xiaomi.push.jx;
import com.xiaomi.push.service.al;
import com.xiaomi.push.service.bf;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class z {
    private static void b(Context context, jj jjVar, byte[] bArr) {
        if (al.m727a(jjVar)) {
            return;
        }
        String strA = al.a(jjVar);
        if (TextUtils.isEmpty(strA) || a(context, strA, bArr)) {
            return;
        }
        fo.a(context).b(strA, al.b(jjVar), jjVar.m593a().m559a(), "1");
    }

    private static boolean c(jj jjVar) {
        if (jjVar.m593a() == null || jjVar.m593a().m560a() == null) {
            return false;
        }
        return "1".equals(jjVar.m593a().m560a().get("obslete_ads_message"));
    }

    private static void d(XMPushService xMPushService, jj jjVar) {
        xMPushService.a(new ad(4, xMPushService, jjVar));
    }

    public void a(Context context, bf.b bVar, boolean z2, int i2, String str) {
        u uVarM802a;
        if (z2 || (uVarM802a = v.m802a(context)) == null || !"token-expired".equals(str)) {
            return;
        }
        v.a(context, uVarM802a.f24628f, uVarM802a.f24626d, uVarM802a.f24627e);
    }

    private static void c(XMPushService xMPushService, jj jjVar) {
        xMPushService.a(new ac(4, xMPushService, jjVar));
    }

    public void a(XMPushService xMPushService, hs hsVar, bf.b bVar) {
        if (hsVar instanceof hr) {
            hr hrVar = (hr) hsVar;
            hp hpVarA = hrVar.a("s");
            if (hpVarA != null) {
                try {
                    a(xMPushService, bo.a(bo.a(bVar.f24504h, hrVar.j()), hpVarA.c()), ig.a(hsVar.mo485a()));
                    return;
                } catch (IllegalArgumentException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    return;
                }
            }
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("not a mipush message");
    }

    private static boolean b(jj jjVar) {
        Map<String, String> mapM560a = jjVar.m593a().m560a();
        return mapM560a != null && mapM560a.containsKey("notify_effect");
    }

    private static void b(XMPushService xMPushService, jj jjVar) {
        xMPushService.a(new ab(4, xMPushService, jjVar));
    }

    public void a(XMPushService xMPushService, gq gqVar, bf.b bVar) {
        HashMap map;
        try {
            byte[] bArrM449a = gqVar.m449a(bVar.f24504h);
            if (e.b(gqVar)) {
                map = new HashMap();
                map.put("t_im", String.valueOf(gqVar.m450b()));
                map.put("t_rt", String.valueOf(gqVar.m443a()));
            } else {
                map = null;
            }
            a(xMPushService, bArrM449a, gqVar.c(), map);
        } catch (IllegalArgumentException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    private static void a(XMPushService xMPushService, byte[] bArr, long j2) throws NumberFormatException {
        a(xMPushService, bArr, j2, (Map<String, String>) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(com.xiaomi.push.service.XMPushService r20, byte[] r21, long r22, java.util.Map<java.lang.String, java.lang.String> r24) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 640
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.z.a(com.xiaomi.push.service.XMPushService, byte[], long, java.util.Map):void");
    }

    public static Intent a(byte[] bArr, long j2) {
        jj jjVarA = a(bArr);
        if (jjVarA == null) {
            return null;
        }
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.putExtra("mipush_payload", bArr);
        intent.putExtra("mrt", Long.toString(j2));
        intent.setPackage(jjVarA.f748b);
        return intent;
    }

    public static jj a(byte[] bArr) {
        jj jjVar = new jj();
        try {
            jx.a(jjVar, bArr);
            return jjVar;
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:145:0x042b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(com.xiaomi.push.service.XMPushService r19, java.lang.String r20, byte[] r21, android.content.Intent r22) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 1209
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.z.a(com.xiaomi.push.service.XMPushService, java.lang.String, byte[], android.content.Intent):void");
    }

    public static void a(Context context, jj jjVar, byte[] bArr) {
        try {
            al.c cVarM723a = al.m723a(context, jjVar, bArr);
            if (cVarM723a.f24462a > 0 && !TextUtils.isEmpty(cVarM723a.f1006a)) {
                ig.a(context, cVarM723a.f1006a, cVarM723a.f24462a, true, false, System.currentTimeMillis());
            }
            if (com.xiaomi.push.j.m550a(context) && ah.a(context, jjVar, cVarM723a.f1007a)) {
                ah.m718a(context, jjVar);
                com.xiaomi.channel.commonutils.logger.b.m91a("consume this broadcast by tts");
            } else {
                b(context, jjVar, bArr);
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("notify push msg error " + e2);
            e2.printStackTrace();
        }
    }

    public static boolean a(Context context, String str, byte[] bArr) {
        if (!com.xiaomi.push.g.m425a(context, str)) {
            return false;
        }
        Intent intent = new Intent("com.xiaomi.mipush.MESSAGE_ARRIVED");
        intent.putExtra("mipush_payload", bArr);
        intent.setPackage(str);
        try {
            if (context.getPackageManager().queryBroadcastReceivers(intent, 0).isEmpty()) {
                return false;
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("broadcast message arrived.");
            context.sendBroadcast(intent, ai.a(str));
            return true;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("meet error when broadcast message arrived. " + e2);
            return false;
        }
    }

    private static boolean a(XMPushService xMPushService, String str, jj jjVar, ja jaVar) {
        boolean z2 = true;
        if (jaVar != null && jaVar.m560a() != null && jaVar.m560a().containsKey("__check_alive") && jaVar.m560a().containsKey("__awake")) {
            jm jmVar = new jm();
            jmVar.b(jjVar.m594a());
            jmVar.d(str);
            jmVar.c(ix.AwakeSystemApp.f620a);
            jmVar.a(jaVar.m559a());
            jmVar.f760a = new HashMap();
            boolean zM425a = com.xiaomi.push.g.m425a(xMPushService.getApplicationContext(), str);
            jmVar.f760a.put("app_running", Boolean.toString(zM425a));
            if (!zM425a) {
                boolean z3 = Boolean.parseBoolean(jaVar.m560a().get("__awake"));
                jmVar.f760a.put("awaked", Boolean.toString(z3));
                if (!z3) {
                    z2 = false;
                }
            }
            try {
                ai.a(xMPushService, ai.a(jjVar.b(), jjVar.m594a(), jmVar, in.Notification));
            } catch (hm e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        return z2;
    }

    private static void a(XMPushService xMPushService, jj jjVar) {
        xMPushService.a(new aa(4, xMPushService, jjVar));
    }

    private static boolean a(jj jjVar) {
        return "com.xiaomi.xmsf".equals(jjVar.f748b) && jjVar.m593a() != null && jjVar.m593a().m560a() != null && jjVar.m593a().m560a().containsKey("miui_package_name");
    }

    private static boolean a(Context context, String str) {
        Intent intent = new Intent("com.xiaomi.mipush.miui.CLICK_MESSAGE");
        intent.setPackage(str);
        Intent intent2 = new Intent("com.xiaomi.mipush.miui.RECEIVE_MESSAGE");
        intent2.setPackage(str);
        PackageManager packageManager = context.getPackageManager();
        try {
            List<ResolveInfo> listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent2, 32);
            List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 32);
            if (listQueryBroadcastReceivers.isEmpty()) {
                if (listQueryIntentServices.isEmpty()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }

    private static void a(XMPushService xMPushService, jj jjVar, String str) {
        xMPushService.a(new ae(4, xMPushService, jjVar, str));
    }

    private static void a(XMPushService xMPushService, jj jjVar, String str, String str2) {
        xMPushService.a(new af(4, xMPushService, jjVar, str, str2));
    }

    private static void a(XMPushService xMPushService, jj jjVar, jm jmVar) {
        xMPushService.a(new ag(4, jmVar, jjVar, xMPushService));
    }

    public static jj a(Context context, jj jjVar) {
        return a(context, jjVar, (Map<String, String>) null);
    }

    public static jj a(Context context, jj jjVar, Map<String, String> map) {
        jd jdVar = new jd();
        jdVar.b(jjVar.m594a());
        ja jaVarM593a = jjVar.m593a();
        if (jaVarM593a != null) {
            jdVar.a(jaVarM593a.m559a());
            jdVar.a(jaVarM593a.m557a());
            if (!TextUtils.isEmpty(jaVarM593a.m564b())) {
                jdVar.c(jaVarM593a.m564b());
            }
        }
        jdVar.a(jx.m659a(context, jjVar));
        jj jjVarA = ai.a(jjVar.b(), jjVar.m594a(), jdVar, in.AckMessage);
        ja jaVarM593a2 = jjVar.m593a();
        if (jaVarM593a2 != null) {
            jaVarM593a2 = bs.a(jaVarM593a2.m558a());
        }
        jaVarM593a2.a("mat", Long.toString(System.currentTimeMillis()));
        jaVarM593a2.a("cs", String.valueOf(jx.a(context, jjVar)));
        if (map != null) {
            try {
                if (map.size() > 0) {
                    for (String str : map.keySet()) {
                        jaVarM593a2.a(str, map.get(str));
                    }
                }
            } catch (Throwable unused) {
            }
        }
        jjVarA.a(jaVarM593a2);
        return jjVarA;
    }

    private static boolean a(Context context, Intent intent) {
        try {
            List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 32);
            if (listQueryBroadcastReceivers != null) {
                if (!listQueryBroadcastReceivers.isEmpty()) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }
}
