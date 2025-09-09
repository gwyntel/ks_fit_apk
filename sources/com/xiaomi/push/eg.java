package com.xiaomi.push;

import com.xiaomi.push.ef;
import com.xiaomi.push.ef.c;
import java.io.File;
import java.util.Date;

/* loaded from: classes4.dex */
class eg extends ef.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f23653a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ ef f319a;

    /* renamed from: a, reason: collision with other field name */
    File f320a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f321a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Date f322a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f323a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f23654b;

    /* renamed from: b, reason: collision with other field name */
    final /* synthetic */ Date f324b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    eg(ef efVar, int i2, Date date, Date date2, String str, String str2, boolean z2) {
        super();
        this.f319a = efVar;
        this.f23653a = i2;
        this.f322a = date;
        this.f324b = date2;
        this.f321a = str;
        this.f23654b = str2;
        this.f323a = z2;
    }

    @Override // com.xiaomi.push.ef.b, com.xiaomi.push.ak.b
    public void b() {
        if (z.d()) {
            try {
                File file = new File(this.f319a.f312a.getExternalFilesDir(null) + "/.logcache");
                file.mkdirs();
                if (file.isDirectory()) {
                    ee eeVar = new ee();
                    eeVar.a(this.f23653a);
                    this.f320a = eeVar.a(this.f319a.f312a, this.f322a, this.f324b, file);
                }
            } catch (NullPointerException unused) {
            }
        }
    }

    @Override // com.xiaomi.push.ak.b
    /* renamed from: c */
    public void mo308c() {
        File file = this.f320a;
        if (file != null && file.exists()) {
            this.f319a.f313a.add(this.f319a.new c(this.f321a, this.f23654b, this.f320a, this.f323a));
        }
        this.f319a.a(0L);
    }
}
