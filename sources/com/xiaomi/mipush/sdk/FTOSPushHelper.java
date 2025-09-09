package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import java.util.Map;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class FTOSPushHelper {

    /* renamed from: a, reason: collision with root package name */
    private static long f23354a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static volatile boolean f99a = false;

    private static void a(Context context) {
        AbstractPushManager abstractPushManagerA = f.a(context).a(e.ASSEMBLE_PUSH_FTOS);
        if (abstractPushManagerA != null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH :  register fun touch os when network change!");
            abstractPushManagerA.register();
        }
    }

    public static void doInNetworkChange(Context context) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (getNeedRegister()) {
            long j2 = f23354a;
            if (j2 <= 0 || j2 + 300000 <= jElapsedRealtime) {
                f23354a = jElapsedRealtime;
                a(context);
            }
        }
    }

    public static boolean getNeedRegister() {
        return f99a;
    }

    public static boolean hasNetwork(Context context) {
        return i.m163a(context);
    }

    public static void notifyFTOSNotificationClicked(Context context, Map<String, String> map) throws JSONException {
        PushMessageReceiver pushMessageReceiverA;
        if (map == null || !map.containsKey("pushMsg")) {
            return;
        }
        String str = map.get("pushMsg");
        if (TextUtils.isEmpty(str) || (pushMessageReceiverA = i.a(context)) == null) {
            return;
        }
        MiPushMessage miPushMessageA = i.a(str);
        if (miPushMessageA.getExtra().containsKey("notify_effect")) {
            return;
        }
        pushMessageReceiverA.onNotificationMessageClicked(context, miPushMessageA);
    }

    public static void setNeedRegister(boolean z2) {
        f99a = z2;
    }

    public static void uploadToken(Context context, String str) {
        i.m162a(context, e.ASSEMBLE_PUSH_FTOS, str);
    }
}
