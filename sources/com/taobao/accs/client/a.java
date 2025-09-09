package com.taobao.accs.client;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.IProcessName;
import com.taobao.accs.utl.ALog;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static String f20079a;

    /* renamed from: b, reason: collision with root package name */
    public static String f20080b;

    /* renamed from: c, reason: collision with root package name */
    public static String f20081c;

    /* renamed from: d, reason: collision with root package name */
    public static String f20082d;

    /* renamed from: e, reason: collision with root package name */
    public static IProcessName f20083e;

    /* renamed from: f, reason: collision with root package name */
    public static AtomicInteger f20084f = new AtomicInteger(-1);

    /* renamed from: g, reason: collision with root package name */
    private static volatile a f20085g;

    /* renamed from: h, reason: collision with root package name */
    private static Context f20086h;

    /* renamed from: i, reason: collision with root package name */
    private ActivityManager f20087i;

    private a(Context context) {
        if (context == null) {
            throw new RuntimeException("Context is null!!");
        }
        if (f20086h == null) {
            f20086h = context.getApplicationContext();
        }
    }

    public static a a(Context context) {
        if (f20085g == null) {
            synchronized (a.class) {
                try {
                    if (f20085g == null) {
                        f20085g = new a(context);
                    }
                } finally {
                }
            }
        }
        return f20085g;
    }

    public static String b() {
        String str = TextUtils.isEmpty(f20079a) ? "com.umeng.message.component.UmengIntentService" : f20079a;
        ALog.d("AdapterGlobalClientInfo", "getAgooCustomServiceName", "serviceName", str);
        return str;
    }

    public static boolean c() {
        return f20084f.intValue() == 0;
    }

    public ActivityManager a() {
        if (this.f20087i == null) {
            this.f20087i = (ActivityManager) f20086h.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        }
        return this.f20087i;
    }
}
