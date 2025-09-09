package com.google.firebase.messaging;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes3.dex */
final class ProxyNotificationPreferences {
    private static final String FCM_PREFERENCES = "com.google.firebase.messaging";

    private ProxyNotificationPreferences() {
    }

    static boolean a(Context context) {
        return getPreference(context).getBoolean("proxy_notification_initialized", false);
    }

    static void b(Context context, boolean z2) {
        SharedPreferences.Editor editorEdit = getPreference(context).edit();
        editorEdit.putBoolean("proxy_notification_initialized", z2);
        editorEdit.apply();
    }

    private static SharedPreferences getPreference(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        return context.getSharedPreferences("com.google.firebase.messaging", 0);
    }
}
