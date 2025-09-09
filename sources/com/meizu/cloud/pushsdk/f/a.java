package com.meizu.cloud.pushsdk.f;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.f.c.a;
import com.meizu.cloud.pushsdk.f.c.b;
import com.meizu.cloud.pushsdk.f.c.f;
import com.meizu.cloud.pushsdk.f.f.a;
import com.meizu.cloud.pushsdk.f.f.c;
import com.meizu.cloud.pushsdk.f.g.c;
import com.meizu.cloud.pushsdk.f.g.e;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static com.meizu.cloud.pushsdk.f.f.a f19520a;

    /* renamed from: b, reason: collision with root package name */
    private static BroadcastReceiver f19521b;

    /* renamed from: com.meizu.cloud.pushsdk.f.a$a, reason: collision with other inner class name */
    class C0154a extends BroadcastReceiver {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ com.meizu.cloud.pushsdk.f.f.a f19522a;

        C0154a(com.meizu.cloud.pushsdk.f.f.a aVar) {
            this.f19522a = aVar;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (e.d(context)) {
                c.b("QuickTracker", "restart track event: %s", "online true");
                this.f19522a.b();
            }
        }
    }

    public static com.meizu.cloud.pushsdk.f.f.a a(Context context, com.meizu.cloud.pushsdk.e.d.a aVar, f fVar) {
        if (f19520a == null) {
            synchronized (a.class) {
                try {
                    if (f19520a == null) {
                        com.meizu.cloud.pushsdk.f.f.a aVarA = a(b(context, aVar, fVar), (com.meizu.cloud.pushsdk.f.f.c) null, context);
                        f19520a = aVarA;
                        a(context, aVarA);
                    }
                } finally {
                }
            }
        }
        return f19520a;
    }

    private static com.meizu.cloud.pushsdk.f.c.a b(Context context, com.meizu.cloud.pushsdk.e.d.a aVar, f fVar) {
        a.C0155a c0155aC = new a.C0155a(a(), context, com.meizu.cloud.pushsdk.f.c.h.a.class).a(fVar).a(aVar).c(1);
        b bVar = b.DefaultGroup;
        return new com.meizu.cloud.pushsdk.f.c.h.a(c0155aC.a(bVar).b(bVar.a()).a(2));
    }

    public static com.meizu.cloud.pushsdk.f.f.a a(Context context, boolean z2) {
        if (f19520a == null) {
            synchronized (a.class) {
                try {
                    if (f19520a == null) {
                        f19520a = a(b(context, null, null), (com.meizu.cloud.pushsdk.f.f.c) null, context);
                    }
                } finally {
                }
            }
        }
        DebugLogger.i("PushAndroidTracker", "can upload subject " + z2);
        if (z2) {
            f19520a.a(a(context));
        }
        return f19520a;
    }

    private static com.meizu.cloud.pushsdk.f.f.a a(com.meizu.cloud.pushsdk.f.c.a aVar, com.meizu.cloud.pushsdk.f.f.c cVar, Context context) {
        return new com.meizu.cloud.pushsdk.f.f.d.a(new a.C0159a(aVar, "PushAndroidTracker", context.getPackageCodePath(), context, com.meizu.cloud.pushsdk.f.f.d.a.class).a(com.meizu.cloud.pushsdk.f.g.b.VERBOSE).a(Boolean.FALSE).a(cVar).a(4));
    }

    private static com.meizu.cloud.pushsdk.f.f.c a(Context context) {
        return new c.b().a(context).a();
    }

    private static String a() {
        String str = MzSystemUtils.isOverseas() ? PushConstants.URL_ABROAD_STATICS_DOMAIN : PushConstants.URL_STATICS_DOMAIN;
        DebugLogger.e("QuickTracker", "current statics domain is " + str);
        return str;
    }

    private static void a(Context context, com.meizu.cloud.pushsdk.f.f.a aVar) {
        if (f19521b != null) {
            return;
        }
        f19521b = new C0154a(aVar);
        context.registerReceiver(f19521b, new IntentFilter(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION));
    }
}
