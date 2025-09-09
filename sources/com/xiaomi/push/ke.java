package com.xiaomi.push;

import com.xiaomi.push.kf;
import java.io.ByteArrayOutputStream;

/* loaded from: classes4.dex */
public class ke {

    /* renamed from: a, reason: collision with root package name */
    private kj f24368a;

    /* renamed from: a, reason: collision with other field name */
    private final kq f913a;

    /* renamed from: a, reason: collision with other field name */
    private final ByteArrayOutputStream f914a;

    public ke() {
        this(new kf.a());
    }

    public byte[] a(jy jyVar) {
        this.f914a.reset();
        jyVar.b(this.f24368a);
        return this.f914a.toByteArray();
    }

    public ke(kl klVar) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.f914a = byteArrayOutputStream;
        kq kqVar = new kq(byteArrayOutputStream);
        this.f913a = kqVar;
        this.f24368a = klVar.a(kqVar);
    }
}
