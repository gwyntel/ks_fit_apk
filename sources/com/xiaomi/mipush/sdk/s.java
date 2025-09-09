package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.in;
import com.xiaomi.push.ix;
import com.xiaomi.push.jj;
import com.xiaomi.push.jm;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class s {

    /* renamed from: a, reason: collision with root package name */
    private static volatile s f23422a;

    /* renamed from: a, reason: collision with other field name */
    private final Context f154a;

    private s(Context context) {
        this.f154a = context.getApplicationContext();
    }

    private static s a(Context context) {
        if (f23422a == null) {
            synchronized (s.class) {
                try {
                    if (f23422a == null) {
                        f23422a = new s(context);
                    }
                } finally {
                }
            }
        }
        return f23422a;
    }

    public static void b(Context context, jj jjVar, boolean z2) {
        a(context).a(jjVar, 2, z2);
    }

    public static void c(Context context, jj jjVar, boolean z2) {
        a(context).a(jjVar, 3, z2);
    }

    public static void d(Context context, jj jjVar, boolean z2) {
        a(context).a(jjVar, 4, z2);
    }

    public static void e(Context context, jj jjVar, boolean z2) {
        b bVarM140a = b.m140a(context);
        if (TextUtils.isEmpty(bVarM140a.m148c()) || TextUtils.isEmpty(bVarM140a.d())) {
            a(context).a(jjVar, 6, z2);
        } else if (bVarM140a.m152f()) {
            a(context).a(jjVar, 7, z2);
        } else {
            a(context).a(jjVar, 5, z2);
        }
    }

    public static void a(Context context, jj jjVar, boolean z2) {
        a(context).a(jjVar, 1, z2);
    }

    public static void a(Context context, jj jjVar) {
        a(context).a(jjVar, 0, true);
    }

    private void a(jj jjVar, int i2, boolean z2) {
        if (com.xiaomi.push.j.m550a(this.f154a) || !com.xiaomi.push.j.m549a() || jjVar == null || jjVar.f741a != in.SendMessage || jjVar.m593a() == null || !z2) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("click to start activity result:" + String.valueOf(i2));
        jm jmVar = new jm(jjVar.m593a().m559a(), false);
        jmVar.c(ix.SDK_START_ACTIVITY.f620a);
        jmVar.b(jjVar.m594a());
        jmVar.d(jjVar.f748b);
        HashMap map = new HashMap();
        jmVar.f760a = map;
        map.put("result", String.valueOf(i2));
        ao.a(this.f154a).a(jmVar, in.Notification, false, false, null, true, jjVar.f748b, jjVar.f744a, true, false);
    }
}
