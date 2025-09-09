package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.jj;
import java.util.Map;

/* loaded from: classes4.dex */
public class ah {

    /* renamed from: a, reason: collision with root package name */
    private static a f24456a;

    /* renamed from: a, reason: collision with other field name */
    private static b f998a;

    public interface a {
        Map<String, String> a(Context context, jj jjVar);

        /* renamed from: a, reason: collision with other method in class */
        void m720a(Context context, jj jjVar);

        boolean a(Context context, jj jjVar, boolean z2);
    }

    public interface b {
        void a(jj jjVar);

        void a(String str);

        /* renamed from: a, reason: collision with other method in class */
        boolean m721a(jj jjVar);
    }

    public static boolean a(Context context, jj jjVar, boolean z2) {
        a aVar = f24456a;
        if (aVar != null && jjVar != null) {
            return aVar.a(context, jjVar, z2);
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("pepa judement listener or container is null");
        return false;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m718a(Context context, jj jjVar) {
        a aVar = f24456a;
        if (aVar != null && jjVar != null) {
            aVar.m720a(context, jjVar);
        } else {
            com.xiaomi.channel.commonutils.logger.b.m91a("handle msg wrong");
        }
    }

    public static Map<String, String> a(Context context, jj jjVar) {
        a aVar = f24456a;
        if (aVar != null && jjVar != null) {
            return aVar.a(context, jjVar);
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("pepa listener or container is null");
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m719a(jj jjVar) {
        b bVar = f998a;
        if (bVar != null && jjVar != null) {
            return bVar.m721a(jjVar);
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("pepa handleReceiveMessage is null");
        return false;
    }

    public static void a(jj jjVar) {
        b bVar = f998a;
        if (bVar != null && jjVar != null) {
            bVar.a(jjVar);
        } else {
            com.xiaomi.channel.commonutils.logger.b.m91a("pepa clearMessage is null");
        }
    }

    public static void a(String str) {
        b bVar = f998a;
        if (bVar != null && str != null) {
            bVar.a(str);
        } else {
            com.xiaomi.channel.commonutils.logger.b.m91a("pepa clearMessage is null");
        }
    }
}
