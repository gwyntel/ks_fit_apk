package com.xiaomi.push;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.xiaomi.push.cr;

/* loaded from: classes4.dex */
class ct implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23553a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ cr.a f253a;

    ct(cr.a aVar, Context context) {
        this.f253a = aVar;
        this.f23553a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = this.f253a.a();
                if (sQLiteDatabaseA != null && sQLiteDatabaseA.isOpen()) {
                    sQLiteDatabaseA.beginTransaction();
                    this.f253a.a(this.f23553a, sQLiteDatabaseA);
                    sQLiteDatabaseA.setTransactionSuccessful();
                }
                if (sQLiteDatabaseA != null) {
                    try {
                        sQLiteDatabaseA.endTransaction();
                    } catch (Exception e2) {
                        e = e2;
                        com.xiaomi.channel.commonutils.logger.b.a(e);
                        this.f253a.a(this.f23553a);
                    }
                }
                cp cpVar = this.f253a.f244a;
                if (cpVar != null) {
                    cpVar.close();
                }
            } catch (Throwable th) {
                if (sQLiteDatabaseA != null) {
                    try {
                        sQLiteDatabaseA.endTransaction();
                    } catch (Exception e3) {
                        com.xiaomi.channel.commonutils.logger.b.a(e3);
                        this.f253a.a(this.f23553a);
                        throw th;
                    }
                }
                cp cpVar2 = this.f253a.f244a;
                if (cpVar2 != null) {
                    cpVar2.close();
                }
                this.f253a.a(this.f23553a);
                throw th;
            }
        } catch (Exception e4) {
            com.xiaomi.channel.commonutils.logger.b.a(e4);
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Exception e5) {
                    e = e5;
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    this.f253a.a(this.f23553a);
                }
            }
            cp cpVar3 = this.f253a.f244a;
            if (cpVar3 != null) {
                cpVar3.close();
            }
        }
        this.f253a.a(this.f23553a);
    }
}
