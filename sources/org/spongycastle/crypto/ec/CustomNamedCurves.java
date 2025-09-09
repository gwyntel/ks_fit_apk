package org.spongycastle.crypto.ec;

import c.a.a.C0364n;
import c.a.a.h.J;
import c.a.d.l;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.custom.djb.Curve25519;
import org.spongycastle.math.ec.custom.sec.SecP256R1Curve;
import org.spongycastle.math.ec.endo.GLVTypeBEndomorphism;
import org.spongycastle.math.ec.endo.GLVTypeBParameters;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes5.dex */
public class CustomNamedCurves {
    public static X9ECParametersHolder curve25519 = new X9ECParametersHolder() { // from class: org.spongycastle.crypto.ec.CustomNamedCurves.1
        @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
        public X9ECParameters createParameters() {
            ECCurve eCCurveConfigureCurve = CustomNamedCurves.configureCurve(new Curve25519());
            return new X9ECParameters(eCCurveConfigureCurve, new X9ECPoint(eCCurveConfigureCurve, Hex.decode("042AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD245A20AE19A1B8A086B4E01EDD2C7748D14C923D4D7E6D7C61B229E9C5A27ECED3D9")), eCCurveConfigureCurve.getOrder(), eCCurveConfigureCurve.getCofactor(), (byte[]) null);
        }
    };
    public static X9ECParametersHolder secp256r1 = new X9ECParametersHolder() { // from class: org.spongycastle.crypto.ec.CustomNamedCurves.2
        @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
        public X9ECParameters createParameters() {
            byte[] bArrDecode = Hex.decode("C49D360886E704936A6678E1139D26B7819F7E90");
            ECCurve eCCurveConfigureCurve = CustomNamedCurves.configureCurve(new SecP256R1Curve());
            return new X9ECParameters(eCCurveConfigureCurve, new X9ECPoint(eCCurveConfigureCurve, Hex.decode("046B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C2964FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5")), eCCurveConfigureCurve.getOrder(), eCCurveConfigureCurve.getCofactor(), bArrDecode);
        }
    };
    public static final Hashtable nameToCurve = new Hashtable();
    public static final Hashtable nameToOID = new Hashtable();
    public static final Hashtable oidToCurve = new Hashtable();
    public static final Hashtable oidToName = new Hashtable();
    public static final Vector names = new Vector();

    static {
        defineCurve("curve25519", curve25519);
        C0364n c0364n = J.H;
        defineCurveWithOID("secp256r1", c0364n, secp256r1);
        defineCurveAlias("P-256", c0364n);
    }

    public static ECCurve configureCurve(ECCurve eCCurve) {
        return eCCurve;
    }

    public static ECCurve configureCurveGLV(ECCurve eCCurve, GLVTypeBParameters gLVTypeBParameters) {
        return eCCurve.configure().setEndomorphism(new GLVTypeBEndomorphism(eCCurve, gLVTypeBParameters)).create();
    }

    public static void defineCurve(String str, X9ECParametersHolder x9ECParametersHolder) {
        names.addElement(str);
        nameToCurve.put(l.a(str), x9ECParametersHolder);
    }

    public static void defineCurveAlias(String str, C0364n c0364n) {
        Object obj = oidToCurve.get(c0364n);
        if (obj == null) {
            throw new IllegalStateException();
        }
        String strA = l.a(str);
        nameToOID.put(strA, c0364n);
        nameToCurve.put(strA, obj);
    }

    public static void defineCurveWithOID(String str, C0364n c0364n, X9ECParametersHolder x9ECParametersHolder) {
        names.addElement(str);
        oidToName.put(c0364n, str);
        oidToCurve.put(c0364n, x9ECParametersHolder);
        String strA = l.a(str);
        nameToOID.put(strA, c0364n);
        nameToCurve.put(strA, x9ECParametersHolder);
    }

    public static X9ECParameters getByName(String str) {
        X9ECParametersHolder x9ECParametersHolder = (X9ECParametersHolder) nameToCurve.get(l.a(str));
        if (x9ECParametersHolder == null) {
            return null;
        }
        return x9ECParametersHolder.getParameters();
    }

    public static X9ECParameters getByOID(C0364n c0364n) {
        X9ECParametersHolder x9ECParametersHolder = (X9ECParametersHolder) oidToCurve.get(c0364n);
        if (x9ECParametersHolder == null) {
            return null;
        }
        return x9ECParametersHolder.getParameters();
    }

    public static String getName(C0364n c0364n) {
        return (String) oidToName.get(c0364n);
    }

    public static Enumeration getNames() {
        return names.elements();
    }

    public static C0364n getOID(String str) {
        return (C0364n) nameToOID.get(l.a(str));
    }
}
