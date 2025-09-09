package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.cr;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class ck implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private String f23537a;

    /* renamed from: a, reason: collision with other field name */
    private WeakReference<Context> f237a;

    public ck(String str, WeakReference<Context> weakReference) {
        this.f23537a = str;
        this.f237a = weakReference;
    }

    @Override // java.lang.Runnable
    public void run() {
        Context context;
        WeakReference<Context> weakReference = this.f237a;
        if (weakReference == null || (context = weakReference.get()) == null) {
            return;
        }
        if (cx.a(this.f23537a) <= cj.f235a) {
            com.xiaomi.channel.commonutils.logger.b.b("=====> do not need clean db");
            return;
        }
        cn cnVarA = cn.a(this.f23537a);
        cm cmVarA = cm.a(this.f23537a);
        cnVarA.a(cmVarA);
        cmVarA.a(cl.a(context, this.f23537a, 1000));
        cr.a(context).a((cr.a) cnVarA);
    }
}
