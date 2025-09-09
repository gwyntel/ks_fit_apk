package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.l;
import com.xiaomi.push.bk;

/* loaded from: classes4.dex */
public class ak {
    public static AbstractPushManager a(Context context, e eVar) {
        return b(context, eVar);
    }

    private static AbstractPushManager b(Context context, e eVar) {
        l.a aVarM166a = l.m166a(eVar);
        if (aVarM166a == null || TextUtils.isEmpty(aVarM166a.f23415a) || TextUtils.isEmpty(aVarM166a.f23416b)) {
            return null;
        }
        return (AbstractPushManager) bk.a(aVarM166a.f23415a, aVarM166a.f23416b, context);
    }
}
