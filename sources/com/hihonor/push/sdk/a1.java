package com.hihonor.push.sdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class a1 {

    /* renamed from: b, reason: collision with root package name */
    public boolean f15462b;

    /* renamed from: c, reason: collision with root package name */
    public Object f15463c;

    /* renamed from: d, reason: collision with root package name */
    public Exception f15464d;

    /* renamed from: a, reason: collision with root package name */
    public final Object f15461a = new Object();

    /* renamed from: e, reason: collision with root package name */
    public List<j0<Object>> f15465e = new ArrayList();

    public final void a() {
        synchronized (this.f15461a) {
            Iterator<j0<Object>> it = this.f15465e.iterator();
            while (it.hasNext()) {
                try {
                    it.next().a(this);
                } catch (RuntimeException e2) {
                    throw e2;
                } catch (Exception e3) {
                    throw new RuntimeException(e3);
                }
            }
            this.f15465e = null;
        }
    }

    public final Exception b() {
        Exception exc;
        synchronized (this.f15461a) {
            exc = this.f15464d;
        }
        return exc;
    }

    public final Object c() {
        Object obj;
        synchronized (this.f15461a) {
            try {
                if (this.f15464d != null) {
                    throw new RuntimeException(this.f15464d);
                }
                obj = this.f15463c;
            } catch (Throwable th) {
                throw th;
            }
        }
        return obj;
    }

    public final boolean d() {
        synchronized (this.f15461a) {
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean e() {
        /*
            r2 = this;
            java.lang.Object r0 = r2.f15461a
            monitor-enter(r0)
            boolean r1 = r2.f15462b     // Catch: java.lang.Throwable -> L10
            if (r1 == 0) goto L12
            r2.d()     // Catch: java.lang.Throwable -> L10
            java.lang.Exception r1 = r2.f15464d     // Catch: java.lang.Throwable -> L10
            if (r1 != 0) goto L12
            r1 = 1
            goto L13
        L10:
            r1 = move-exception
            goto L15
        L12:
            r1 = 0
        L13:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            return r1
        L15:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hihonor.push.sdk.a1.e():boolean");
    }

    public final a1 a(j0 j0Var) {
        synchronized (this.f15461a) {
            try {
                if (!this.f15462b) {
                    this.f15465e.add(j0Var);
                } else {
                    j0Var.a(this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this;
    }
}
