package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.xiaomi.push.in;
import com.xiaomi.push.ir;
import com.xiaomi.push.ja;
import com.xiaomi.push.jm;
import com.xiaomi.push.service.bc;
import com.xiaomi.push.service.ca;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class MiTinyDataClient {
    public static final String PENDING_REASON_APPID = "com.xiaomi.xmpushsdk.tinydataPending.appId";
    public static final String PENDING_REASON_CHANNEL = "com.xiaomi.xmpushsdk.tinydataPending.channel";
    public static final String PENDING_REASON_INIT = "com.xiaomi.xmpushsdk.tinydataPending.init";

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static volatile a f23358a;

        /* renamed from: a, reason: collision with other field name */
        private Context f102a;

        /* renamed from: a, reason: collision with other field name */
        private Boolean f104a;

        /* renamed from: a, reason: collision with other field name */
        private String f105a;

        /* renamed from: a, reason: collision with other field name */
        private C0192a f103a = new C0192a();

        /* renamed from: a, reason: collision with other field name */
        private final ArrayList<ir> f106a = new ArrayList<>();

        /* renamed from: com.xiaomi.mipush.sdk.MiTinyDataClient$a$a, reason: collision with other inner class name */
        public class C0192a {

            /* renamed from: a, reason: collision with other field name */
            private ScheduledFuture<?> f109a;

            /* renamed from: a, reason: collision with other field name */
            private ScheduledThreadPoolExecutor f110a = new ScheduledThreadPoolExecutor(1);

            /* renamed from: a, reason: collision with other field name */
            public final ArrayList<ir> f108a = new ArrayList<>();

            /* renamed from: a, reason: collision with other field name */
            private final Runnable f107a = new ab(this);

            public C0192a() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void b() {
                ir irVarRemove = this.f108a.remove(0);
                for (jm jmVar : ca.a(Arrays.asList(irVarRemove), a.this.f102a.getPackageName(), b.m140a(a.this.f102a).m141a(), 30720)) {
                    com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient Send item by PushServiceClient.sendMessage(XmActionNotification)." + irVarRemove.d());
                    ao.a(a.this.f102a).a((ao) jmVar, in.Notification, true, (ja) null);
                }
            }

            public void a(ir irVar) {
                this.f110a.execute(new aa(this, irVar));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void a() {
                if (this.f109a == null) {
                    this.f109a = this.f110a.scheduleAtFixedRate(this.f107a, 1000L, 1000L, TimeUnit.MILLISECONDS);
                }
            }
        }

        public void b(String str) {
            com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient.processPendingList(" + str + ")");
            ArrayList arrayList = new ArrayList();
            synchronized (this.f106a) {
                arrayList.addAll(this.f106a);
                this.f106a.clear();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                m113a((ir) it.next());
            }
        }

        public static a a() {
            if (f23358a == null) {
                synchronized (a.class) {
                    try {
                        if (f23358a == null) {
                            f23358a = new a();
                        }
                    } finally {
                    }
                }
            }
            return f23358a;
        }

        /* renamed from: a, reason: collision with other method in class */
        public void m111a(Context context) {
            if (context == null) {
                com.xiaomi.channel.commonutils.logger.b.m91a("context is null, MiTinyDataClientImp.init() failed.");
                return;
            }
            this.f102a = context;
            this.f104a = Boolean.valueOf(a(context));
            b(MiTinyDataClient.PENDING_REASON_INIT);
        }

        private boolean b(Context context) {
            return b.m140a(context).m141a() == null && !a(this.f102a);
        }

        private boolean b(ir irVar) {
            if (ca.a(irVar, false)) {
                return false;
            }
            if (this.f104a.booleanValue()) {
                com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient Send item by PushServiceClient.sendTinyData(ClientUploadDataItem)." + irVar.d());
                ao.a(this.f102a).a(irVar);
                return true;
            }
            this.f103a.a(irVar);
            return true;
        }

        public synchronized void a(String str) {
            if (TextUtils.isEmpty(str)) {
                com.xiaomi.channel.commonutils.logger.b.m91a("channel is null, MiTinyDataClientImp.setChannel(String) failed.");
            } else {
                this.f105a = str;
                b(MiTinyDataClient.PENDING_REASON_CHANNEL);
            }
        }

        private boolean a(Context context) throws PackageManager.NameNotFoundException {
            if (!ao.a(context).m133a()) {
                return true;
            }
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
                if (packageInfo == null) {
                    return false;
                }
                return packageInfo.versionCode >= 108;
            } catch (Exception unused) {
                return false;
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m112a() {
            return this.f102a != null;
        }

        /* renamed from: a, reason: collision with other method in class */
        public synchronized boolean m113a(ir irVar) {
            if (irVar == null) {
                return false;
            }
            try {
                if (ca.a(irVar, true)) {
                    return false;
                }
                boolean z2 = TextUtils.isEmpty(irVar.m516a()) && TextUtils.isEmpty(this.f105a);
                boolean z3 = !m112a();
                Context context = this.f102a;
                boolean z4 = context == null || b(context);
                if (!z3 && !z2 && !z4) {
                    com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient Send item immediately." + irVar.d());
                    if (TextUtils.isEmpty(irVar.d())) {
                        irVar.f(bc.a());
                    }
                    if (TextUtils.isEmpty(irVar.m516a())) {
                        irVar.a(this.f105a);
                    }
                    if (TextUtils.isEmpty(irVar.c())) {
                        irVar.e(this.f102a.getPackageName());
                    }
                    if (irVar.a() <= 0) {
                        irVar.b(System.currentTimeMillis());
                    }
                    return b(irVar);
                }
                if (z2) {
                    com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient Pending " + irVar.b() + " reason is " + MiTinyDataClient.PENDING_REASON_CHANNEL);
                } else if (z3) {
                    com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient Pending " + irVar.b() + " reason is " + MiTinyDataClient.PENDING_REASON_INIT);
                } else if (z4) {
                    com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient Pending " + irVar.b() + " reason is " + MiTinyDataClient.PENDING_REASON_APPID);
                }
                a(irVar);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }

        private void a(ir irVar) {
            synchronized (this.f106a) {
                try {
                    if (!this.f106a.contains(irVar)) {
                        this.f106a.add(irVar);
                        if (this.f106a.size() > 100) {
                            this.f106a.remove(0);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static void init(Context context, String str) {
        if (context == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("context is null, MiTinyDataClient.init(Context, String) failed.");
            return;
        }
        a.a().m111a(context);
        if (TextUtils.isEmpty(str)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("channel is null or empty, MiTinyDataClient.init(Context, String) failed.");
        } else {
            a.a().a(str);
        }
    }

    public static boolean upload(String str, String str2, long j2, String str3) {
        ir irVar = new ir();
        irVar.d(str);
        irVar.c(str2);
        irVar.a(j2);
        irVar.b(str3);
        return a.a().m113a(irVar);
    }

    public static boolean upload(Context context, String str, String str2, long j2, String str3) {
        ir irVar = new ir();
        irVar.d(str);
        irVar.c(str2);
        irVar.a(j2);
        irVar.b(str3);
        irVar.a(true);
        irVar.a("push_sdk_channel");
        return upload(context, irVar);
    }

    public static boolean upload(Context context, ir irVar) {
        com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient.upload " + irVar.d());
        if (!a.a().m112a()) {
            a.a().m111a(context);
        }
        return a.a().m113a(irVar);
    }
}
