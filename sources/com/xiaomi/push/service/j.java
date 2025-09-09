package com.xiaomi.push.service;

import com.xiaomi.push.C0472r;
import com.xiaomi.push.jm;

/* loaded from: classes4.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    private static a f24598a;

    /* renamed from: a, reason: collision with other field name */
    private static b f1086a;

    public interface a {
        boolean a(jm jmVar);
    }

    public interface b {
    }

    public static void a(b bVar) {
        f1086a = bVar;
    }

    public static boolean a(jm jmVar) {
        if (f24598a == null || jmVar == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("rc params is null, not cpra");
            return false;
        }
        if (com.xiaomi.push.j.m550a(C0472r.m684a())) {
            return f24598a.a(jmVar);
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("rc app not permission to cpra");
        return false;
    }
}
