package com.aliyun.iot.aep.sdk.framework.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class BroadCastUtil {

    /* renamed from: a, reason: collision with root package name */
    private static HashMap<String, Intent> f11834a = new HashMap<>(100);

    public static void broadCast(Context context, Intent intent) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void broadCastSticky(Context context, Intent intent) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        f11834a.put(intent.getAction(), intent);
    }

    public static void register(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter);
        for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
            Intent intent = f11834a.get(intentFilter.getAction(i2));
            if (intent != null) {
                broadcastReceiver.onReceive(context, intent);
            }
        }
    }

    public static void unRegister(Context context, BroadcastReceiver broadcastReceiver) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }
}
