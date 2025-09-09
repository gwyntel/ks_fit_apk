package c.a.b.h;

import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class e extends c.a.b.j {

    /* renamed from: c, reason: collision with root package name */
    public d f8099c;

    public e(d dVar, SecureRandom secureRandom) {
        super(secureRandom, dVar.d().bitLength());
        this.f8099c = dVar;
    }

    public d b() {
        return this.f8099c;
    }
}
