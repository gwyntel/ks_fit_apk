package com.xiaomi.push.service;

import android.content.Context;
import android.util.Log;
import com.xiaomi.push.ix;
import com.xiaomi.push.jm;
import com.xiaomi.push.service.XMPushService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class ch implements XMPushService.n {

    /* renamed from: a, reason: collision with root package name */
    private static Context f24576a;

    /* renamed from: a, reason: collision with other field name */
    private static final boolean f1076a = Log.isLoggable("UNDatas", 3);

    /* renamed from: a, reason: collision with other field name */
    private static final Map<Integer, Map<String, List<String>>> f1075a = new HashMap();

    public ch(Context context) {
        f24576a = context;
    }

    private static void b() {
        HashMap map = new HashMap();
        map.putAll(f1075a);
        if (map.size() > 0) {
            for (Integer num : map.keySet()) {
                Map map2 = (Map) map.get(num);
                if (map2 != null && map2.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (String str : map2.keySet()) {
                        sb.append(str);
                        sb.append(":");
                        List list = (List) map2.get(str);
                        if (!com.xiaomi.push.s.a(list)) {
                            for (int i2 = 0; i2 < list.size(); i2++) {
                                if (i2 != 0) {
                                    sb.append(",");
                                }
                                sb.append((String) list.get(i2));
                            }
                        }
                        sb.append(com.alipay.sdk.m.u.i.f9802b);
                    }
                    jm jmVarA = a(null, bc.a(), ix.NotificationRemoved.f620a, null);
                    jmVarA.a("removed_reason", String.valueOf(num));
                    jmVarA.a("all_delete_msgId_appId", sb.toString());
                    com.xiaomi.channel.commonutils.logger.b.b("UNDatas upload all removed messages reason: " + num + " allIds: " + sb.toString());
                    a(f24576a, jmVarA);
                }
                f1075a.remove(num);
            }
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.n
    /* renamed from: a */
    public void mo511a() {
        Map<Integer, Map<String, List<String>>> map = f1075a;
        if (map.size() > 0) {
            synchronized (map) {
                b();
            }
        }
    }

    private static void a(Context context, jm jmVar) {
        if (f1076a) {
            com.xiaomi.channel.commonutils.logger.b.b("UNDatas upload message notification:" + jmVar);
        }
        com.xiaomi.push.ah.a(context).a(new ci(jmVar));
    }

    private static jm a(String str, String str2, String str3, String str4) {
        jm jmVar = new jm();
        if (str3 != null) {
            jmVar.c(str3);
        }
        if (str != null) {
            jmVar.b(str);
        }
        if (str2 != null) {
            jmVar.a(str2);
        }
        if (str4 != null) {
            jmVar.d(str4);
        }
        jmVar.a(false);
        return jmVar;
    }
}
