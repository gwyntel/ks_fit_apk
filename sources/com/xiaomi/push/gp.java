package com.xiaomi.push;

import android.text.TextUtils;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import com.xiaomi.push.ex;
import com.xiaomi.push.service.bf;
import java.util.HashMap;

/* loaded from: classes4.dex */
class gp {
    public static void a(bf.b bVar, String str, hb hbVar) throws NumberFormatException {
        String strA;
        ex.c cVar = new ex.c();
        if (!TextUtils.isEmpty(bVar.f24499c)) {
            cVar.a(bVar.f24499c);
        }
        if (!TextUtils.isEmpty(bVar.f24501e)) {
            cVar.d(bVar.f24501e);
        }
        if (!TextUtils.isEmpty(bVar.f24502f)) {
            cVar.e(bVar.f24502f);
        }
        cVar.b(bVar.f1042a ? "1" : "0");
        if (TextUtils.isEmpty(bVar.f24500d)) {
            cVar.c("XIAOMI-SASL");
        } else {
            cVar.c(bVar.f24500d);
        }
        gq gqVar = new gq();
        gqVar.c(bVar.f1043b);
        gqVar.a(Integer.parseInt(bVar.f24503g));
        gqVar.b(bVar.f1040a);
        gqVar.a("BIND", (String) null);
        gqVar.a(gqVar.e());
        com.xiaomi.channel.commonutils.logger.b.m91a("[Slim]: bind id=" + gqVar.e());
        HashMap map = new HashMap();
        map.put(ClientData.KEY_CHALLENGE, str);
        map.put("token", bVar.f24499c);
        map.put("chid", bVar.f24503g);
        map.put("from", bVar.f1043b);
        map.put("id", gqVar.e());
        map.put("to", "xiaomi.com");
        if (bVar.f1042a) {
            map.put("kick", "1");
        } else {
            map.put("kick", "0");
        }
        if (TextUtils.isEmpty(bVar.f24501e)) {
            map.put("client_attrs", "");
        } else {
            map.put("client_attrs", bVar.f24501e);
        }
        if (TextUtils.isEmpty(bVar.f24502f)) {
            map.put("cloud_attrs", "");
        } else {
            map.put("cloud_attrs", bVar.f24502f);
        }
        if (bVar.f24500d.equals("XIAOMI-PASS") || bVar.f24500d.equals("XMPUSH-PASS")) {
            strA = bn.a(bVar.f24500d, null, map, bVar.f24504h);
        } else {
            bVar.f24500d.equals("XIAOMI-SASL");
            strA = null;
        }
        cVar.f(strA);
        gqVar.a(cVar.m303a(), (String) null);
        hbVar.b(gqVar);
    }

    public static void a(String str, String str2, hb hbVar) throws NumberFormatException {
        gq gqVar = new gq();
        gqVar.c(str2);
        gqVar.a(Integer.parseInt(str));
        gqVar.a("UBND", (String) null);
        hbVar.b(gqVar);
    }
}
