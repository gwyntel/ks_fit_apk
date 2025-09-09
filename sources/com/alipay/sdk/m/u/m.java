package com.alipay.sdk.m.u;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.sdk.app.EnvUtils;

/* loaded from: classes2.dex */
public class m {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9815a = "content://com.alipay.android.app.settings.data.ServerProvider/current_server";

    public static String a(Context context) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(f9815a), null, null, null, null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            string = cursorQuery.moveToFirst() ? cursorQuery.getString(cursorQuery.getColumnIndex("url")) : null;
            cursorQuery.close();
        }
        return string;
    }

    public static String b(Context context) {
        if (EnvUtils.isSandBox()) {
            return com.alipay.sdk.m.l.a.f9409b;
        }
        if (context == null) {
            return com.alipay.sdk.m.l.a.f9408a;
        }
        String str = com.alipay.sdk.m.l.a.f9408a;
        return TextUtils.isEmpty(str) ? com.alipay.sdk.m.l.a.f9408a : str;
    }
}
