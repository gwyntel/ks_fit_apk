package com.meizu.cloud.pushsdk.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.f.d.b;
import com.meizu.cloud.pushsdk.f.g.e;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.umeng.analytics.pro.f;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d {

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f19828a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ Intent f19829b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ long f19830c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ boolean f19831d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ Map f19832e;

        a(Context context, Intent intent, long j2, boolean z2, Map map) {
            this.f19828a = context;
            this.f19829b = intent;
            this.f19830c = j2;
            this.f19831d = z2;
            this.f19832e = map;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.f19828a.startService(this.f19829b);
                DebugLogger.i("UxIPUtils", "delayed " + this.f19830c + " ms start tracker data in mz_tracker process " + this.f19829b.getStringExtra(PushConstants.EXTRA_PUSH_TRACKER_JSON_DATA));
            } catch (Exception e2) {
                e2.printStackTrace();
                DebugLogger.e("UxIPUtils", "delayed startRemotePushTracker error " + e2.getMessage());
                d.a(this.f19828a, this.f19831d, (String) this.f19832e.get("en"), (Map<String, String>) this.f19832e);
            }
        }
    }

    @SuppressLint({"QueryPermissionsNeeded"})
    private static Intent a(Context context, Map<String, String> map) {
        String str;
        String str2;
        List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(new Intent(PushConstants.MZ_PUSH_TRACKER_SERVICE_ACTION), 0);
        if (listQueryIntentServices != null) {
            Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = null;
                    str2 = null;
                    break;
                }
                ResolveInfo next = it.next();
                if (PushConstants.PUSH_PACKAGE_NAME.equals(next.serviceInfo.packageName)) {
                    ServiceInfo serviceInfo = next.serviceInfo;
                    str2 = serviceInfo.packageName;
                    str = serviceInfo.name;
                    break;
                }
            }
            if (TextUtils.isEmpty(str) && listQueryIntentServices.size() > 0) {
                str2 = listQueryIntentServices.get(0).serviceInfo.packageName;
                str = listQueryIntentServices.get(0).serviceInfo.name;
            }
        } else {
            str = null;
            str2 = null;
        }
        DebugLogger.i("UxIPUtils", "current process packageName " + str2);
        if (!TextUtils.isEmpty(str)) {
            try {
                String string = e.a((Map) map).toString();
                Intent intent = new Intent();
                intent.setPackage(str2);
                intent.setAction(PushConstants.MZ_PUSH_TRACKER_SERVICE_ACTION);
                intent.putExtra(PushConstants.EXTRA_PUSH_TRACKER_JSON_DATA, string);
                return intent;
            } catch (Exception e2) {
                e2.printStackTrace();
                DebugLogger.e("UxIPUtils", "getRemotePushTrackerIntent error " + e2.getMessage());
            }
        }
        return null;
    }

    public static void b(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, true, str, str2, str3, str4, "acce", str5);
    }

    public static void c(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, true, str, str2, str3, str4, "cpm", str5);
    }

    public static void d(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, true, str, str2, str3, str4, "dpm", str5);
    }

    public static void e(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, true, str, str2, str3, str4, "iopm", str5);
    }

    public static void f(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, true, str, str2, str3, str4, "ipm", str5);
    }

    public static void g(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, false, str, str2, str3, str4, "rpe", str5);
    }

    public static void h(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, true, str, str2, str3, str4, "rpe", str5);
    }

    public static com.meizu.cloud.pushsdk.handler.e.j.d a(String str) {
        String str2;
        com.meizu.cloud.pushsdk.handler.e.j.d dVar = new com.meizu.cloud.pushsdk.handler.e.j.d();
        if (TextUtils.isEmpty(str)) {
            str2 = "the platformExtra is empty";
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                return com.meizu.cloud.pushsdk.handler.e.j.d.a().d(jSONObject.has("task_id") ? jSONObject.getString("task_id") : null).a(jSONObject.has("device_id") ? jSONObject.getString("device_id") : null).b(jSONObject.has(PushConstants.PUSH_TIMESTAMP) ? jSONObject.getString(PushConstants.PUSH_TIMESTAMP) : null).c(jSONObject.has(PushConstants.SEQ_ID) ? jSONObject.getString(PushConstants.SEQ_ID) : null).a();
            } catch (Exception unused) {
                str2 = "the platformExtra parse error";
            }
        }
        DebugLogger.e("UxIPUtils", str2);
        return dVar;
    }

    public static void b(Context context, String str, String str2, String str3, String str4, String str5, long j2) {
        a(context, false, str, str2, str3, str4, "npm", str5, j2);
    }

    public static void c(Context context, String str, String str2, String str3, String str4, String str5, long j2) {
        a(context, false, str, str2, str3, str4, "nspm", str5, j2);
    }

    public static void d(Context context, String str, String str2, String str3, String str4, String str5, long j2) {
        a(context, false, str, str2, str3, str4, "rpe", str5, j2);
    }

    public static void e(Context context, String str, String str2, String str3, String str4, String str5, long j2) {
        a(context, false, str, str2, str3, str4, "sipm", str5, j2);
    }

    public static void f(Context context, String str, String str2, String str3, String str4, String str5, long j2) {
        a(context, true, str, str2, str3, str4, "spm", str5, j2);
    }

    public static String a(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_ID);
        if (!TextUtils.isEmpty(stringExtra)) {
            return stringExtra;
        }
        try {
            MPushMessage mPushMessage = (MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
            return mPushMessage != null ? mPushMessage.getTaskId() : stringExtra;
        } catch (Exception e2) {
            DebugLogger.e("UxIPUtils", "parse MessageV2 error " + e2.getMessage());
            return "no push platform task";
        }
    }

    public static void a(Context context, Intent intent, String str, int i2) {
        a(context, intent, PushManager.TAG, str, i2);
    }

    public static void a(Context context, Intent intent, String str, String str2, int i2) {
        if (TextUtils.isEmpty(a(intent))) {
            return;
        }
        a(context, context.getPackageName(), intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY), a(intent), str, str2, i2);
    }

    public static void a(Context context, String str, int i2, String str2, String str3) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        a(context, context.getPackageName(), str3, str2, PushManager.TAG, str, i2);
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, true, str, str2, str3, str4, "acsm", str5);
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5, int i2) {
        HashMap map = new HashMap(8);
        map.put(AlinkConstants.KEY_TASK_ID, str3);
        map.put("deviceId", str2);
        map.put(com.alipay.sdk.m.t.a.f9743k, String.valueOf(System.currentTimeMillis() / 1000));
        map.put("package_name", str);
        map.put("pushsdk_version", str4);
        map.put("push_info", str5);
        map.put("push_info_type", String.valueOf(i2));
        a(context, false, "notification_service_message", (Map<String, String>) map);
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5, long j2) {
        a(context, false, str, str2, str3, str4, "fspm", str5, j2);
    }

    public static void a(Context context, boolean z2, String str, String str2, String str3, String str4, String str5, String str6) {
        a(context, z2, str, str2, str3, str4, str5, str6, 0L);
    }

    public static void a(Context context, boolean z2, String str, String str2, String str3, String str4, String str5, String str6, long j2) {
        HashMap map = new HashMap(8);
        map.put("en", str5);
        map.put("ti", str3);
        map.put(AppIconSetting.DEFAULT_LARGE_ICON, str2);
        map.put("fdId", com.meizu.cloud.pushsdk.d.c.b(context));
        if (TextUtils.isEmpty(str6)) {
            str6 = String.valueOf(System.currentTimeMillis() / 1000);
        }
        map.put("ts", str6);
        map.put("pn", str);
        map.put(f.T, PushManager.TAG);
        map.put("nm", String.valueOf(System.currentTimeMillis() / 1000));
        if (!TextUtils.isEmpty(str4)) {
            map.put("si", str4);
        }
        if (a(context, map, z2, j2)) {
            return;
        }
        a(context, z2, str5, map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void a(Context context, boolean z2, String str, Map<String, String> map) {
        DebugLogger.e("UxIPUtils", "onLogEvent eventName [" + str + "] properties = " + map);
        if ("notification_service_message".equals(str)) {
            return;
        }
        com.meizu.cloud.pushsdk.f.a.a(context, (com.meizu.cloud.pushsdk.e.d.a) null, (com.meizu.cloud.pushsdk.f.c.f) null).a(((b.c) com.meizu.cloud.pushsdk.f.d.b.d().c(str).a(1).a(Long.valueOf(map.get("ts")).longValue())).b(map.get("nm") != null ? map.get("nm") : String.valueOf(System.currentTimeMillis() / 1000)).a(map.get(AppIconSetting.DEFAULT_LARGE_ICON)).d(map.get("fdId")).f(map.get("pn")).g(map.get(f.T)).i(map.get("ti")).h(TextUtils.isEmpty(map.get("si")) ? "" : map.get("si")).e(String.valueOf(b.e(context, map.get("pn")))).b(), z2);
    }

    private static boolean a(Context context, Intent intent, boolean z2, Map<String, String> map, long j2) {
        String str;
        if (intent == null) {
            str = "startRemotePushTracker error intent is null";
        } else {
            if (j2 != 0) {
                new Handler(context.getMainLooper()).postDelayed(new a(context, intent, j2, z2, map), j2);
                return true;
            }
            try {
                context.startService(intent);
                DebugLogger.i("UxIPUtils", "immediately start tracker data in mz_tracker process " + intent.getStringExtra(PushConstants.EXTRA_PUSH_TRACKER_JSON_DATA));
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                str = "startRemotePushTracker error " + e2.getMessage();
            }
        }
        DebugLogger.e("UxIPUtils", str);
        return false;
    }

    private static boolean a(Context context, Map<String, String> map, boolean z2, long j2) {
        return a(context, a(context, map), z2, map, j2);
    }
}
