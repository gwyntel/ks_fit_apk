package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import java.text.SimpleDateFormat;

/* loaded from: classes4.dex */
public class cw {

    /* renamed from: a, reason: collision with root package name */
    private static String f23554a;

    /* renamed from: a, reason: collision with other field name */
    private static SimpleDateFormat f254a;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        f254a = simpleDateFormat;
        f23554a = simpleDateFormat.format(Long.valueOf(System.currentTimeMillis()));
    }

    public static ir a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ir irVar = new ir();
        irVar.d("category_push_stat");
        irVar.a("push_sdk_stat_channel");
        irVar.a(1L);
        irVar.b(str);
        irVar.a(true);
        irVar.b(System.currentTimeMillis());
        irVar.g(cf.a(context).m253a());
        irVar.e("com.xiaomi.xmsf");
        irVar.f("");
        irVar.c("push_stat");
        return irVar;
    }
}
