package com.xiaomi.push.service;

import android.content.Context;
import android.os.Messenger;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.dt;
import com.xiaomi.push.gq;
import com.xiaomi.push.hb;
import com.xiaomi.push.hm;
import com.xiaomi.push.hs;
import com.xiaomi.push.in;
import com.xiaomi.push.ix;
import com.xiaomi.push.ja;
import com.xiaomi.push.jc;
import com.xiaomi.push.jj;
import com.xiaomi.push.jm;
import com.xiaomi.push.jx;
import com.xiaomi.push.jy;
import com.xiaomi.push.kd;
import com.xiaomi.push.service.bf;
import java.nio.ByteBuffer;
import java.util.Map;

/* loaded from: classes4.dex */
final class ai {
    static void a(XMPushService xMPushService) {
        u uVarM802a = v.m802a(xMPushService.getApplicationContext());
        if (uVarM802a != null) {
            bf.b bVarA = v.m802a(xMPushService.getApplicationContext()).a(xMPushService);
            com.xiaomi.channel.commonutils.logger.b.m91a("prepare account. " + bVarA.f1040a);
            a(xMPushService, bVarA);
            bf.a().a(bVarA);
            a(xMPushService, uVarM802a, 172800);
        }
    }

    static <T extends jy<T, ?>> jj b(String str, String str2, T t2, in inVar) {
        return a(str, str2, t2, inVar, false);
    }

    static jj b(String str, String str2) {
        jm jmVar = new jm();
        jmVar.b(str2);
        jmVar.c(ix.AppDataCleared.f620a);
        jmVar.a(bc.a());
        jmVar.a(false);
        return a(str, str2, jmVar, in.Notification);
    }

    private static void a(XMPushService xMPushService, u uVar, int i2) {
        by.a(xMPushService).a(new aj("MSAID", i2, xMPushService, uVar));
    }

    private static String a(jj jjVar) {
        Map<String, String> map;
        ja jaVar = jjVar.f742a;
        if (jaVar != null && (map = jaVar.f660b) != null) {
            String str = map.get("ext_traffic_source_pkg");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return jjVar.f748b;
    }

    static gq a(u uVar, Context context, jj jjVar) throws NumberFormatException {
        try {
            gq gqVar = new gq();
            gqVar.a(5);
            gqVar.c(uVar.f1111a);
            gqVar.b(a(jjVar));
            gqVar.a("SECMSG", "message");
            String str = uVar.f1111a;
            jjVar.f743a.f670a = str.substring(0, str.indexOf("@"));
            jjVar.f743a.f674c = str.substring(str.indexOf("/") + 1);
            gqVar.a(jx.a(jjVar), uVar.f24625c);
            gqVar.a((short) 1);
            com.xiaomi.channel.commonutils.logger.b.m91a("try send mi push message. packagename:" + jjVar.f748b + " action:" + jjVar.f741a);
            return gqVar;
        } catch (NullPointerException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return null;
        }
    }

    static gq a(XMPushService xMPushService, byte[] bArr) {
        jj jjVar = new jj();
        try {
            jx.a(jjVar, bArr);
            return a(v.m802a((Context) xMPushService), xMPushService, jjVar);
        } catch (kd e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return null;
        }
    }

    static <T extends jy<T, ?>> jj a(String str, String str2, T t2, in inVar) {
        return a(str, str2, t2, inVar, true);
    }

    private static <T extends jy<T, ?>> jj a(String str, String str2, T t2, in inVar, boolean z2) {
        byte[] bArrA = jx.a(t2);
        jj jjVar = new jj();
        jc jcVar = new jc();
        jcVar.f669a = 5L;
        jcVar.f670a = "fakeid";
        jjVar.a(jcVar);
        jjVar.a(ByteBuffer.wrap(bArrA));
        jjVar.a(inVar);
        jjVar.b(z2);
        jjVar.b(str);
        jjVar.a(false);
        jjVar.a(str2);
        return jjVar;
    }

    static jj a(String str, String str2) {
        jm jmVar = new jm();
        jmVar.b(str2);
        jmVar.c("package uninstalled");
        jmVar.a(hs.i());
        jmVar.a(false);
        return a(str, str2, jmVar, in.Notification);
    }

    static void a(XMPushService xMPushService, bf.b bVar) {
        bVar.a((Messenger) null);
        bVar.a(new ak(xMPushService));
    }

    static void a(XMPushService xMPushService, String str, byte[] bArr) {
        dt.a(str, xMPushService.getApplicationContext(), bArr);
        hb hbVarM708a = xMPushService.m708a();
        if (hbVarM708a != null) {
            if (hbVarM708a.mo461a()) {
                gq gqVarA = a(xMPushService, bArr);
                if (gqVarA != null) {
                    hbVarM708a.b(gqVarA);
                    return;
                } else {
                    y.a(xMPushService, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "not a valid message");
                    return;
                }
            }
            throw new hm("Don't support XMPP connection.");
        }
        throw new hm("try send msg while connection is null.");
    }

    static void a(XMPushService xMPushService, jj jjVar) {
        dt.a(jjVar.b(), xMPushService.getApplicationContext(), jjVar, -1);
        hb hbVarM708a = xMPushService.m708a();
        if (hbVarM708a != null) {
            if (hbVarM708a.mo461a()) {
                gq gqVarA = a(v.m802a((Context) xMPushService), xMPushService, jjVar);
                if (gqVarA != null) {
                    hbVarM708a.b(gqVarA);
                    return;
                }
                return;
            }
            throw new hm("Don't support XMPP connection.");
        }
        throw new hm("try send msg while connection is null.");
    }

    static String a(String str) {
        return str + ".permission.MIPUSH_RECEIVE";
    }
}
