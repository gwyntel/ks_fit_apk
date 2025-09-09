package com.umeng.message.proguard;

import android.content.Context;
import android.net.Uri;
import com.kingsmith.miot.KsProperty;

/* loaded from: classes4.dex */
public final class h {

    /* renamed from: a, reason: collision with root package name */
    private static String f22842a;

    public static Uri a(Context context) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(f(context));
        builder.path("MsgAlias");
        return builder.build();
    }

    public static Uri b(Context context) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(f(context));
        builder.path("MsgAliasDeleteAll");
        return builder.build();
    }

    public static Uri c(Context context) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(f(context));
        builder.path(KsProperty.Sp);
        return builder.build();
    }

    public static Uri d(Context context) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(f(context));
        builder.path("MsgLogStores");
        return builder.build();
    }

    public static Uri e(Context context) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(f(context));
        builder.path("InAppLogStores");
        return builder.build();
    }

    public static String f(Context context) {
        if (f22842a == null) {
            f22842a = context.getPackageName() + ".umeng.message";
        }
        return f22842a;
    }
}
