package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.text.TextUtils;
import anetwork.channel.util.RequestConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.xiaomi.mipush.sdk.PushMessageHandler;
import com.xiaomi.push.bp;
import com.xiaomi.push.cf;
import com.xiaomi.push.dt;
import com.xiaomi.push.fn;
import com.xiaomi.push.fo;
import com.xiaomi.push.fy;
import com.xiaomi.push.in;
import com.xiaomi.push.is;
import com.xiaomi.push.ix;
import com.xiaomi.push.iz;
import com.xiaomi.push.ja;
import com.xiaomi.push.jb;
import com.xiaomi.push.jd;
import com.xiaomi.push.je;
import com.xiaomi.push.ji;
import com.xiaomi.push.jj;
import com.xiaomi.push.jk;
import com.xiaomi.push.jl;
import com.xiaomi.push.jm;
import com.xiaomi.push.jo;
import com.xiaomi.push.jq;
import com.xiaomi.push.js;
import com.xiaomi.push.ju;
import com.xiaomi.push.jw;
import com.xiaomi.push.jx;
import com.xiaomi.push.jy;
import com.xiaomi.push.kd;
import com.xiaomi.push.service.ax;
import com.xiaomi.push.service.az;
import com.xiaomi.push.service.ba;
import com.xiaomi.push.service.bj;
import com.xiaomi.push.service.bs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TimeZone;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public class am {

    /* renamed from: a, reason: collision with root package name */
    private static am f23378a;

    /* renamed from: a, reason: collision with other field name */
    private static Object f119a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private static Queue<String> f120a;

    /* renamed from: a, reason: collision with other field name */
    private Context f121a;

    private am(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f121a = applicationContext;
        if (applicationContext == null) {
            this.f121a = context;
        }
    }

    public static am a(Context context) {
        if (f23378a == null) {
            f23378a = new am(context);
        }
        return f23378a;
    }

    private void b(je jeVar) {
        com.xiaomi.channel.commonutils.logger.b.c("ASSEMBLE_PUSH : " + jeVar.toString());
        String strA = jeVar.a();
        Map<String, String> mapM574a = jeVar.m574a();
        if (mapM574a != null) {
            String str = mapM574a.get(Constants.ASSEMBLE_PUSH_REG_INFO);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (str.contains("brand:" + ag.FCM.name())) {
                com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH : receive fcm token sync ack");
                Context context = this.f121a;
                e eVar = e.ASSEMBLE_PUSH_FCM;
                i.b(context, eVar, str);
                a(strA, jeVar.f699a, eVar);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("brand:");
            ag agVar = ag.HUAWEI;
            sb.append(agVar.name());
            if (!str.contains(sb.toString())) {
                if (!str.contains("channel:" + agVar.name())) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("brand:");
                    ag agVar2 = ag.OPPO;
                    sb2.append(agVar2.name());
                    if (!str.contains(sb2.toString())) {
                        if (!str.contains("channel:" + agVar2.name())) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("brand:");
                            ag agVar3 = ag.VIVO;
                            sb3.append(agVar3.name());
                            if (!str.contains(sb3.toString())) {
                                if (!str.contains("channel:" + agVar3.name())) {
                                    return;
                                }
                            }
                            com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH : receive FTOS token sync ack");
                            Context context2 = this.f121a;
                            e eVar2 = e.ASSEMBLE_PUSH_FTOS;
                            i.b(context2, eVar2, str);
                            a(strA, jeVar.f699a, eVar2);
                            return;
                        }
                    }
                    com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH : receive COS token sync ack");
                    Context context3 = this.f121a;
                    e eVar3 = e.ASSEMBLE_PUSH_COS;
                    i.b(context3, eVar3, str);
                    a(strA, jeVar.f699a, eVar3);
                    return;
                }
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH : receive hw token sync ack");
            Context context4 = this.f121a;
            e eVar4 = e.ASSEMBLE_PUSH_HUAWEI;
            i.b(context4, eVar4, str);
            a(strA, jeVar.f699a, eVar4);
        }
    }

    public PushMessageHandler.a a(Intent intent) {
        String action = intent.getAction();
        com.xiaomi.channel.commonutils.logger.b.m91a("receive an intent from server, action=" + action);
        String stringExtra = intent.getStringExtra("mrt");
        if (stringExtra == null) {
            stringExtra = Long.toString(System.currentTimeMillis());
        }
        String stringExtra2 = intent.getStringExtra(TmpConstant.RRESPONSE_MESSAGEID);
        int intExtra = intent.getIntExtra("eventMessageType", -1);
        if ("com.xiaomi.mipush.RECEIVE_MESSAGE".equals(action)) {
            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            boolean booleanExtra = intent.getBooleanExtra("mipush_notified", false);
            if (byteArrayExtra == null) {
                com.xiaomi.channel.commonutils.logger.b.d("receiving an empty message, drop");
                fo.a(this.f121a).a(this.f121a.getPackageName(), intent, AgooConstants.ACK_PACK_NULL);
                return null;
            }
            jj jjVar = new jj();
            try {
                jx.a(jjVar, byteArrayExtra);
                b bVarM140a = b.m140a(this.f121a);
                ja jaVarM593a = jjVar.m593a();
                in inVarA = jjVar.a();
                in inVar = in.SendMessage;
                if (inVarA == inVar && jaVarM593a != null && !bVarM140a.m151e() && !booleanExtra) {
                    jaVarM593a.a("mrt", stringExtra);
                    jaVarM593a.a("mat", Long.toString(System.currentTimeMillis()));
                    if (!m120a(jjVar)) {
                        b(jjVar);
                    } else {
                        com.xiaomi.channel.commonutils.logger.b.b("this is a mina's message, ack later");
                        jaVarM593a.a(Constants.EXTRA_KEY_HYBRID_MESSAGE_TS, String.valueOf(jaVarM593a.m557a()));
                        jaVarM593a.a(Constants.EXTRA_KEY_HYBRID_DEVICE_STATUS, String.valueOf((int) jx.m659a(this.f121a, jjVar)));
                    }
                }
                if (jjVar.a() == inVar && !jjVar.m601b()) {
                    if (com.xiaomi.push.service.al.m727a(jjVar)) {
                        com.xiaomi.channel.commonutils.logger.b.m91a(String.format("drop an un-encrypted wake-up messages. %1$s, %2$s", jjVar.b(), jaVarM593a != null ? jaVarM593a.m559a() : ""));
                        fo.a(this.f121a).a(this.f121a.getPackageName(), intent, String.format("13: %1$s", jjVar.b()));
                    } else {
                        com.xiaomi.channel.commonutils.logger.b.m91a(String.format("drop an un-encrypted messages. %1$s, %2$s", jjVar.b(), jaVarM593a != null ? jaVarM593a.m559a() : ""));
                        fo.a(this.f121a).a(this.f121a.getPackageName(), intent, String.format("14: %1$s", jjVar.b()));
                    }
                    s.a(this.f121a, jjVar, booleanExtra);
                    return null;
                }
                if (jjVar.a() == inVar && jjVar.m601b() && com.xiaomi.push.service.al.m727a(jjVar) && (!booleanExtra || jaVarM593a == null || jaVarM593a.m560a() == null || !jaVarM593a.m560a().containsKey("notify_effect"))) {
                    com.xiaomi.channel.commonutils.logger.b.m91a(String.format("drop a wake-up messages which not has 'notify_effect' attr. %1$s, %2$s", jjVar.b(), jaVarM593a != null ? jaVarM593a.m559a() : ""));
                    fo.a(this.f121a).a(this.f121a.getPackageName(), intent, String.format("25: %1$s", jjVar.b()));
                    s.b(this.f121a, jjVar, booleanExtra);
                    return null;
                }
                if (!bVarM140a.m149c() && jjVar.f741a != in.Registration) {
                    if (com.xiaomi.push.service.al.m727a(jjVar)) {
                        return a(jjVar, booleanExtra, byteArrayExtra, stringExtra2, intExtra, intent);
                    }
                    s.e(this.f121a, jjVar, booleanExtra);
                    boolean zM150d = bVarM140a.m150d();
                    com.xiaomi.channel.commonutils.logger.b.d("receive message without registration. need re-register!registered?" + zM150d);
                    fo.a(this.f121a).a(this.f121a.getPackageName(), intent, AgooConstants.ACK_PACK_ERROR);
                    if (!zM150d) {
                        return null;
                    }
                    a();
                    return null;
                }
                if (bVarM140a.m149c() && bVarM140a.m152f()) {
                    if (jjVar.f741a == in.UnRegistration) {
                        if (jjVar.m601b()) {
                            bVarM140a.m142a();
                            MiPushClient.clearExtras(this.f121a);
                            PushMessageHandler.a();
                            return null;
                        }
                        com.xiaomi.channel.commonutils.logger.b.d("receiving an un-encrypt unregistration message");
                        return null;
                    }
                    s.e(this.f121a, jjVar, booleanExtra);
                    MiPushClient.unregisterPush(this.f121a);
                    return null;
                }
                return a(jjVar, booleanExtra, byteArrayExtra, stringExtra2, intExtra, intent);
            } catch (kd e2) {
                fo.a(this.f121a).a(this.f121a.getPackageName(), intent, "16");
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                return null;
            } catch (Exception e3) {
                fo.a(this.f121a).a(this.f121a.getPackageName(), intent, "17");
                com.xiaomi.channel.commonutils.logger.b.a(e3);
                return null;
            }
        }
        if ("com.xiaomi.mipush.ERROR".equals(action)) {
            MiPushCommandMessage miPushCommandMessage = new MiPushCommandMessage();
            jj jjVar2 = new jj();
            try {
                byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
                if (byteArrayExtra2 != null) {
                    jx.a(jjVar2, byteArrayExtra2);
                }
            } catch (kd unused) {
            }
            miPushCommandMessage.setCommand(String.valueOf(jjVar2.a()));
            miPushCommandMessage.setResultCode(intent.getIntExtra("mipush_error_code", 0));
            miPushCommandMessage.setReason(intent.getStringExtra("mipush_error_msg"));
            com.xiaomi.channel.commonutils.logger.b.d("receive a error message. code = " + intent.getIntExtra("mipush_error_code", 0) + ", msg= " + intent.getStringExtra("mipush_error_msg"));
            return miPushCommandMessage;
        }
        if (!"com.xiaomi.mipush.MESSAGE_ARRIVED".equals(action)) {
            return null;
        }
        byte[] byteArrayExtra3 = intent.getByteArrayExtra("mipush_payload");
        if (byteArrayExtra3 == null) {
            com.xiaomi.channel.commonutils.logger.b.d("message arrived: receiving an empty message, drop");
            return null;
        }
        jj jjVar3 = new jj();
        try {
            jx.a(jjVar3, byteArrayExtra3);
            b bVarM140a2 = b.m140a(this.f121a);
            if (com.xiaomi.push.service.al.m727a(jjVar3)) {
                com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive ignore reg message, ignore!");
                return null;
            }
            if (!bVarM140a2.m149c()) {
                com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive message without registration. need unregister or re-register!");
                return null;
            }
            if (bVarM140a2.m149c() && bVarM140a2.m152f()) {
                com.xiaomi.channel.commonutils.logger.b.d("message arrived: app info is invalidated");
                return null;
            }
            return a(jjVar3, byteArrayExtra3);
        } catch (Exception e4) {
            com.xiaomi.channel.commonutils.logger.b.d("fail to deal with arrived message. " + e4);
            return null;
        }
    }

    private void b(jj jjVar) {
        ja jaVarM593a = jjVar.m593a();
        if (jaVarM593a != null) {
            jaVarM593a = bs.a(jaVarM593a.m558a());
        }
        jd jdVar = new jd();
        jdVar.b(jjVar.m594a());
        jdVar.a(jaVarM593a.m559a());
        jdVar.a(jaVarM593a.m557a());
        if (!TextUtils.isEmpty(jaVarM593a.m564b())) {
            jdVar.c(jaVarM593a.m564b());
        }
        jdVar.a(jx.m659a(this.f121a, jjVar));
        ao.a(this.f121a).a((ao) jdVar, in.AckMessage, false, jaVarM593a);
    }

    private void b(jm jmVar) {
        Map<String, String> mapM609a = jmVar.m609a();
        if (mapM609a == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("detect failed because null");
            return;
        }
        String str = (String) ax.a(mapM609a, "pkgList", (Object) null);
        if (TextUtils.isEmpty(str)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("detect failed because empty");
            return;
        }
        Map<String, String> mapM423a = com.xiaomi.push.g.m423a(this.f121a, str);
        if (mapM423a != null) {
            String str2 = mapM423a.get("alive");
            String str3 = mapM423a.get("notAlive");
            if (!TextUtils.isEmpty(str2)) {
                jm jmVar2 = new jm();
                jmVar2.a(jmVar.m608a());
                jmVar2.b(jmVar.b());
                jmVar2.d(jmVar.c());
                jmVar2.c(ix.DetectAppAliveResult.f620a);
                HashMap map = new HashMap();
                jmVar2.f760a = map;
                map.put("alive", str2);
                if (Boolean.parseBoolean((String) ax.a(mapM609a, "reportNotAliveApp", RequestConstant.FALSE)) && !TextUtils.isEmpty(str3)) {
                    jmVar2.f760a.put("notAlive", str3);
                }
                ao.a(this.f121a).a((ao) jmVar2, in.Notification, false, (ja) null);
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.b("detect failed because no alive process");
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("detect failed because get status illegal");
    }

    private PushMessageHandler.a a(jj jjVar, byte[] bArr) {
        String str = null;
        try {
            jy jyVarA = ai.a(this.f121a, jjVar);
            if (jyVarA == null) {
                com.xiaomi.channel.commonutils.logger.b.d("message arrived: receiving an un-recognized message. " + jjVar.f741a);
                return null;
            }
            in inVarA = jjVar.a();
            com.xiaomi.channel.commonutils.logger.b.m91a("message arrived: processing an arrived message, action=" + inVarA);
            if (an.f23379a[inVarA.ordinal()] != 1) {
                return null;
            }
            if (!jjVar.m601b()) {
                com.xiaomi.channel.commonutils.logger.b.d("message arrived: receiving an un-encrypt message(SendMessage).");
                return null;
            }
            jq jqVar = (jq) jyVarA;
            iz izVarA = jqVar.a();
            if (izVarA == null) {
                com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive an empty message without push content, drop it");
                return null;
            }
            ja jaVar = jjVar.f742a;
            if (jaVar != null && jaVar.m560a() != null) {
                str = jjVar.f742a.f656a.get("jobkey");
            }
            MiPushMessage miPushMessageGenerateMessage = PushMessageHelper.generateMessage(jqVar, jjVar.m593a(), false);
            miPushMessageGenerateMessage.setArrivedMessage(true);
            com.xiaomi.channel.commonutils.logger.b.m91a("message arrived: receive a message, msgid=" + izVarA.m540a() + ", jobkey=" + str);
            return miPushMessageGenerateMessage;
        } catch (u e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive a message but decrypt failed. report when click.");
            return null;
        } catch (kd e3) {
            com.xiaomi.channel.commonutils.logger.b.a(e3);
            com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive a message which action string is not valid. is the reg expired?");
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private PushMessageHandler.a a(jj jjVar, boolean z2, byte[] bArr, String str, int i2, Intent intent) throws NumberFormatException {
        String strM540a;
        String str2;
        jq jqVar;
        MiPushMessage miPushMessage;
        ja jaVar;
        ArrayList arrayList = null;
        try {
            jy jyVarA = ai.a(this.f121a, jjVar);
            if (jyVarA == null) {
                com.xiaomi.channel.commonutils.logger.b.d("receiving an un-recognized message. " + jjVar.f741a);
                fo.a(this.f121a).b(this.f121a.getPackageName(), fn.m404a(i2), str, "18");
                s.c(this.f121a, jjVar, z2);
                return null;
            }
            in inVarA = jjVar.a();
            com.xiaomi.channel.commonutils.logger.b.m93a("processing a message, action=", inVarA, ", hasNotified=", Boolean.valueOf(z2));
            switch (an.f23379a[inVarA.ordinal()]) {
                case 1:
                    if (!jjVar.m601b()) {
                        com.xiaomi.channel.commonutils.logger.b.d("receiving an un-encrypt message(SendMessage).");
                        return null;
                    }
                    if (b.m140a(this.f121a).m151e() && !z2) {
                        com.xiaomi.channel.commonutils.logger.b.m91a("receive a message in pause state. drop it");
                        fo.a(this.f121a).a(this.f121a.getPackageName(), fn.m404a(i2), str, AgooConstants.ACK_PACK_NULL);
                        return null;
                    }
                    jq jqVar2 = (jq) jyVarA;
                    iz izVarA = jqVar2.a();
                    if (izVarA == null) {
                        com.xiaomi.channel.commonutils.logger.b.d("receive an empty message without push content, drop it");
                        fo.a(this.f121a).b(this.f121a.getPackageName(), fn.m404a(i2), str, AgooConstants.REPORT_ENCRYPT_FAIL);
                        s.d(this.f121a, jjVar, z2);
                        return null;
                    }
                    int intExtra = intent.getIntExtra("notification_click_button", 0);
                    if (z2) {
                        if (com.xiaomi.push.service.al.m727a(jjVar)) {
                            MiPushClient.reportIgnoreRegMessageClicked(this.f121a, izVarA.m540a(), jjVar.m593a(), jjVar.f748b, izVarA.b());
                        } else {
                            if (jjVar.m593a() != null) {
                                jaVar = new ja(jjVar.m593a());
                            } else {
                                jaVar = new ja();
                            }
                            if (jaVar.m560a() == null) {
                                jaVar.a(new HashMap());
                            }
                            jaVar.m560a().put("notification_click_button", String.valueOf(intExtra));
                            MiPushClient.reportMessageClicked(this.f121a, izVarA.m540a(), jaVar, izVarA.b());
                        }
                    }
                    if (!z2) {
                        if (!TextUtils.isEmpty(jqVar2.d()) && MiPushClient.aliasSetTime(this.f121a, jqVar2.d()) < 0) {
                            MiPushClient.addAlias(this.f121a, jqVar2.d());
                        } else if (!TextUtils.isEmpty(jqVar2.c()) && MiPushClient.topicSubscribedTime(this.f121a, jqVar2.c()) < 0) {
                            MiPushClient.addTopic(this.f121a, jqVar2.c());
                        }
                    }
                    ja jaVar2 = jjVar.f742a;
                    if (jaVar2 == null || jaVar2.m560a() == null) {
                        strM540a = null;
                        str2 = null;
                    } else {
                        strM540a = jjVar.f742a.f656a.get("jobkey");
                        str2 = strM540a;
                    }
                    if (TextUtils.isEmpty(strM540a)) {
                        strM540a = izVarA.m540a();
                    }
                    if (!z2 && m119a(this.f121a, strM540a)) {
                        com.xiaomi.channel.commonutils.logger.b.m91a("drop a duplicate message, key=" + strM540a);
                        fo.a(this.f121a).c(this.f121a.getPackageName(), fn.m404a(i2), str, "2:" + strM540a);
                        jqVar = jqVar2;
                        miPushMessage = null;
                    } else {
                        MiPushMessage miPushMessageGenerateMessage = PushMessageHelper.generateMessage(jqVar2, jjVar.m593a(), z2);
                        if (miPushMessageGenerateMessage.getPassThrough() == 0 && !z2 && com.xiaomi.push.service.al.m728a(miPushMessageGenerateMessage.getExtra())) {
                            com.xiaomi.push.service.al.m723a(this.f121a, jjVar, bArr);
                            return null;
                        }
                        String strA = com.xiaomi.push.service.al.a(miPushMessageGenerateMessage.getExtra(), intExtra);
                        jqVar = jqVar2;
                        com.xiaomi.channel.commonutils.logger.b.m93a("receive a message, msgid=", izVarA.m540a(), ", jobkey=", strM540a, ", btn=", Integer.valueOf(intExtra), ", typeId=", strA, ", hasNotified=", Boolean.valueOf(z2));
                        if (z2 && miPushMessageGenerateMessage.getExtra() != null && !TextUtils.isEmpty(strA)) {
                            Map<String, String> extra = miPushMessageGenerateMessage.getExtra();
                            if (intExtra != 0 && jjVar.m593a() != null) {
                                ao.a(this.f121a).a(jjVar.m593a().c(), intExtra);
                            }
                            if (com.xiaomi.push.service.al.m727a(jjVar)) {
                                Intent intentA = a(this.f121a, jjVar.f748b, extra, intExtra);
                                intentA.putExtra("eventMessageType", i2);
                                intentA.putExtra(TmpConstant.RRESPONSE_MESSAGEID, str);
                                intentA.putExtra("jobkey", str2);
                                String strC = izVarA.c();
                                if (!TextUtils.isEmpty(strC)) {
                                    intentA.putExtra(MessengerShareContentUtility.ATTACHMENT_PAYLOAD, strC);
                                }
                                this.f121a.startActivity(intentA);
                                s.a(this.f121a, jjVar);
                                fo.a(this.f121a).a(this.f121a.getPackageName(), fn.m404a(i2), str, 3006, strA);
                                com.xiaomi.channel.commonutils.logger.b.m92a("PushMessageProcessor", "start business activity succ");
                            } else {
                                Context context = this.f121a;
                                Intent intentA2 = a(context, context.getPackageName(), extra, intExtra);
                                if (intentA2 != null) {
                                    if (!strA.equals(bj.f24519c)) {
                                        intentA2.putExtra(PushMessageHelper.KEY_MESSAGE, miPushMessageGenerateMessage);
                                        intentA2.putExtra("eventMessageType", i2);
                                        intentA2.putExtra(TmpConstant.RRESPONSE_MESSAGEID, str);
                                        intentA2.putExtra("jobkey", str2);
                                    }
                                    this.f121a.startActivity(intentA2);
                                    s.a(this.f121a, jjVar);
                                    com.xiaomi.channel.commonutils.logger.b.m92a("PushMessageProcessor", "start activity succ");
                                    fo.a(this.f121a).a(this.f121a.getPackageName(), fn.m404a(i2), str, 1006, strA);
                                    if (strA.equals(bj.f24519c)) {
                                        fo.a(this.f121a).a(this.f121a.getPackageName(), fn.m404a(i2), str, AgooConstants.ACK_FLAG_NULL);
                                    }
                                } else {
                                    com.xiaomi.channel.commonutils.logger.b.c("PushMessageProcessor", "missing target intent for message: " + izVarA.m540a() + ", typeId=" + strA);
                                }
                            }
                            com.xiaomi.channel.commonutils.logger.b.m92a("PushMessageProcessor", "pre-def msg process done.");
                            return null;
                        }
                        miPushMessage = miPushMessageGenerateMessage;
                    }
                    if (jjVar.m593a() == null && !z2) {
                        a(jqVar, jjVar);
                    }
                    return miPushMessage;
                case 2:
                    jo joVar = (jo) jyVarA;
                    String str3 = b.m140a(this.f121a).f139a;
                    if (!TextUtils.isEmpty(str3) && TextUtils.equals(str3, joVar.m622a())) {
                        long jM129a = ao.a(this.f121a).m129a();
                        if (jM129a > 0 && SystemClock.elapsedRealtime() - jM129a > com.heytap.mcssdk.constant.Constants.MILLS_OF_CONNECT_SUCCESS) {
                            com.xiaomi.channel.commonutils.logger.b.m91a("The received registration result has expired.");
                            fo.a(this.f121a).b(this.f121a.getPackageName(), fn.m404a(i2), str, "26");
                            return null;
                        }
                        b.m140a(this.f121a).f139a = null;
                        if (joVar.f804a == 0) {
                            b.m140a(this.f121a).b(joVar.f816e, joVar.f817f, joVar.f823l);
                            FCMPushHelper.persistIfXmsfSupDecrypt(this.f121a);
                            fo.a(this.f121a).a(this.f121a.getPackageName(), fn.m404a(i2), str, 6006, "1");
                        } else {
                            fo.a(this.f121a).a(this.f121a.getPackageName(), fn.m404a(i2), str, 6006, "2");
                        }
                        if (!TextUtils.isEmpty(joVar.f816e)) {
                            arrayList = new ArrayList();
                            arrayList.add(joVar.f816e);
                        }
                        MiPushCommandMessage miPushCommandMessageGenerateCommandMessage = PushMessageHelper.generateCommandMessage(fy.COMMAND_REGISTER.f444a, arrayList, joVar.f804a, joVar.f815d, null, joVar.m623a());
                        ao.a(this.f121a).m138d();
                        return miPushCommandMessageGenerateCommandMessage;
                    }
                    com.xiaomi.channel.commonutils.logger.b.m91a("bad Registration result:");
                    fo.a(this.f121a).b(this.f121a.getPackageName(), fn.m404a(i2), str, AgooConstants.REPORT_MESSAGE_NULL);
                    return null;
                case 3:
                    if (!jjVar.m601b()) {
                        com.xiaomi.channel.commonutils.logger.b.d("receiving an un-encrypt message(UnRegistration).");
                        return null;
                    }
                    if (((ju) jyVarA).f882a == 0) {
                        b.m140a(this.f121a).m142a();
                        MiPushClient.clearExtras(this.f121a);
                    }
                    PushMessageHandler.a();
                    return null;
                case 4:
                    js jsVar = (js) jyVarA;
                    if (jsVar.f857a == 0) {
                        MiPushClient.addTopic(this.f121a, jsVar.b());
                    }
                    if (!TextUtils.isEmpty(jsVar.b())) {
                        arrayList = new ArrayList();
                        arrayList.add(jsVar.b());
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("resp-cmd:");
                    fy fyVar = fy.COMMAND_SUBSCRIBE_TOPIC;
                    sb.append(fyVar);
                    sb.append(", ");
                    sb.append(jsVar.a());
                    com.xiaomi.channel.commonutils.logger.b.e(sb.toString());
                    return PushMessageHelper.generateCommandMessage(fyVar.f444a, arrayList, jsVar.f857a, jsVar.f863d, jsVar.c(), null);
                case 5:
                    jw jwVar = (jw) jyVarA;
                    if (jwVar.f902a == 0) {
                        MiPushClient.removeTopic(this.f121a, jwVar.b());
                    }
                    if (!TextUtils.isEmpty(jwVar.b())) {
                        arrayList = new ArrayList();
                        arrayList.add(jwVar.b());
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("resp-cmd:");
                    fy fyVar2 = fy.COMMAND_UNSUBSCRIBE_TOPIC;
                    sb2.append(fyVar2);
                    sb2.append(", ");
                    sb2.append(jwVar.a());
                    com.xiaomi.channel.commonutils.logger.b.e(sb2.toString());
                    return PushMessageHelper.generateCommandMessage(fyVar2.f444a, arrayList, jwVar.f902a, jwVar.f908d, jwVar.c(), null);
                case 6:
                    dt.a(this.f121a.getPackageName(), this.f121a, jyVarA, in.Command, bArr.length);
                    ji jiVar = (ji) jyVarA;
                    String strB = jiVar.b();
                    List<String> listM587a = jiVar.m587a();
                    if (jiVar.f729a == 0) {
                        if (TextUtils.equals(strB, fy.COMMAND_SET_ACCEPT_TIME.f444a) && listM587a != null && listM587a.size() > 1) {
                            MiPushClient.addAcceptTime(this.f121a, listM587a.get(0), listM587a.get(1));
                            if ("00:00".equals(listM587a.get(0)) && "00:00".equals(listM587a.get(1))) {
                                b.m140a(this.f121a).a(true);
                            } else {
                                b.m140a(this.f121a).a(false);
                            }
                            listM587a = a(TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault(), listM587a);
                        } else if (TextUtils.equals(strB, fy.COMMAND_SET_ALIAS.f444a) && listM587a != null && listM587a.size() > 0) {
                            MiPushClient.addAlias(this.f121a, listM587a.get(0));
                        } else if (TextUtils.equals(strB, fy.COMMAND_UNSET_ALIAS.f444a) && listM587a != null && listM587a.size() > 0) {
                            MiPushClient.removeAlias(this.f121a, listM587a.get(0));
                        } else if (TextUtils.equals(strB, fy.COMMAND_SET_ACCOUNT.f444a) && listM587a != null && listM587a.size() > 0) {
                            MiPushClient.addAccount(this.f121a, listM587a.get(0));
                        } else if (TextUtils.equals(strB, fy.COMMAND_UNSET_ACCOUNT.f444a) && listM587a != null && listM587a.size() > 0) {
                            MiPushClient.removeAccount(this.f121a, listM587a.get(0));
                        } else if (TextUtils.equals(strB, fy.COMMAND_CHK_VDEVID.f444a)) {
                            return null;
                        }
                    }
                    List<String> list = listM587a;
                    com.xiaomi.channel.commonutils.logger.b.e("resp-cmd:" + strB + ", " + jiVar.a());
                    return PushMessageHelper.generateCommandMessage(strB, list, jiVar.f729a, jiVar.f737d, jiVar.c(), null);
                case 7:
                    dt.a(this.f121a.getPackageName(), this.f121a, jyVarA, in.Notification, bArr.length);
                    if (jyVarA instanceof je) {
                        je jeVar = (je) jyVarA;
                        String strA2 = jeVar.a();
                        com.xiaomi.channel.commonutils.logger.b.e("resp-type:" + jeVar.b() + ", code:" + jeVar.f699a + ", " + strA2);
                        if (ix.DisablePushMessage.f620a.equalsIgnoreCase(jeVar.f706d)) {
                            if (jeVar.f699a == 0) {
                                synchronized (af.class) {
                                    try {
                                        if (af.a(this.f121a).m118a(strA2)) {
                                            af.a(this.f121a).c(strA2);
                                            af afVarA = af.a(this.f121a);
                                            au auVar = au.DISABLE_PUSH;
                                            if ("syncing".equals(afVarA.a(auVar))) {
                                                af.a(this.f121a).a(auVar, "synced");
                                                MiPushClient.clearNotification(this.f121a);
                                                MiPushClient.clearLocalNotificationType(this.f121a);
                                                PushMessageHandler.a();
                                                ao.a(this.f121a).m135b();
                                            }
                                        }
                                    } finally {
                                    }
                                }
                            } else if ("syncing".equals(af.a(this.f121a).a(au.DISABLE_PUSH))) {
                                synchronized (af.class) {
                                    try {
                                        if (af.a(this.f121a).m118a(strA2)) {
                                            if (af.a(this.f121a).a(strA2) < 10) {
                                                af.a(this.f121a).b(strA2);
                                                ao.a(this.f121a).a(true, strA2);
                                            } else {
                                                af.a(this.f121a).c(strA2);
                                            }
                                        }
                                    } finally {
                                    }
                                }
                            } else {
                                af.a(this.f121a).c(strA2);
                            }
                        } else if (ix.EnablePushMessage.f620a.equalsIgnoreCase(jeVar.f706d)) {
                            if (jeVar.f699a == 0) {
                                synchronized (af.class) {
                                    try {
                                        if (af.a(this.f121a).m118a(strA2)) {
                                            af.a(this.f121a).c(strA2);
                                            af afVarA2 = af.a(this.f121a);
                                            au auVar2 = au.ENABLE_PUSH;
                                            if ("syncing".equals(afVarA2.a(auVar2))) {
                                                af.a(this.f121a).a(auVar2, "synced");
                                            }
                                        }
                                    } finally {
                                    }
                                }
                            } else if ("syncing".equals(af.a(this.f121a).a(au.ENABLE_PUSH))) {
                                synchronized (af.class) {
                                    try {
                                        if (af.a(this.f121a).m118a(strA2)) {
                                            if (af.a(this.f121a).a(strA2) < 10) {
                                                af.a(this.f121a).b(strA2);
                                                ao.a(this.f121a).a(false, strA2);
                                            } else {
                                                af.a(this.f121a).c(strA2);
                                            }
                                        }
                                    } finally {
                                    }
                                }
                            } else {
                                af.a(this.f121a).c(strA2);
                            }
                        } else if (ix.ThirdPartyRegUpdate.f620a.equalsIgnoreCase(jeVar.f706d)) {
                            b(jeVar);
                        } else if (ix.UploadTinyData.f620a.equalsIgnoreCase(jeVar.f706d)) {
                            a(jeVar);
                        }
                    } else if (jyVarA instanceof jm) {
                        jm jmVar = (jm) jyVarA;
                        if ("registration id expired".equalsIgnoreCase(jmVar.f765d)) {
                            List<String> allAlias = MiPushClient.getAllAlias(this.f121a);
                            List<String> allTopic = MiPushClient.getAllTopic(this.f121a);
                            List<String> allUserAccount = MiPushClient.getAllUserAccount(this.f121a);
                            String acceptTime = MiPushClient.getAcceptTime(this.f121a);
                            com.xiaomi.channel.commonutils.logger.b.e("resp-type:" + jmVar.f765d + ", " + jmVar.m608a());
                            MiPushClient.reInitialize(this.f121a, jb.RegIdExpired);
                            for (String str4 : allAlias) {
                                MiPushClient.removeAlias(this.f121a, str4);
                                MiPushClient.setAlias(this.f121a, str4, null);
                            }
                            for (String str5 : allTopic) {
                                MiPushClient.removeTopic(this.f121a, str5);
                                MiPushClient.subscribe(this.f121a, str5, null);
                            }
                            for (String str6 : allUserAccount) {
                                MiPushClient.removeAccount(this.f121a, str6);
                                MiPushClient.setUserAccount(this.f121a, str6, null);
                            }
                            String[] strArrSplit = acceptTime.split(",");
                            if (strArrSplit.length == 2) {
                                MiPushClient.removeAcceptTime(this.f121a);
                                MiPushClient.addAcceptTime(this.f121a, strArrSplit[0], strArrSplit[1]);
                            }
                        } else if (ix.ClientInfoUpdateOk.f620a.equalsIgnoreCase(jmVar.f765d)) {
                            if (jmVar.m609a() != null && jmVar.m609a().containsKey("app_version")) {
                                b.m140a(this.f121a).m143a(jmVar.m609a().get("app_version"));
                            }
                        } else if (ix.AwakeApp.f620a.equalsIgnoreCase(jmVar.f765d)) {
                            if (jjVar.m601b() && jmVar.m609a() != null && jmVar.m609a().containsKey("awake_info")) {
                                String str7 = jmVar.m609a().get("awake_info");
                                Context context2 = this.f121a;
                                o.a(context2, b.m140a(context2).m141a(), az.a(this.f121a).a(is.AwakeInfoUploadWaySwitch.a(), 0), str7);
                            }
                        } else {
                            try {
                                if (ix.NormalClientConfigUpdate.f620a.equalsIgnoreCase(jmVar.f765d)) {
                                    jl jlVar = new jl();
                                    jx.a(jlVar, jmVar.m614a());
                                    ba.a(az.a(this.f121a), jlVar);
                                } else if (ix.CustomClientConfigUpdate.f620a.equalsIgnoreCase(jmVar.f765d)) {
                                    jk jkVar = new jk();
                                    jx.a(jkVar, jmVar.m614a());
                                    ba.a(az.a(this.f121a), jkVar);
                                } else if (ix.SyncInfoResult.f620a.equalsIgnoreCase(jmVar.f765d)) {
                                    av.a(this.f121a, jmVar);
                                } else if (ix.ForceSync.f620a.equalsIgnoreCase(jmVar.f765d)) {
                                    com.xiaomi.channel.commonutils.logger.b.m91a("receive force sync notification");
                                    av.a(this.f121a, false);
                                } else if (ix.CancelPushMessage.f620a.equals(jmVar.f765d)) {
                                    com.xiaomi.channel.commonutils.logger.b.e("resp-type:" + jmVar.f765d + ", " + jmVar.m608a());
                                    if (jmVar.m609a() != null) {
                                        int i3 = -2;
                                        if (jmVar.m609a().containsKey(bj.Q)) {
                                            String str8 = jmVar.m609a().get(bj.Q);
                                            if (!TextUtils.isEmpty(str8)) {
                                                try {
                                                    i3 = Integer.parseInt(str8);
                                                } catch (NumberFormatException e2) {
                                                    e2.printStackTrace();
                                                }
                                            }
                                        }
                                        if (i3 >= -1) {
                                            MiPushClient.clearNotification(this.f121a, i3);
                                        } else {
                                            String str9 = "";
                                            String str10 = "";
                                            if (jmVar.m609a().containsKey(bj.O)) {
                                                str9 = jmVar.m609a().get(bj.O);
                                            }
                                            if (jmVar.m609a().containsKey(bj.P)) {
                                                str10 = jmVar.m609a().get(bj.P);
                                            }
                                            MiPushClient.clearNotification(this.f121a, str9, str10);
                                        }
                                    }
                                    a(jmVar);
                                } else if (ix.HybridRegisterResult.f620a.equals(jmVar.f765d)) {
                                    try {
                                        jo joVar2 = new jo();
                                        jx.a(joVar2, jmVar.m614a());
                                        MiPushClient4Hybrid.onReceiveRegisterResult(this.f121a, joVar2);
                                    } catch (kd e3) {
                                        com.xiaomi.channel.commonutils.logger.b.a(e3);
                                    }
                                } else if (ix.HybridUnregisterResult.f620a.equals(jmVar.f765d)) {
                                    try {
                                        ju juVar = new ju();
                                        jx.a(juVar, jmVar.m614a());
                                        MiPushClient4Hybrid.onReceiveUnregisterResult(this.f121a, juVar);
                                    } catch (kd e4) {
                                        com.xiaomi.channel.commonutils.logger.b.a(e4);
                                    }
                                } else if (!ix.PushLogUpload.f620a.equals(jmVar.f765d)) {
                                    if (ix.DetectAppAlive.f620a.equals(jmVar.f765d)) {
                                        com.xiaomi.channel.commonutils.logger.b.b("receive detect msg");
                                        b(jmVar);
                                    } else if (com.xiaomi.push.service.j.a(jmVar)) {
                                        com.xiaomi.channel.commonutils.logger.b.b("receive notification handle by cpra");
                                    }
                                }
                            } catch (kd unused) {
                            }
                        }
                    }
                    return null;
                default:
                    return null;
            }
        } catch (u e5) {
            com.xiaomi.channel.commonutils.logger.b.a(e5);
            a(jjVar);
            fo.a(this.f121a).b(this.f121a.getPackageName(), fn.m404a(i2), str, "19");
            s.c(this.f121a, jjVar, z2);
            return null;
        } catch (kd e6) {
            com.xiaomi.channel.commonutils.logger.b.a(e6);
            com.xiaomi.channel.commonutils.logger.b.d("receive a message which action string is not valid. is the reg expired?");
            fo.a(this.f121a).b(this.f121a.getPackageName(), fn.m404a(i2), str, "20");
            s.c(this.f121a, jjVar, z2);
            return null;
        }
    }

    private void a(String str, long j2, e eVar) {
        au auVarA = l.a(eVar);
        if (auVarA == null) {
            return;
        }
        if (j2 == 0) {
            synchronized (af.class) {
                try {
                    if (af.a(this.f121a).m118a(str)) {
                        af.a(this.f121a).c(str);
                        if ("syncing".equals(af.a(this.f121a).a(auVarA))) {
                            af.a(this.f121a).a(auVarA, "synced");
                        }
                    }
                } finally {
                }
            }
            return;
        }
        if ("syncing".equals(af.a(this.f121a).a(auVarA))) {
            synchronized (af.class) {
                try {
                    if (af.a(this.f121a).m118a(str)) {
                        if (af.a(this.f121a).a(str) < 10) {
                            af.a(this.f121a).b(str);
                            ao.a(this.f121a).a(str, auVarA, eVar, "retry");
                        } else {
                            af.a(this.f121a).c(str);
                        }
                    }
                } finally {
                }
            }
            return;
        }
        af.a(this.f121a).c(str);
    }

    private void a(je jeVar) {
        String strA = jeVar.a();
        com.xiaomi.channel.commonutils.logger.b.b("receive ack " + strA);
        Map<String, String> mapM574a = jeVar.m574a();
        if (mapM574a != null) {
            String str = mapM574a.get("real_source");
            if (TextUtils.isEmpty(str)) {
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.b("receive ack : messageId = " + strA + "  realSource = " + str);
            cf.a(this.f121a).a(strA, str, Boolean.valueOf(jeVar.f699a == 0));
        }
    }

    public List<String> a(TimeZone timeZone, TimeZone timeZone2, List<String> list) throws NumberFormatException {
        if (timeZone.equals(timeZone2)) {
            return list;
        }
        long rawOffset = ((timeZone.getRawOffset() - timeZone2.getRawOffset()) / 1000) / 60;
        long j2 = ((((Long.parseLong(list.get(0).split(":")[0]) * 60) + Long.parseLong(list.get(0).split(":")[1])) - rawOffset) + 1440) % 1440;
        long j3 = ((((Long.parseLong(list.get(1).split(":")[0]) * 60) + Long.parseLong(list.get(1).split(":")[1])) - rawOffset) + 1440) % 1440;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j2 / 60), Long.valueOf(j2 % 60)));
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j3 / 60), Long.valueOf(j3 % 60)));
        return arrayList;
    }

    private void a() {
        SharedPreferences sharedPreferences = this.f121a.getSharedPreferences("mipush_extra", 0);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (Math.abs(jCurrentTimeMillis - sharedPreferences.getLong(Constants.SP_KEY_LAST_REINITIALIZE, 0L)) > 1800000) {
            MiPushClient.reInitialize(this.f121a, jb.PackageUnregistered);
            sharedPreferences.edit().putLong(Constants.SP_KEY_LAST_REINITIALIZE, jCurrentTimeMillis).commit();
        }
    }

    private void a(jj jjVar) {
        com.xiaomi.channel.commonutils.logger.b.m91a("receive a message but decrypt failed. report now.");
        jm jmVar = new jm(jjVar.m593a().f654a, false);
        jmVar.c(ix.DecryptMessageFail.f620a);
        jmVar.b(jjVar.m594a());
        jmVar.d(jjVar.f748b);
        HashMap map = new HashMap();
        jmVar.f760a = map;
        map.put("regid", MiPushClient.getRegId(this.f121a));
        ao.a(this.f121a).a((ao) jmVar, in.Notification, false, (ja) null);
    }

    private void a(jq jqVar, jj jjVar) {
        ja jaVarM593a = jjVar.m593a();
        if (jaVarM593a != null) {
            jaVarM593a = bs.a(jaVarM593a.m558a());
        }
        jd jdVar = new jd();
        jdVar.b(jqVar.b());
        jdVar.a(jqVar.m631a());
        jdVar.a(jqVar.a().a());
        if (!TextUtils.isEmpty(jqVar.c())) {
            jdVar.c(jqVar.c());
        }
        if (!TextUtils.isEmpty(jqVar.d())) {
            jdVar.d(jqVar.d());
        }
        jdVar.a(jx.m659a(this.f121a, jjVar));
        ao.a(this.f121a).a((ao) jdVar, in.AckMessage, jaVarM593a);
    }

    private void a(jm jmVar) {
        je jeVar = new je();
        jeVar.c(ix.CancelPushMessageACK.f620a);
        jeVar.a(jmVar.m608a());
        jeVar.a(jmVar.a());
        jeVar.b(jmVar.b());
        jeVar.e(jmVar.c());
        jeVar.a(0L);
        jeVar.d("success clear push message.");
        ao.a(this.f121a).a(jeVar, in.Notification, false, true, null, false, this.f121a.getPackageName(), b.m140a(this.f121a).m141a(), false);
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m119a(Context context, String str) {
        synchronized (f119a) {
            try {
                b.m140a(context);
                SharedPreferences sharedPreferencesA = b.a(context);
                if (f120a == null) {
                    String[] strArrSplit = sharedPreferencesA.getString("pref_msg_ids", "").split(",");
                    f120a = new LinkedList();
                    for (String str2 : strArrSplit) {
                        f120a.add(str2);
                    }
                }
                if (f120a.contains(str)) {
                    return true;
                }
                f120a.add(str);
                if (f120a.size() > 25) {
                    f120a.poll();
                }
                String strA = bp.a(f120a, ",");
                SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
                editorEdit.putString("pref_msg_ids", strA);
                com.xiaomi.push.p.a(editorEdit);
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void a(Context context, String str) {
        synchronized (f119a) {
            f120a.remove(str);
            b.m140a(context);
            SharedPreferences sharedPreferencesA = b.a(context);
            String strA = bp.a(f120a, ",");
            SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
            editorEdit.putString("pref_msg_ids", strA);
            com.xiaomi.push.p.a(editorEdit);
        }
    }

    public static Intent a(Context context, String str, Map<String, String> map, int i2) {
        return com.xiaomi.push.service.al.b(context, str, map, i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m120a(jj jjVar) {
        Map<String, String> mapM560a = jjVar.m593a() == null ? null : jjVar.m593a().m560a();
        if (mapM560a == null) {
            return false;
        }
        String str = mapM560a.get(Constants.EXTRA_KEY_PUSH_SERVER_ACTION);
        return TextUtils.equals(str, Constants.EXTRA_VALUE_HYBRID_MESSAGE) || TextUtils.equals(str, Constants.EXTRA_VALUE_PLATFORM_MESSAGE);
    }
}
