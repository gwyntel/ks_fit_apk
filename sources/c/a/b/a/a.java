package c.a.b.a;

import c.a.b.c;
import c.a.b.e;
import c.a.b.h.h;
import c.a.b.h.i;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes2.dex */
public class a implements c {

    /* renamed from: a, reason: collision with root package name */
    public h f8000a;

    @Override // c.a.b.c
    public BigInteger a(e eVar) {
        i iVar = (i) eVar;
        if (!iVar.a().equals(this.f8000a.a())) {
            throw new IllegalStateException("ECDH public key has wrong domain parameters");
        }
        ECPoint eCPointNormalize = iVar.b().multiply(this.f8000a.b()).normalize();
        if (eCPointNormalize.isInfinity()) {
            throw new IllegalStateException("Infinity is not a valid agreement value for ECDH");
        }
        return eCPointNormalize.getAffineXCoord().toBigInteger();
    }

    @Override // c.a.b.c
    public void init(e eVar) {
        this.f8000a = (h) eVar;
    }
}
