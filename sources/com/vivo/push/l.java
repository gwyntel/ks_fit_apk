package com.vivo.push;

import android.content.Context;

/* loaded from: classes4.dex */
public abstract class l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    protected Context f23178a;

    /* renamed from: b, reason: collision with root package name */
    private int f23179b;

    /* renamed from: c, reason: collision with root package name */
    private o f23180c;

    public l(o oVar) {
        this.f23179b = -1;
        this.f23180c = oVar;
        int iB = oVar.b();
        this.f23179b = iB;
        if (iB < 0) {
            throw new IllegalArgumentException("PushTask need a > 0 task id.");
        }
        this.f23178a = e.a().h();
    }

    public final int a() {
        return this.f23179b;
    }

    protected abstract void a(o oVar);

    @Override // java.lang.Runnable
    public final void run() {
        Context context = this.f23178a;
        if (context != null && !(this.f23180c instanceof com.vivo.push.b.n)) {
            com.vivo.push.util.p.a(context, "[执行指令]" + this.f23180c);
        }
        a(this.f23180c);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        o oVar = this.f23180c;
        sb.append(oVar == null ? "[null]" : oVar.toString());
        sb.append(com.alipay.sdk.m.u.i.f9804d);
        return sb.toString();
    }
}
