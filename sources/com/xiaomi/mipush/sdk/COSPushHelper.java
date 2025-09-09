package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/* loaded from: classes4.dex */
public class COSPushHelper {

    /* renamed from: a, reason: collision with root package name */
    private static long f23353a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static volatile boolean f98a = false;

    public static void convertMessage(Intent intent) {
        i.a(intent);
    }

    public static void doInNetworkChange(Context context) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (getNeedRegister()) {
            long j2 = f23353a;
            if (j2 <= 0 || j2 + 300000 <= jElapsedRealtime) {
                f23353a = jElapsedRealtime;
                registerCOSAssemblePush(context);
            }
        }
    }

    public static boolean getNeedRegister() {
        return f98a;
    }

    public static boolean hasNetwork(Context context) {
        return i.m163a(context);
    }

    public static void onNotificationMessageCome(Context context, String str) {
    }

    public static void onPassThoughMessageCome(Context context, String str) {
    }

    public static void registerCOSAssemblePush(Context context) {
        AbstractPushManager abstractPushManagerA = f.a(context).a(e.ASSEMBLE_PUSH_COS);
        if (abstractPushManagerA != null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH :  register cos when network change!");
            abstractPushManagerA.register();
        }
    }

    public static synchronized void setNeedRegister(boolean z2) {
        f98a = z2;
    }

    public static void uploadToken(Context context, String str) {
        i.m162a(context, e.ASSEMBLE_PUSH_COS, str);
    }
}
