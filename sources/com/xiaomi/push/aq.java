package com.xiaomi.push;

import android.os.IBinder;
import com.xiaomi.push.ao;

/* loaded from: classes4.dex */
class aq implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ IBinder f23445a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ ao.a f181a;

    aq(ao.a aVar, IBinder iBinder) {
        this.f181a = aVar;
        this.f23445a = iBinder;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            ao.this.f179a = ao.b.a(this.f23445a);
            ao.this.f180b = ao.b.m181a(this.f23445a);
            ao.this.b();
            ao.this.f175a = 2;
            synchronized (ao.this.f178a) {
                try {
                    ao.this.f178a.notifyAll();
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
            ao.this.b();
            ao.this.f175a = 2;
            synchronized (ao.this.f178a) {
                try {
                    ao.this.f178a.notifyAll();
                } catch (Exception unused3) {
                }
            }
        } catch (Throwable th) {
            ao.this.b();
            ao.this.f175a = 2;
            synchronized (ao.this.f178a) {
                try {
                    ao.this.f178a.notifyAll();
                } catch (Exception unused4) {
                }
                throw th;
            }
        }
    }
}
