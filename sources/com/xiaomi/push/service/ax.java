package com.xiaomi.push.service;

import android.app.Notification;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public class ax {

    /* renamed from: a, reason: collision with root package name */
    public static final a<String, String, String> f24477a;

    /* renamed from: b, reason: collision with root package name */
    public static final a<String, String, String> f24478b;

    /* renamed from: c, reason: collision with root package name */
    public static final a<String, String, String> f24479c;

    /* renamed from: d, reason: collision with root package name */
    public static final a<String, String, String> f24480d;

    /* renamed from: e, reason: collision with root package name */
    public static final a<String, String, String> f24481e;

    /* renamed from: f, reason: collision with root package name */
    public static final a<String, String, String> f24482f;

    /* renamed from: g, reason: collision with root package name */
    public static final a<String, String, String> f24483g;

    /* renamed from: h, reason: collision with root package name */
    public static final a<String, String, String> f24484h;

    /* renamed from: a, reason: collision with other field name */
    private static final String[] f1024a = {"com.mi.globalbrowser", "com.android.browser"};

    /* renamed from: a, reason: collision with other field name */
    private static String f1023a = null;

    public static class a<F, S, T> {

        /* renamed from: a, reason: collision with root package name */
        F f24485a;

        /* renamed from: b, reason: collision with root package name */
        S f24486b;

        /* renamed from: c, reason: collision with root package name */
        T f24487c;

        private a(F f2, S s2, T t2) {
            this.f24485a = f2;
            this.f24486b = s2;
            this.f24487c = t2;
        }
    }

    static {
        String str = "getNotificationSettings";
        f24477a = new a<>(str, str, str);
        String str2 = "canSound";
        f24478b = new a<>("setSound", str2, str2);
        String str3 = "canVibrate";
        f24479c = new a<>("setVibrate", str3, str3);
        String str4 = "canLights";
        f24480d = new a<>("setLights", str4, str4);
        String str5 = "canShowOnKeyguard";
        f24481e = new a<>("setShowOnKeyguard", str5, str5);
        f24482f = new a<>("setFloat", "canFloat", "canShowFloat");
        String str6 = "canShowBadge";
        f24483g = new a<>("setShowBadge", str6, str6);
        String str7 = "canShowOngoing";
        f24484h = new a<>("setShowOngoing", str7, str7);
    }

    public static String a(Notification notification) {
        CharSequence charSequence;
        Bundle bundle = notification.extras;
        if (bundle != null) {
            charSequence = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE);
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notification.extras.getCharSequence(NotificationCompat.EXTRA_TITLE_BIG);
            }
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notification.extras.getCharSequence("mipush.customTitle");
            }
        } else {
            charSequence = null;
        }
        return charSequence != null ? charSequence.toString() : "";
    }

    public static String b(Notification notification) {
        CharSequence charSequence;
        Bundle bundle = notification.extras;
        if (bundle != null) {
            charSequence = bundle.getCharSequence(NotificationCompat.EXTRA_TEXT);
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notification.extras.getCharSequence(NotificationCompat.EXTRA_BIG_TEXT);
            }
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notification.extras.getCharSequence("mipush.customContent");
            }
        } else {
            charSequence = null;
        }
        return charSequence != null ? charSequence.toString() : "";
    }

    public static String c(Notification notification) {
        Object objA;
        try {
            Bundle bundle = notification.extras;
            string = bundle != null ? bundle.getString(HiAnalyticsConstant.BI_KEY_TARGET_PACKAGE) : null;
            return (!TextUtils.isEmpty(string) || (objA = com.xiaomi.push.bk.a(notification, "extraNotification")) == null) ? string : (String) com.xiaomi.push.bk.a(objA, "getTargetPkg", new Object[0]);
        } catch (Exception unused) {
            return string;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static Notification.Action[] m752a(Notification notification) {
        Parcelable[] parcelableArray;
        Notification.Action[] actionArr = notification.actions;
        if (actionArr != null) {
            return actionArr;
        }
        Bundle bundle = notification.extras;
        if (bundle == null || (parcelableArray = bundle.getParcelableArray("mipush.customActions")) == null) {
            return null;
        }
        return (Notification.Action[]) Arrays.copyOf(parcelableArray, parcelableArray.length, Notification.Action[].class);
    }

    public static void b(Notification notification, boolean z2) {
        try {
            Bundle bundle = notification.extras;
            if (bundle != null) {
                bundle.putBoolean("miui.enableKeyguard", z2);
            }
            Object objA = com.xiaomi.push.bk.a(notification, "extraNotification");
            if (objA != null) {
                com.xiaomi.push.bk.a(objA, "setEnableKeyguard", Boolean.valueOf(z2));
            }
        } catch (Exception unused) {
        }
    }

    public static <T> T a(Notification notification, String str) {
        Bundle bundle = notification.extras;
        if (bundle == null) {
            return null;
        }
        try {
            return (T) bundle.get(str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void a(Map<String, String> map, Bundle bundle, String str) {
        if (map != null && bundle != null && !TextUtils.isEmpty(str)) {
            if (TextUtils.isEmpty(map.get(str))) {
                bundle.remove(str);
                return;
            } else {
                bundle.putString(str, map.get(str));
                return;
            }
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("cp map to b fail:" + str);
    }

    /* renamed from: a, reason: collision with other method in class */
    static void m750a(Notification notification, String str) {
        try {
            Bundle bundle = notification.extras;
            if (bundle != null) {
                bundle.putString(HiAnalyticsConstant.BI_KEY_TARGET_PACKAGE, str);
            }
            Object objA = com.xiaomi.push.bk.a(notification, "extraNotification");
            if (objA != null) {
                com.xiaomi.push.bk.a(objA, "setTargetPkg", str);
            }
        } catch (Exception unused) {
        }
    }

    static void a(Notification notification, boolean z2) {
        try {
            Bundle bundle = notification.extras;
            if (bundle != null) {
                bundle.putBoolean("miui.enableFloat", z2);
            }
            Object objA = com.xiaomi.push.bk.a(notification, "extraNotification");
            if (objA != null) {
                com.xiaomi.push.bk.a(objA, "setEnableFloat", Boolean.valueOf(z2));
            }
        } catch (Exception unused) {
        }
    }

    static void a(Notification notification, int i2) {
        try {
            Bundle bundle = notification.extras;
            if (bundle != null) {
                bundle.putInt("miui.messageCount", i2);
            }
            Object objA = com.xiaomi.push.bk.a(notification, "extraNotification");
            if (objA != null) {
                com.xiaomi.push.bk.a(objA, "setMessageCount", Integer.valueOf(i2));
            }
        } catch (Exception unused) {
        }
    }

    static void a(Notification notification, int i2, int i3) {
        if (notification != null) {
            if (notification.extras == null) {
                notification.extras = new Bundle();
            }
            notification.extras.putInt("is_priority", i2);
            notification.extras.putInt("mipush_class", i3);
        }
    }

    public static String a(Object obj) {
        return (String) a(obj, "msg_busi_type", "");
    }

    public static <T> T a(Object obj, String str, T t2) {
        Object objA = null;
        try {
            if (obj instanceof Notification) {
                objA = a((Notification) obj, str);
            } else if (obj instanceof Map) {
                objA = ((Map) obj).get(str);
            } else if (obj instanceof Bundle) {
                objA = ((Bundle) obj).get(str);
            } else {
                com.xiaomi.channel.commonutils.logger.b.m91a("not support get value from classType:" + obj);
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("get value error " + e2);
        }
        return objA == null ? t2 : (T) objA;
    }

    static int a(Context context, String str) {
        return com.xiaomi.push.g.b(context, str);
    }

    static void a(Context context, String str, Intent intent) {
        if (intent == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str)) {
            arrayList.add(str);
        }
        arrayList.addAll(Arrays.asList(f1024a));
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            String str2 = (String) arrayList.get(i2);
            if (!TextUtils.isEmpty(str2)) {
                Intent intent2 = new Intent(intent);
                intent2.setPackage(str2);
                try {
                    if (context.getPackageManager().resolveActivity(intent2, 65536) != null) {
                        intent.setPackage(str2);
                        break;
                    }
                    continue;
                } catch (Exception e2) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("can't match url intent. " + e2);
                }
            }
        }
        intent.setPackage(intent.getPackage());
    }

    public static int a(ContentResolver contentResolver) {
        try {
            return Settings.Global.getInt(contentResolver, "user_aggregate", 0);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("get user aggregate failed, " + e2);
            return 0;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m751a(ContentResolver contentResolver) {
        int iA = a(contentResolver);
        return iA == 1 || iA == 2;
    }

    public static boolean a(Map<String, String> map) {
        return Boolean.parseBoolean((String) a(map, "not_suppress", "true"));
    }

    public static boolean a(Notification.Builder builder, boolean z2) {
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setGroupAlertBehavior(z2 ? 2 : 1);
            return true;
        }
        com.xiaomi.channel.commonutils.logger.b.b("not support setGroupAlertBehavior");
        return false;
    }

    public static int a(Context context, String str, String str2, a<String, String, String> aVar) {
        if (aVar == null) {
            return -1;
        }
        try {
            Bundle bundleA = a(context, aVar.f24486b, str, str2, (Bundle) null);
            if (bundleA == null || !bundleA.containsKey(aVar.f24487c)) {
                return -1;
            }
            return bundleA.getBoolean(aVar.f24487c) ? 1 : 0;
        } catch (Exception unused) {
            return -1;
        }
    }

    public static Bundle a(Context context, String str, String str2) {
        try {
            return a(context, f24477a.f24486b, str, str2, (Bundle) null);
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean a(Context context, String str, String str2, a<String, String, String> aVar, boolean z2) {
        if (aVar != null) {
            try {
                Bundle bundle = new Bundle();
                bundle.putBoolean(aVar.f24487c, z2);
                a(context, aVar.f24485a, str, str2, bundle);
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private static Bundle a(Context context, String str, String str2, String str3, Bundle bundle) {
        if (context != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("package", str2);
            if (!TextUtils.isEmpty(str3)) {
                bundle2.putString("channel_id", str3);
            }
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            return context.getContentResolver().call(Uri.parse("content://statusbar.notification"), str, (String) null, bundle2);
        }
        throw new IllegalArgumentException("call notification provider failed!");
    }
}
