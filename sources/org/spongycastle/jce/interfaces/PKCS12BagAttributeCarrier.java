package org.spongycastle.jce.interfaces;

import c.a.a.C0364n;
import c.a.a.InterfaceC0346f;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public interface PKCS12BagAttributeCarrier {
    InterfaceC0346f getBagAttribute(C0364n c0364n);

    Enumeration getBagAttributeKeys();

    void setBagAttribute(C0364n c0364n, InterfaceC0346f interfaceC0346f);
}
