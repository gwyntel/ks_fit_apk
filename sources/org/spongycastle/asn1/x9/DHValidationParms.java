package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0368s;
import c.a.a.AbstractC0374y;
import c.a.a.C0347g;
import c.a.a.C0361k;
import c.a.a.S;
import c.a.a.fa;
import c.a.a.r;

/* loaded from: classes5.dex */
public class DHValidationParms extends AbstractC0363m {
    public C0361k pgenCounter;
    public S seed;

    public DHValidationParms(S s2, C0361k c0361k) {
        if (s2 == null) {
            throw new IllegalArgumentException("'seed' cannot be null");
        }
        if (c0361k == null) {
            throw new IllegalArgumentException("'pgenCounter' cannot be null");
        }
        this.seed = s2;
        this.pgenCounter = c0361k;
    }

    public static DHValidationParms getInstance(AbstractC0374y abstractC0374y, boolean z2) {
        return getInstance(AbstractC0368s.getInstance(abstractC0374y, z2));
    }

    public C0361k getPgenCounter() {
        return this.pgenCounter;
    }

    public S getSeed() {
        return this.seed;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(this.seed);
        c0347g.a(this.pgenCounter);
        return new fa(c0347g);
    }

    public static DHValidationParms getInstance(Object obj) {
        if (obj instanceof DHValidationParms) {
            return (DHValidationParms) obj;
        }
        if (obj != null) {
            return new DHValidationParms(AbstractC0368s.getInstance(obj));
        }
        return null;
    }

    public DHValidationParms(AbstractC0368s abstractC0368s) {
        if (abstractC0368s.h() == 2) {
            this.seed = S.getInstance(abstractC0368s.a(0));
            this.pgenCounter = C0361k.getInstance(abstractC0368s.a(1));
        } else {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0368s.h());
        }
    }
}
