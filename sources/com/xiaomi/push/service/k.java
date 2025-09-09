package com.xiaomi.push.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.xiaomi.push.gq;
import com.xiaomi.push.hq;
import com.xiaomi.push.hr;
import com.xiaomi.push.hs;
import com.xiaomi.push.hu;
import com.xiaomi.push.service.bf;
import java.util.Collection;
import java.util.Iterator;
import org.android.agoo.message.MessageService;

/* loaded from: classes4.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    private z f24599a = new z();

    @SuppressLint({"WrongConstant"})
    public void a(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.service_started");
        if (com.xiaomi.push.j.m554c()) {
            intent.addFlags(16777216);
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("[Bcst] send ***.push.service_started broadcast to inform push service has started.");
        context.sendBroadcast(intent);
    }

    @SuppressLint({"DefaultLocale"})
    public void a(Context context, bf.b bVar, boolean z2, int i2, String str) {
        if (AlcsPalConst.MODEL_TYPE_TGMESH.equalsIgnoreCase(bVar.f24503g)) {
            this.f24599a.a(context, bVar, z2, i2, str);
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.channel_opened");
        intent.setPackage(bVar.f1040a);
        intent.putExtra("ext_succeeded", z2);
        if (!z2) {
            intent.putExtra("ext_reason", i2);
        }
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("ext_reason_msg", str);
        }
        intent.putExtra("ext_chid", bVar.f24503g);
        intent.putExtra(bj.f24535s, bVar.f1043b);
        intent.putExtra(bj.J, bVar.f24505i);
        com.xiaomi.channel.commonutils.logger.b.m91a(String.format("[Bcst] notify channel open result. %s,%s,%b,%d", bVar.f24503g, bVar.f1040a, Boolean.valueOf(z2), Integer.valueOf(i2)));
        a(context, intent, bVar);
    }

    @SuppressLint({"DefaultLocale"})
    public void a(Context context, bf.b bVar, int i2) throws RemoteException {
        if (AlcsPalConst.MODEL_TYPE_TGMESH.equalsIgnoreCase(bVar.f24503g)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.channel_closed");
        intent.setPackage(bVar.f1040a);
        intent.putExtra(bj.f24538v, bVar.f24503g);
        intent.putExtra("ext_reason", i2);
        intent.putExtra(bj.f24535s, bVar.f1043b);
        intent.putExtra(bj.J, bVar.f24505i);
        if (bVar.f1034a != null && MessageService.MSG_ACCS_NOTIFY_DISMISS.equals(bVar.f24503g)) {
            try {
                bVar.f1034a.send(Message.obtain(null, 17, intent));
                return;
            } catch (RemoteException unused) {
                bVar.f1034a = null;
                StringBuilder sb = new StringBuilder();
                sb.append("peer may died: ");
                String str = bVar.f1043b;
                sb.append(str.substring(str.lastIndexOf(64)));
                com.xiaomi.channel.commonutils.logger.b.m91a(sb.toString());
                return;
            }
        }
        com.xiaomi.channel.commonutils.logger.b.m91a(String.format("[Bcst] notify channel closed. %s,%s,%d", bVar.f24503g, bVar.f1040a, Integer.valueOf(i2)));
        a(context, intent, bVar);
    }

    public void a(XMPushService xMPushService, String str, hs hsVar) {
        String str2;
        bf.b bVarA = a(hsVar);
        if (bVarA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("error while notify channel closed! channel " + str + " not registered");
            return;
        }
        if (AlcsPalConst.MODEL_TYPE_TGMESH.equalsIgnoreCase(str)) {
            this.f24599a.a(xMPushService, hsVar, bVarA);
            return;
        }
        String str3 = bVarA.f1040a;
        if (hsVar instanceof hr) {
            str2 = "com.xiaomi.push.new_msg";
        } else if (hsVar instanceof hq) {
            str2 = "com.xiaomi.push.new_iq";
        } else if (hsVar instanceof hu) {
            str2 = "com.xiaomi.push.new_pres";
        } else {
            com.xiaomi.channel.commonutils.logger.b.d("unknown packet type, drop it");
            return;
        }
        Intent intent = new Intent();
        intent.setAction(str2);
        intent.setPackage(str3);
        intent.putExtra("ext_chid", str);
        intent.putExtra("ext_packet", hsVar.a());
        intent.putExtra(bj.J, bVarA.f24505i);
        intent.putExtra(bj.B, bVarA.f24504h);
        com.xiaomi.channel.commonutils.logger.b.m91a(String.format("[Bcst] notify packet arrival. %s,%s,%s", bVarA.f24503g, bVarA.f1040a, hsVar.j()));
        if ("3".equalsIgnoreCase(str)) {
            intent.putExtra(bj.f24539w, hsVar.f551a);
            intent.putExtra(bj.f24540x, System.currentTimeMillis());
        }
        a(xMPushService, intent, bVarA);
    }

    public void a(XMPushService xMPushService, String str, gq gqVar) throws RemoteException {
        bf.b bVarA = a(gqVar);
        if (bVarA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("error while notify channel closed! channel " + str + " not registered");
            return;
        }
        if (AlcsPalConst.MODEL_TYPE_TGMESH.equalsIgnoreCase(str)) {
            this.f24599a.a(xMPushService, gqVar, bVarA);
            return;
        }
        String str2 = bVarA.f1040a;
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.new_msg");
        intent.setPackage(str2);
        intent.putExtra("ext_rcv_timestamp", SystemClock.elapsedRealtime());
        intent.putExtra("ext_chid", str);
        intent.putExtra("ext_raw_packet", gqVar.m449a(bVarA.f24504h));
        intent.putExtra(bj.J, bVarA.f24505i);
        intent.putExtra(bj.B, bVarA.f24504h);
        if (e.a(gqVar)) {
            intent.putExtra("ext_downward_pkt_id", gqVar.e());
        }
        if (bVarA.f1034a != null) {
            try {
                bVarA.f1034a.send(Message.obtain(null, 17, intent));
                com.xiaomi.channel.commonutils.logger.b.m91a("message was sent by messenger for chid=" + str);
                return;
            } catch (RemoteException unused) {
                bVarA.f1034a = null;
                StringBuilder sb = new StringBuilder();
                sb.append("peer may died: ");
                String str3 = bVarA.f1043b;
                sb.append(str3.substring(str3.lastIndexOf(64)));
                com.xiaomi.channel.commonutils.logger.b.m91a(sb.toString());
            }
        }
        if ("com.xiaomi.xmsf".equals(str2)) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a(String.format("[Bcst] notify packet(blob) arrival. %s,%s,%s", bVarA.f24503g, bVarA.f1040a, gqVar.e()));
        if (e.a(gqVar)) {
            bq.a().a(gqVar.e(), SystemClock.elapsedRealtime());
        }
        a(xMPushService, intent, bVarA);
    }

    bf.b a(hs hsVar) {
        Collection<bf.b> collectionM755a = bf.a().m755a(hsVar.k());
        if (collectionM755a.isEmpty()) {
            return null;
        }
        Iterator<bf.b> it = collectionM755a.iterator();
        if (collectionM755a.size() == 1) {
            return it.next();
        }
        String strM = hsVar.m();
        String strL = hsVar.l();
        while (it.hasNext()) {
            bf.b next = it.next();
            if (TextUtils.equals(strM, next.f1043b) || TextUtils.equals(strL, next.f1043b)) {
                return next;
            }
        }
        return null;
    }

    bf.b a(gq gqVar) {
        Collection<bf.b> collectionM755a = bf.a().m755a(Integer.toString(gqVar.a()));
        if (collectionM755a.isEmpty()) {
            return null;
        }
        Iterator<bf.b> it = collectionM755a.iterator();
        if (collectionM755a.size() == 1) {
            return it.next();
        }
        String strG = gqVar.g();
        while (it.hasNext()) {
            bf.b next = it.next();
            if (TextUtils.equals(strG, next.f1043b)) {
                return next;
            }
        }
        return null;
    }

    public void a(Context context, bf.b bVar, String str, String str2) {
        if (bVar == null) {
            com.xiaomi.channel.commonutils.logger.b.d("error while notify kick by server!");
            return;
        }
        if (AlcsPalConst.MODEL_TYPE_TGMESH.equalsIgnoreCase(bVar.f24503g)) {
            com.xiaomi.channel.commonutils.logger.b.d("mipush kicked by server");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.kicked");
        intent.setPackage(bVar.f1040a);
        intent.putExtra("ext_kick_type", str);
        intent.putExtra("ext_kick_reason", str2);
        intent.putExtra("ext_chid", bVar.f24503g);
        intent.putExtra(bj.f24535s, bVar.f1043b);
        intent.putExtra(bj.J, bVar.f24505i);
        com.xiaomi.channel.commonutils.logger.b.m91a(String.format("[Bcst] notify packet(blob) arrival. %s,%s,%s", bVar.f24503g, bVar.f1040a, str2));
        a(context, intent, bVar);
    }

    private static void a(Context context, Intent intent, bf.b bVar) {
        if ("com.xiaomi.xmsf".equals(context.getPackageName())) {
            context.sendBroadcast(intent);
        } else {
            context.sendBroadcast(intent, a(bVar));
        }
    }

    public static String a(bf.b bVar) {
        if (!MessageService.MSG_ACCS_NOTIFY_DISMISS.equals(bVar.f24503g)) {
            return bVar.f1040a + ".permission.MIPUSH_RECEIVE";
        }
        return bVar.f1040a + ".permission.MIMC_RECEIVE";
    }
}
