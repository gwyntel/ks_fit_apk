package com.meizu.cloud.pushsdk.notification.g;

import android.content.Context;
import android.content.res.AssetManager;
import com.meizu.cloud.pushinternal.DebugLogger;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static d f19764a;

    /* renamed from: b, reason: collision with root package name */
    private AssetManager f19765b;

    private d(Context context) {
        b(context);
    }

    private void b(Context context) {
        this.f19765b = context.getAssets();
    }

    public int a(Context context, String str, String str2) {
        DebugLogger.i("ResourceReader", "Get resource type " + str2 + " " + str);
        return context.getResources().getIdentifier(str, str2, context.getApplicationInfo().packageName);
    }

    public static d a(Context context) {
        if (f19764a == null) {
            f19764a = new d(context);
        }
        return f19764a;
    }
}
