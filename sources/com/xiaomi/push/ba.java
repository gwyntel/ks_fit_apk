package com.xiaomi.push;

import android.os.IBinder;
import com.xiaomi.push.ay;

/* loaded from: classes4.dex */
class ba implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ IBinder f23479a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ ay.b f205a;

    ba(ay.b bVar, IBinder iBinder) {
        this.f205a = bVar;
        this.f23479a = iBinder;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String packageName = ay.this.f198a.getPackageName();
            String strB = ay.this.b();
            ay.a aVar = new ay.a();
            aVar.f23466b = ay.c.a(this.f23479a, packageName, strB, "OUID");
            ay.this.f200a = aVar;
            ay.this.m186b();
            ay.this.f197a = 2;
            synchronized (ay.this.f201a) {
                try {
                    ay.this.f201a.notifyAll();
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
            ay.this.m186b();
            ay.this.f197a = 2;
            synchronized (ay.this.f201a) {
                try {
                    ay.this.f201a.notifyAll();
                } catch (Exception unused3) {
                }
            }
        } catch (Throwable th) {
            ay.this.m186b();
            ay.this.f197a = 2;
            synchronized (ay.this.f201a) {
                try {
                    ay.this.f201a.notifyAll();
                } catch (Exception unused4) {
                }
                throw th;
            }
        }
    }
}
