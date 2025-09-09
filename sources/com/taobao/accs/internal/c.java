package com.taobao.accs.internal;

import androidx.annotation.Keep;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.base.AccsConnectStateListener;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.data.Message;
import com.taobao.accs.net.k;

@Keep
/* loaded from: classes4.dex */
public class c implements com.taobao.accs.c {

    /* renamed from: a, reason: collision with root package name */
    private com.taobao.accs.net.a f20184a;

    public c(String str) {
        this.f20184a = new k(GlobalClientInfo.f20065a, 1, str);
    }

    @Override // com.taobao.accs.c
    public void a() {
        this.f20184a.a();
    }

    @Override // com.taobao.accs.c
    public void b() {
        this.f20184a.k();
    }

    @Override // com.taobao.accs.c
    public String c() {
        return this.f20184a.i();
    }

    @Override // com.taobao.accs.c
    public String d() {
        return this.f20184a.f20199i.getAppSecret();
    }

    @Override // com.taobao.accs.c
    public boolean e(String str) {
        return this.f20184a.j().c(str);
    }

    @Override // com.taobao.accs.c
    public boolean f(String str) {
        return this.f20184a.j().d(str);
    }

    @Override // com.taobao.accs.c
    public void a(boolean z2, boolean z3) {
        this.f20184a.a(z2, z3);
    }

    @Override // com.taobao.accs.c
    public String b(String str) {
        return this.f20184a.b(str);
    }

    @Override // com.taobao.accs.c
    public void c(String str) {
        this.f20184a.f20191a = str;
    }

    @Override // com.taobao.accs.c
    public void d(String str) {
        this.f20184a.f20192b = str;
    }

    @Override // com.taobao.accs.c
    public String e() {
        return this.f20184a.f20199i.getStoreId();
    }

    @Override // com.taobao.accs.c
    public boolean f() {
        return this.f20184a.m();
    }

    @Override // com.taobao.accs.c
    public boolean a(String str) {
        return this.f20184a.a(str);
    }

    @Override // com.taobao.accs.c
    public void b(AccsConnectStateListener accsConnectStateListener) {
        this.f20184a.b(accsConnectStateListener);
    }

    @Override // com.taobao.accs.c
    public void a(Message message, int i2) {
        this.f20184a.b(message, i2);
    }

    @Override // com.taobao.accs.c
    public void a(Message message, boolean z2) {
        this.f20184a.b(message, z2);
    }

    @Override // com.taobao.accs.c
    public void a(AccsClientConfig accsClientConfig) {
        com.taobao.accs.net.a aVar = this.f20184a;
        if (aVar instanceof k) {
            ((k) aVar).a(accsClientConfig);
        }
    }

    @Override // com.taobao.accs.c
    public boolean a(String str, String str2) {
        return this.f20184a.j().b(str, str2);
    }

    @Override // com.taobao.accs.c
    public void a(AccsConnectStateListener accsConnectStateListener) {
        this.f20184a.a(accsConnectStateListener);
    }
}
