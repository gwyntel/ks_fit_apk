package com.meizu.cloud.pushsdk.platform.d;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public abstract class c<T extends BasicPushStatus> {

    /* renamed from: a, reason: collision with root package name */
    private ScheduledExecutorService f19806a;

    /* renamed from: b, reason: collision with root package name */
    protected final Context f19807b;

    /* renamed from: c, reason: collision with root package name */
    protected String f19808c;

    /* renamed from: d, reason: collision with root package name */
    protected String f19809d;

    /* renamed from: e, reason: collision with root package name */
    protected String f19810e;

    /* renamed from: f, reason: collision with root package name */
    protected final com.meizu.cloud.pushsdk.platform.c.a f19811f;

    /* renamed from: g, reason: collision with root package name */
    protected boolean f19812g = true;

    /* renamed from: h, reason: collision with root package name */
    protected boolean f19813h = true;

    /* renamed from: i, reason: collision with root package name */
    private String f19814i = null;

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.this.g();
        }
    }

    public c(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this.f19806a = scheduledExecutorService;
        this.f19807b = context;
        this.f19808c = str;
        this.f19809d = str2;
        this.f19811f = aVar;
    }

    private boolean k() {
        return this.f19813h && !this.f19807b.getPackageName().equals(this.f19814i);
    }

    protected abstract T a();

    @SuppressLint({"QueryPermissionsNeeded"})
    protected String a(Context context, String str) {
        String str2 = null;
        if (!TextUtils.isEmpty(str)) {
            List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(new Intent(str), 0);
            if (listQueryIntentServices != null) {
                Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ResolveInfo next = it.next();
                    if (PushConstants.PUSH_PACKAGE_NAME.equals(next.serviceInfo.packageName)) {
                        ServiceInfo serviceInfo = next.serviceInfo;
                        this.f19814i = serviceInfo.packageName;
                        str2 = serviceInfo.name;
                        break;
                    }
                }
                if (TextUtils.isEmpty(str2) && listQueryIntentServices.size() > 0) {
                    this.f19814i = listQueryIntentServices.get(0).serviceInfo.packageName;
                    str2 = listQueryIntentServices.get(0).serviceInfo.name;
                }
            }
        }
        DebugLogger.i("Strategy", "current process packageName " + this.f19814i);
        return str2;
    }

    protected abstract void b(T t2);

    public void b(String str) {
        this.f19809d = str;
    }

    protected abstract T c();

    public void c(String str) {
        this.f19810e = str;
    }

    protected abstract boolean d();

    protected abstract T e();

    public boolean f() {
        ScheduledExecutorService scheduledExecutorService = this.f19806a;
        if (scheduledExecutorService == null) {
            return g();
        }
        scheduledExecutorService.execute(new a());
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean g() {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.platform.d.c.g():boolean");
    }

    protected abstract Intent h();

    protected Intent[] i() {
        return null;
    }

    protected abstract int j();

    protected boolean l() {
        return this.f19813h && this.f19812g && !TextUtils.isEmpty(a(this.f19807b, PushConstants.MZ_PUSH_MANAGER_SERVICE_ACTION));
    }

    protected void a(Intent intent) {
        try {
            intent.setPackage(this.f19814i);
            intent.setAction(PushConstants.MZ_PUSH_MANAGER_SERVICE_ACTION);
            this.f19807b.startService(intent);
        } catch (Exception e2) {
            DebugLogger.e("Strategy", "start RemoteService error " + e2.getMessage());
        }
    }

    protected boolean b() {
        return 2 == j() || 32 == j();
    }

    public void a(String str) {
        this.f19808c = str;
    }

    public void a(boolean z2) {
        this.f19812g = z2;
    }

    private boolean a(int i2) {
        return i2 >= 110000 && i2 <= 200000;
    }

    private boolean a(T t2) {
        int iIntValue = Integer.valueOf(t2.getCode()).intValue();
        return (iIntValue > 200 && iIntValue < 600) || (iIntValue > 1000 && iIntValue < 2000) || iIntValue == 0;
    }
}
