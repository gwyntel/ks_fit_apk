package com.vivo.push.cache;

import android.content.Context;
import android.text.TextUtils;
import com.vivo.push.util.ContextDelegate;
import com.vivo.push.util.g;
import com.vivo.push.util.p;
import com.vivo.push.util.w;
import com.vivo.push.util.y;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class c<T> {

    /* renamed from: a, reason: collision with root package name */
    protected static final Object f23089a = new Object();

    /* renamed from: b, reason: collision with root package name */
    protected List<T> f23090b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    protected Context f23091c;

    /* renamed from: d, reason: collision with root package name */
    private byte[] f23092d;

    /* renamed from: e, reason: collision with root package name */
    private byte[] f23093e;

    protected c(Context context) {
        this.f23091c = ContextDelegate.getContext(context);
        w wVarB = w.b();
        wVarB.a(this.f23091c);
        this.f23092d = wVarB.c();
        this.f23093e = wVarB.d();
        c();
    }

    private String b() {
        return y.b(this.f23091c).a(a(), null);
    }

    private void d(String str) {
        y.b(this.f23091c).b(a(), str);
    }

    protected abstract String a();

    protected abstract List<T> a(String str);

    abstract String b(String str) throws Exception;

    public final void c() {
        synchronized (f23089a) {
            g.a(a());
            this.f23090b.clear();
            c(b());
        }
    }

    protected final byte[] e() {
        byte[] bArr = this.f23092d;
        return (bArr == null || bArr.length <= 0) ? w.b().c() : bArr;
    }

    protected final byte[] f() {
        byte[] bArr = this.f23093e;
        return (bArr == null || bArr.length <= 0) ? w.b().d() : bArr;
    }

    public final void d() {
        synchronized (f23089a) {
            this.f23090b.clear();
            d("");
            p.d("CacheSettings", "clear " + a() + " strApps");
        }
    }

    private void c(String str) {
        if (TextUtils.isEmpty(str)) {
            p.d("CacheSettings", "ClientManager init " + a() + " strApps empty.");
            return;
        }
        if (str.length() > 10000) {
            p.d("CacheSettings", "sync " + a() + " strApps lenght too large");
            d();
            return;
        }
        try {
            p.d("CacheSettings", "ClientManager init " + a() + " strApps : " + str);
            List<T> listA = a(b(str));
            if (listA != null) {
                this.f23090b.addAll(listA);
            }
        } catch (Exception e2) {
            d();
            p.d("CacheSettings", p.a(e2));
        }
    }
}
