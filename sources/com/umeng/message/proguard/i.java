package com.umeng.message.proguard;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import com.facebook.share.internal.ShareConstants;
import com.taobao.accs.EventReceiver;
import com.taobao.agoo.AgooCommondReceiver;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.message.common.UPLog;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class i {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f22843a = false;

    /* renamed from: b, reason: collision with root package name */
    private static volatile boolean f22844b = true;

    static class a extends BroadcastReceiver {
        private a() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(final Context context, final Intent intent) {
            if (!i.f22844b || intent == null || intent.getData() == null) {
                return;
            }
            com.umeng.message.proguard.b.c(new Runnable() { // from class: com.umeng.message.proguard.i.a.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        i.a(context, intent);
                    } catch (Throwable th) {
                        UPLog.e("RH", th);
                    }
                }
            });
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    static class b extends BroadcastReceiver {

        /* renamed from: a, reason: collision with root package name */
        private final BroadcastReceiver f22848a = new EventReceiver();

        b() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(final Context context, final Intent intent) {
            if (intent == null) {
                return;
            }
            com.umeng.message.proguard.b.c(new Runnable() { // from class: com.umeng.message.proguard.i.b.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        b.this.f22848a.onReceive(context, intent);
                    } catch (Throwable th) {
                        UPLog.e("RH", th);
                    }
                }
            });
        }
    }

    public static void a(boolean z2) {
        f22844b = z2;
    }

    public static synchronized void a() {
        if (f22843a) {
            return;
        }
        Application applicationA = x.a();
        if (applicationA == null) {
            return;
        }
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            applicationA.registerReceiver(new b(), intentFilter);
        } catch (Throwable unused) {
        }
        if (f22844b) {
            try {
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
                intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
                intentFilter2.addDataScheme("package");
                applicationA.registerReceiver(new a((byte) 0), intentFilter2);
            } catch (Throwable unused2) {
            }
            f22843a = true;
        }
    }

    private static void a(String str, int i2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("pkg", str);
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, i2);
            UMWorkDispatch.sendEvent(x.a(), 16390, v.a(), jSONObject.toString());
        } catch (Exception unused) {
        }
    }

    static /* synthetic */ void a(Context context, Intent intent) throws JSONException {
        Uri data;
        if (intent == null || (data = intent.getData()) == null) {
            return;
        }
        boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
        String action = intent.getAction();
        String schemeSpecificPart = data.getSchemeSpecificPart();
        if (schemeSpecificPart == null || schemeSpecificPart.length() == 0) {
            return;
        }
        if ("android.intent.action.PACKAGE_REMOVED".equals(action) && !booleanExtra) {
            a(schemeSpecificPart, 80);
            new AgooCommondReceiver().onReceive(context, intent);
        } else if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            if (booleanExtra) {
                a(schemeSpecificPart, 81);
            } else {
                a(schemeSpecificPart, 82);
            }
        }
    }
}
