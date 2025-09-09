package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0365o;
import c.a.a.AbstractC0368s;
import c.a.a.AbstractC0374y;
import c.a.a.C0347g;
import c.a.a.fa;
import c.a.a.ka;
import c.a.a.r;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class OtherInfo extends AbstractC0363m {
    public KeySpecificInfo keyInfo;
    public AbstractC0365o partyAInfo;
    public AbstractC0365o suppPubInfo;

    public OtherInfo(KeySpecificInfo keySpecificInfo, AbstractC0365o abstractC0365o, AbstractC0365o abstractC0365o2) {
        this.keyInfo = keySpecificInfo;
        this.partyAInfo = abstractC0365o;
        this.suppPubInfo = abstractC0365o2;
    }

    public static OtherInfo getInstance(Object obj) {
        if (obj instanceof OtherInfo) {
            return (OtherInfo) obj;
        }
        if (obj != null) {
            return new OtherInfo(AbstractC0368s.getInstance(obj));
        }
        return null;
    }

    public KeySpecificInfo getKeyInfo() {
        return this.keyInfo;
    }

    public AbstractC0365o getPartyAInfo() {
        return this.partyAInfo;
    }

    public AbstractC0365o getSuppPubInfo() {
        return this.suppPubInfo;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(this.keyInfo);
        AbstractC0365o abstractC0365o = this.partyAInfo;
        if (abstractC0365o != null) {
            c0347g.a(new ka(0, abstractC0365o));
        }
        c0347g.a(new ka(2, this.suppPubInfo));
        return new fa(c0347g);
    }

    public OtherInfo(AbstractC0368s abstractC0368s) {
        Enumeration enumerationG = abstractC0368s.g();
        this.keyInfo = KeySpecificInfo.getInstance(enumerationG.nextElement());
        while (enumerationG.hasMoreElements()) {
            AbstractC0374y abstractC0374y = (AbstractC0374y) enumerationG.nextElement();
            if (abstractC0374y.h() == 0) {
                this.partyAInfo = (AbstractC0365o) abstractC0374y.g();
            } else if (abstractC0374y.h() == 2) {
                this.suppPubInfo = (AbstractC0365o) abstractC0374y.g();
            }
        }
    }
}
