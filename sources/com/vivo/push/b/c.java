package com.vivo.push.b;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class c extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private String f23035a;

    /* renamed from: b, reason: collision with root package name */
    private String f23036b;

    /* renamed from: c, reason: collision with root package name */
    private long f23037c;

    /* renamed from: d, reason: collision with root package name */
    private int f23038d;

    /* renamed from: e, reason: collision with root package name */
    private int f23039e;

    /* renamed from: f, reason: collision with root package name */
    private String f23040f;

    public c(int i2, String str) {
        super(i2);
        this.f23037c = -1L;
        this.f23038d = -1;
        this.f23035a = null;
        this.f23036b = str;
    }

    public final void a(int i2) {
        this.f23039e = i2;
    }

    public final void b(String str) {
        this.f23035a = str;
    }

    @Override // com.vivo.push.o
    protected void c(com.vivo.push.a aVar) {
        aVar.a("req_id", this.f23035a);
        aVar.a("package_name", this.f23036b);
        aVar.a("sdk_version", 323L);
        aVar.a("PUSH_APP_STATUS", this.f23038d);
        if (TextUtils.isEmpty(this.f23040f)) {
            return;
        }
        aVar.a("BaseAppCommand.EXTRA__HYBRIDVERSION", this.f23040f);
    }

    @Override // com.vivo.push.o
    protected void d(com.vivo.push.a aVar) {
        this.f23035a = aVar.a("req_id");
        this.f23036b = aVar.a("package_name");
        this.f23037c = aVar.b("sdk_version", 0L);
        this.f23038d = aVar.b("PUSH_APP_STATUS", 0);
        this.f23040f = aVar.a("BaseAppCommand.EXTRA__HYBRIDVERSION");
    }

    public final int f() {
        return this.f23039e;
    }

    public final void g() {
        this.f23040f = null;
    }

    public final String h() {
        return this.f23035a;
    }

    @Override // com.vivo.push.o
    public String toString() {
        return "BaseAppCommand";
    }

    public final int a(Context context) {
        if (this.f23038d == -1) {
            String strA = this.f23036b;
            if (TextUtils.isEmpty(strA)) {
                com.vivo.push.util.p.a("BaseAppCommand", "pkg name is null");
                strA = a();
                if (TextUtils.isEmpty(strA)) {
                    com.vivo.push.util.p.a("BaseAppCommand", "src is null");
                    return -1;
                }
            }
            this.f23038d = com.vivo.push.util.t.b(context, strA);
            if (!TextUtils.isEmpty(this.f23040f)) {
                this.f23038d = 2;
            }
        }
        return this.f23038d;
    }
}
