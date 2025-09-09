package org.spongycastle.jcajce.provider.asymmetric.util;

import c.a.a.C0360j;
import c.a.a.C0364n;
import c.a.a.C0367q;
import c.a.a.InterfaceC0346f;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;

/* loaded from: classes5.dex */
public class PKCS12BagAttributeCarrierImpl implements PKCS12BagAttributeCarrier {
    public Hashtable pkcs12Attributes;
    public Vector pkcs12Ordering;

    public PKCS12BagAttributeCarrierImpl(Hashtable hashtable, Vector vector) {
        this.pkcs12Attributes = hashtable;
        this.pkcs12Ordering = vector;
    }

    public Hashtable getAttributes() {
        return this.pkcs12Attributes;
    }

    @Override // org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public InterfaceC0346f getBagAttribute(C0364n c0364n) {
        return (InterfaceC0346f) this.pkcs12Attributes.get(c0364n);
    }

    @Override // org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public Enumeration getBagAttributeKeys() {
        return this.pkcs12Ordering.elements();
    }

    public Vector getOrdering() {
        return this.pkcs12Ordering;
    }

    public void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        Object object = objectInputStream.readObject();
        if (object instanceof Hashtable) {
            this.pkcs12Attributes = (Hashtable) object;
            this.pkcs12Ordering = (Vector) objectInputStream.readObject();
        } else {
            C0360j c0360j = new C0360j((byte[]) object);
            while (true) {
                C0364n c0364n = (C0364n) c0360j.d();
                if (c0364n == null) {
                    return;
                } else {
                    setBagAttribute(c0364n, c0360j.d());
                }
            }
        }
    }

    @Override // org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(C0364n c0364n, InterfaceC0346f interfaceC0346f) {
        if (this.pkcs12Attributes.containsKey(c0364n)) {
            this.pkcs12Attributes.put(c0364n, interfaceC0346f);
        } else {
            this.pkcs12Attributes.put(c0364n, interfaceC0346f);
            this.pkcs12Ordering.addElement(c0364n);
        }
    }

    public int size() {
        return this.pkcs12Ordering.size();
    }

    public void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.pkcs12Ordering.size() == 0) {
            objectOutputStream.writeObject(new Hashtable());
            objectOutputStream.writeObject(new Vector());
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        C0367q c0367q = new C0367q(byteArrayOutputStream);
        Enumeration bagAttributeKeys = getBagAttributeKeys();
        while (bagAttributeKeys.hasMoreElements()) {
            C0364n c0364n = (C0364n) bagAttributeKeys.nextElement();
            c0367q.a((InterfaceC0346f) c0364n);
            c0367q.a((InterfaceC0346f) this.pkcs12Attributes.get(c0364n));
        }
        objectOutputStream.writeObject(byteArrayOutputStream.toByteArray());
    }

    public PKCS12BagAttributeCarrierImpl() {
        this(new Hashtable(), new Vector());
    }
}
