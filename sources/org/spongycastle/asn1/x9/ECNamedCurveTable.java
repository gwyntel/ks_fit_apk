package org.spongycastle.asn1.x9;

import c.a.a.C0364n;
import c.a.a.a.b;
import c.a.a.c.c;
import c.a.a.e.a;
import c.a.a.h.I;
import c.a.a.i.o;
import java.util.Enumeration;
import java.util.Vector;

/* loaded from: classes5.dex */
public class ECNamedCurveTable {
    public static void addEnumeration(Vector vector, Enumeration enumeration) {
        while (enumeration.hasMoreElements()) {
            vector.addElement(enumeration.nextElement());
        }
    }

    public static X9ECParameters getByName(String str) {
        X9ECParameters byName = X962NamedCurves.getByName(str);
        if (byName == null) {
            byName = I.c(str);
        }
        if (byName == null) {
            byName = a.a(str);
        }
        if (byName == null) {
            byName = o.a(str);
        }
        if (byName == null) {
            byName = b.c(str);
        }
        return byName == null ? c.c(str) : byName;
    }

    public static X9ECParameters getByOID(C0364n c0364n) {
        X9ECParameters byOID = X962NamedCurves.getByOID(c0364n);
        if (byOID == null) {
            byOID = I.a(c0364n);
        }
        if (byOID == null) {
            byOID = o.a(c0364n);
        }
        if (byOID == null) {
            byOID = b.a(c0364n);
        }
        return byOID == null ? c.a(c0364n) : byOID;
    }

    public static String getName(C0364n c0364n) {
        String strB = a.b(c0364n);
        if (strB == null) {
            strB = I.b(c0364n);
        }
        if (strB == null) {
            strB = o.b(c0364n);
        }
        if (strB == null) {
            strB = X962NamedCurves.getName(c0364n);
        }
        if (strB == null) {
            strB = c.a.a.b.b.a(c0364n);
        }
        return strB == null ? c.b(c0364n) : strB;
    }

    public static Enumeration getNames() {
        Vector vector = new Vector();
        addEnumeration(vector, X962NamedCurves.getNames());
        addEnumeration(vector, I.a());
        addEnumeration(vector, a.a());
        addEnumeration(vector, o.a());
        addEnumeration(vector, b.a());
        addEnumeration(vector, c.a());
        return vector.elements();
    }

    public static C0364n getOID(String str) {
        C0364n oid = X962NamedCurves.getOID(str);
        if (oid == null) {
            oid = I.d(str);
        }
        if (oid == null) {
            oid = a.b(str);
        }
        if (oid == null) {
            oid = o.b(str);
        }
        if (oid == null) {
            oid = b.d(str);
        }
        return oid == null ? c.d(str) : oid;
    }
}
