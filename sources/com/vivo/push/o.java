package com.vivo.push;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public abstract class o {

    /* renamed from: a, reason: collision with root package name */
    private int f23192a;

    /* renamed from: b, reason: collision with root package name */
    private String f23193b;

    public o(int i2) {
        this.f23192a = -1;
        if (i2 < 0) {
            throw new IllegalArgumentException("PushCommand: the value of command must > 0.");
        }
        this.f23192a = i2;
    }

    private void e(a aVar) {
        aVar.a("command", this.f23192a);
        aVar.a("client_pkgname", this.f23193b);
        c(aVar);
    }

    public final String a() {
        return this.f23193b;
    }

    public final int b() {
        return this.f23192a;
    }

    protected abstract void c(a aVar);

    public boolean c() {
        return false;
    }

    protected abstract void d(a aVar);

    public String toString() {
        return getClass().getSimpleName();
    }

    public final void a(String str) {
        this.f23193b = str;
    }

    public final void b(Intent intent) {
        a aVarA = a.a(intent);
        if (aVarA == null) {
            com.vivo.push.util.p.b("PushCommand", "bundleWapper is null");
            return;
        }
        aVarA.a("method", this.f23192a);
        e(aVarA);
        Bundle bundleB = aVarA.b();
        if (bundleB != null) {
            intent.putExtras(bundleB);
        }
    }

    public final void a(Intent intent) {
        a aVarA = a.a(intent);
        if (aVarA == null) {
            com.vivo.push.util.p.b("PushCommand", "bundleWapper is null");
            return;
        }
        a(aVarA);
        Bundle bundleB = aVarA.b();
        if (bundleB != null) {
            intent.putExtras(bundleB);
        }
    }

    public final void a(a aVar) {
        String strA = p.a(this.f23192a);
        if (strA == null) {
            strA = "";
        }
        aVar.a("method", strA);
        e(aVar);
    }

    public final void b(a aVar) {
        String strA = aVar.a();
        if (!TextUtils.isEmpty(strA)) {
            this.f23193b = strA;
        } else {
            this.f23193b = aVar.a("client_pkgname");
        }
        d(aVar);
    }
}
