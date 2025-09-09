package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.push.in;
import com.xiaomi.push.jj;
import com.xiaomi.push.jq;
import com.xiaomi.push.jx;
import com.xiaomi.push.service.bj;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class FCMPushHelper {
    private static void a(Context context, jj jjVar) {
        try {
            MiPushMessage miPushMessageGenerateMessage = PushMessageHelper.generateMessage((jq) ai.a(context, jjVar), jjVar.m593a(), false);
            PushMessageReceiver pushMessageReceiverA = i.a(context);
            if (pushMessageReceiverA != null) {
                pushMessageReceiverA.onNotificationMessageArrived(context, miPushMessageGenerateMessage);
            }
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a("fcm broadcast notification come error ", th);
        }
    }

    private static boolean b(Context context) {
        return context.getSharedPreferences("mipush_extra", 0).getBoolean("is_xmsf_sup_decrypt", false);
    }

    public static void clearToken(Context context) {
        i.m161a(context, e.ASSEMBLE_PUSH_FCM);
    }

    public static void convertMessage(Intent intent) {
        i.a(intent);
    }

    public static boolean isFCMSwitchOpen(Context context) {
        return i.m164a(context, e.ASSEMBLE_PUSH_FCM) && MiPushClient.getOpenFCMPush(context);
    }

    public static void notifyFCMNotificationCome(Context context, Map<String, String> map) {
        PushMessageReceiver pushMessageReceiverA;
        String str = map.get("pushMsg");
        if (TextUtils.isEmpty(str) || (pushMessageReceiverA = i.a(context)) == null) {
            return;
        }
        pushMessageReceiverA.onNotificationMessageArrived(context, i.a(str));
    }

    public static Map<String, String> notifyFCMPassThoughMessageCome(Context context, Map<String, String> map) {
        PushMessageReceiver pushMessageReceiverA;
        String str = map.get("pushMsg");
        if (!TextUtils.isEmpty(str) && (pushMessageReceiverA = i.a(context)) != null) {
            pushMessageReceiverA.onReceivePassThroughMessage(context, i.a(str));
        }
        String str2 = map.get("mipushContainer");
        if (TextUtils.isEmpty(str2)) {
            return new HashMap();
        }
        try {
            byte[] bArrDecode = Base64.decode(str2, 2);
            a(context, com.xiaomi.push.service.z.a(bArrDecode));
            a(context, bArrDecode);
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a("fcm notify notification error ", th);
        }
        return a(context);
    }

    public static void persistIfXmsfSupDecrypt(Context context) {
        context.getSharedPreferences("mipush_extra", 0).edit().putBoolean("is_xmsf_sup_decrypt", ((long) com.xiaomi.push.j.b(context)) >= 50002000).apply();
    }

    public static void reportFCMMessageDelete() {
        MiTinyDataClient.upload(i.c(e.ASSEMBLE_PUSH_FCM), "fcm", 1L, "some fcm messages was deleted ");
    }

    public static void uploadToken(Context context, String str) {
        i.m162a(context, e.ASSEMBLE_PUSH_FCM, str);
    }

    private static Map<String, String> a(Context context) {
        HashMap map = new HashMap();
        map.put("actionType", String.valueOf(in.AckMessage.a()));
        map.put("deviceStatus", String.valueOf((int) jx.m660a(context, context.getPackageName())));
        map.put("mat", Long.toString(System.currentTimeMillis()));
        return map;
    }

    private static void a(Context context, byte[] bArr) {
        boolean z2 = false;
        boolean zM133a = ao.a(context).m133a();
        boolean z3 = !"com.xiaomi.xmsf".equals(context.getPackageName());
        boolean zM109a = m109a(context);
        if (zM133a && z3 && zM109a) {
            bArr = com.xiaomi.push.service.o.a(bArr, b.m140a(context).d());
            if (bArr == null) {
                com.xiaomi.channel.commonutils.logger.b.m91a("fcm message encrypt failed");
            } else {
                String strEncodeToString = Base64.encodeToString(bArr, 2);
                if (TextUtils.isEmpty(strEncodeToString)) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("fcm message buf base64 encode failed");
                } else {
                    Intent intent = new Intent(bj.f24530n);
                    intent.setPackage("com.xiaomi.xmsf");
                    intent.setClassName("com.xiaomi.xmsf", "com.xiaomi.push.service.XMPushService");
                    intent.putExtra("ext_fcm_container_buffer", strEncodeToString);
                    intent.putExtra("mipush_app_package", context.getPackageName());
                    context.startService(intent);
                    com.xiaomi.channel.commonutils.logger.b.m91a("fcm message reroute to xmsf");
                    z2 = true;
                }
            }
        } else {
            com.xiaomi.channel.commonutils.logger.b.m91a(String.format("xmsf can not receive fcm msg - shouldUseMIUIPush=%s;isNotXmsf=%s;xmsfSupport=%s", Boolean.valueOf(zM133a), Boolean.valueOf(z3), Boolean.valueOf(zM109a)));
        }
        if (z2) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.b("fcm message post local");
        com.xiaomi.push.service.al.m723a(context, com.xiaomi.push.service.z.a(bArr), bArr);
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m109a(Context context) {
        return ((long) com.xiaomi.push.j.b(context)) >= 50002000 && b(context);
    }
}
