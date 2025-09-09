package com.umeng.message.proguard;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.umeng.message.common.UPLog;
import com.vivo.push.PushClientConstants;

/* loaded from: classes4.dex */
public final class aw {

    /* renamed from: a, reason: collision with root package name */
    private static Boolean f22801a;

    /* renamed from: b, reason: collision with root package name */
    private static int f22802b;

    /* renamed from: c, reason: collision with root package name */
    private static final Handler f22803c = new Handler(Looper.getMainLooper()) { // from class: com.umeng.message.proguard.aw.4
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i2 = message.what;
            if (1 == i2) {
                aw.a(message.arg1);
                removeMessages(2);
                Message messageObtainMessage = obtainMessage(2);
                messageObtainMessage.obj = message.obj;
                message.obj = null;
                sendMessageDelayed(messageObtainMessage, 500L);
                return;
            }
            if (2 == i2) {
                final int i3 = aw.f22802b;
                aw.c();
                final Context context = (Context) message.obj;
                message.obj = null;
                if (context == null) {
                    return;
                }
                b.c(new Runnable() { // from class: com.umeng.message.proguard.aw.4.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        String type;
                        if (aw.f22801a == null || aw.f22801a.booleanValue()) {
                            try {
                                ComponentName componentNameA = aw.a(context);
                                if (componentNameA == null) {
                                    Boolean unused = aw.f22801a = Boolean.FALSE;
                                    return;
                                }
                                Uri uri = Uri.parse("content://com.huawei.android.launcher.settings/badge/");
                                try {
                                    type = context.getContentResolver().getType(uri);
                                } catch (Throwable unused2) {
                                    type = null;
                                }
                                if (TextUtils.isEmpty(type)) {
                                    uri = Uri.parse("content://com.hihonor.android.launcher.settings/badge/");
                                    type = context.getContentResolver().getType(uri);
                                }
                                if (type == null) {
                                    Boolean unused3 = aw.f22801a = Boolean.FALSE;
                                    return;
                                }
                                UPLog.i("Badge", "hw changeBadgeNum:", Integer.valueOf(i3));
                                Bundle bundle = new Bundle();
                                bundle.putString("package", componentNameA.getPackageName());
                                bundle.putString("class", componentNameA.getClassName());
                                Bundle bundleCall = context.getContentResolver().call(uri, "getbadgeNumber", (String) null, bundle);
                                int i4 = bundleCall != null ? bundleCall.getInt("badgenumber", 0) : 0;
                                int iMax = Math.max(0, i3 + i4);
                                if (i4 != iMax) {
                                    bundle.putInt("badgenumber", iMax);
                                    context.getContentResolver().call(uri, "change_badge", (String) null, bundle);
                                }
                                UPLog.d("Badge", "hw changeBadgeNum cur:", Integer.valueOf(iMax), "last:", Integer.valueOf(i4));
                                Boolean unused4 = aw.f22801a = Boolean.TRUE;
                            } catch (Throwable unused5) {
                                Boolean unused6 = aw.f22801a = Boolean.FALSE;
                            }
                        }
                    }
                });
            }
        }
    };

    static /* synthetic */ int a(int i2) {
        int i3 = f22802b + i2;
        f22802b = i3;
        return i3;
    }

    static /* synthetic */ int c() {
        f22802b = 0;
        return 0;
    }

    public static void b(Context context, int i2) {
        Boolean bool = f22801a;
        if (bool == null || bool.booleanValue()) {
            String str = Build.MANUFACTURER;
            if ("huawei".equalsIgnoreCase(str) || "honor".equalsIgnoreCase(str)) {
                Handler handler = f22803c;
                Message messageObtainMessage = handler.obtainMessage(1);
                messageObtainMessage.arg1 = i2;
                messageObtainMessage.obj = context;
                handler.sendMessage(messageObtainMessage);
            }
        }
    }

    public static void a(final Context context, final int i2) {
        Boolean bool = f22801a;
        if (bool == null || bool.booleanValue()) {
            String str = Build.MANUFACTURER;
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if ("oppo".equalsIgnoreCase(str)) {
                b.b(new Runnable() { // from class: com.umeng.message.proguard.aw.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (aw.f22801a == null || aw.f22801a.booleanValue()) {
                            try {
                                Bundle bundle = new Bundle();
                                bundle.putInt("app_badge_count", i2);
                                context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", (String) null, bundle);
                                UPLog.d("Badge", "oppo setBadgeNum:", Integer.valueOf(i2));
                                Boolean unused = aw.f22801a = Boolean.TRUE;
                            } catch (Throwable unused2) {
                                Boolean unused3 = aw.f22801a = Boolean.FALSE;
                            }
                        }
                    }
                });
                return;
            }
            if ("vivo".equalsIgnoreCase(str)) {
                b.b(new Runnable() { // from class: com.umeng.message.proguard.aw.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (aw.f22801a == null || aw.f22801a.booleanValue()) {
                            try {
                                ComponentName componentNameA = aw.a(context);
                                if (componentNameA == null) {
                                    Boolean unused = aw.f22801a = Boolean.FALSE;
                                    return;
                                }
                                Intent intent = new Intent();
                                intent.setAction("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
                                intent.putExtra("packageName", componentNameA.getPackageName());
                                intent.putExtra(PushClientConstants.TAG_CLASS_NAME, componentNameA.getClassName());
                                intent.putExtra("notificationNum", i2);
                                if (Build.VERSION.SDK_INT >= 26) {
                                    intent.addFlags(16777216);
                                }
                                context.sendBroadcast(intent);
                                UPLog.d("Badge", "vivo setBadgeNum:", Integer.valueOf(i2));
                                Boolean unused2 = aw.f22801a = Boolean.TRUE;
                            } catch (Throwable unused3) {
                                Boolean unused4 = aw.f22801a = Boolean.FALSE;
                            }
                        }
                    }
                });
            } else if ("huawei".equalsIgnoreCase(str) || "honor".equalsIgnoreCase(str)) {
                b.b(new Runnable() { // from class: com.umeng.message.proguard.aw.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        String type;
                        if (aw.f22801a == null || aw.f22801a.booleanValue()) {
                            try {
                                Uri uri = Uri.parse("content://com.huawei.android.launcher.settings/badge/");
                                try {
                                    type = context.getContentResolver().getType(uri);
                                } catch (Throwable unused) {
                                    type = null;
                                }
                                if (TextUtils.isEmpty(type)) {
                                    uri = Uri.parse("content://com.hihonor.android.launcher.settings/badge/");
                                    type = context.getContentResolver().getType(uri);
                                }
                                if (type == null) {
                                    Boolean unused2 = aw.f22801a = Boolean.FALSE;
                                    return;
                                }
                                ComponentName componentNameA = aw.a(context);
                                if (componentNameA == null) {
                                    Boolean unused3 = aw.f22801a = Boolean.FALSE;
                                    return;
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("package", componentNameA.getPackageName());
                                bundle.putString("class", componentNameA.getClassName());
                                bundle.putInt("badgenumber", i2);
                                context.getContentResolver().call(uri, "change_badge", (String) null, bundle);
                                UPLog.d("Badge", "hw setBadgeNum:", Integer.valueOf(i2));
                                Boolean unused4 = aw.f22801a = Boolean.TRUE;
                            } catch (Throwable unused5) {
                                Boolean unused6 = aw.f22801a = Boolean.FALSE;
                            }
                        }
                    }
                });
            }
        }
    }

    static /* synthetic */ ComponentName a(Context context) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntentForPackage != null) {
            return launchIntentForPackage.getComponent();
        }
        return null;
    }
}
