package org.spongycastle.jcajce.provider.asymmetric.util;

import c.a.a.C0364n;
import c.a.a.c.c;
import c.a.a.h.I;
import c.a.a.i.o;
import c.a.b.h.b;
import c.a.b.h.g;
import c.a.b.h.h;
import c.a.b.h.i;
import c.a.d.a;
import c.a.d.d;
import c.a.d.l;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Enumeration;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X962NamedCurves;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.jce.interfaces.ECPrivateKey;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public class ECUtil {
    public static ECPoint calculateQ(BigInteger bigInteger, ECParameterSpec eCParameterSpec) {
        return eCParameterSpec.getG().multiply(bigInteger).normalize();
    }

    public static int[] convertMidTerms(int[] iArr) {
        int i2;
        int[] iArr2 = new int[3];
        if (iArr.length == 1) {
            iArr2[0] = iArr[0];
        } else {
            if (iArr.length != 3) {
                throw new IllegalArgumentException("Only Trinomials and pentanomials supported");
            }
            int i3 = iArr[0];
            int i4 = iArr[1];
            if (i3 >= i4 || i3 >= (i2 = iArr[2])) {
                int i5 = iArr[2];
                if (i4 < i5) {
                    iArr2[0] = i4;
                    int i6 = iArr[0];
                    if (i6 < i5) {
                        iArr2[1] = i6;
                        iArr2[2] = i5;
                    } else {
                        iArr2[1] = i5;
                        iArr2[2] = i6;
                    }
                } else {
                    iArr2[0] = i5;
                    int i7 = iArr[0];
                    if (i7 < i4) {
                        iArr2[1] = i7;
                        iArr2[2] = iArr[1];
                    } else {
                        iArr2[1] = i4;
                        iArr2[2] = i7;
                    }
                }
            } else {
                iArr2[0] = i3;
                if (i4 < i2) {
                    iArr2[1] = i4;
                    iArr2[2] = i2;
                } else {
                    iArr2[1] = i2;
                    iArr2[2] = iArr[1];
                }
            }
        }
        return iArr2;
    }

    public static String generateKeyFingerprint(ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        ECCurve curve = eCParameterSpec.getCurve();
        return curve != null ? new d(a.a(eCPoint.getEncoded(false), curve.getA().getEncoded(), curve.getB().getEncoded(), eCParameterSpec.getG().getEncoded(false))).toString() : new d(eCPoint.getEncoded(false)).toString();
    }

    public static b generatePrivateKeyParameter(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey instanceof ECPrivateKey) {
            ECPrivateKey eCPrivateKey = (ECPrivateKey) privateKey;
            ECParameterSpec parameters = eCPrivateKey.getParameters();
            if (parameters == null) {
                parameters = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            }
            return new h(eCPrivateKey.getD(), new c.a.b.h.d(parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed()));
        }
        if (privateKey instanceof java.security.interfaces.ECPrivateKey) {
            java.security.interfaces.ECPrivateKey eCPrivateKey2 = (java.security.interfaces.ECPrivateKey) privateKey;
            ECParameterSpec eCParameterSpecConvertSpec = EC5Util.convertSpec(eCPrivateKey2.getParams(), false);
            return new h(eCPrivateKey2.getS(), new c.a.b.h.d(eCParameterSpecConvertSpec.getCurve(), eCParameterSpecConvertSpec.getG(), eCParameterSpecConvertSpec.getN(), eCParameterSpecConvertSpec.getH(), eCParameterSpecConvertSpec.getSeed()));
        }
        try {
            byte[] encoded = privateKey.getEncoded();
            if (encoded == null) {
                throw new InvalidKeyException("no encoding for EC private key");
            }
            PrivateKey privateKey2 = BouncyCastleProvider.getPrivateKey(c.a.a.f.a.getInstance(encoded));
            if (privateKey2 instanceof java.security.interfaces.ECPrivateKey) {
                return generatePrivateKeyParameter(privateKey2);
            }
            throw new InvalidKeyException("can't identify EC private key.");
        } catch (Exception e2) {
            throw new InvalidKeyException("cannot identify EC private key: " + e2.toString());
        }
    }

    public static b generatePublicKeyParameter(PublicKey publicKey) throws InvalidKeyException {
        if (publicKey instanceof ECPublicKey) {
            ECPublicKey eCPublicKey = (ECPublicKey) publicKey;
            ECParameterSpec parameters = eCPublicKey.getParameters();
            return new i(eCPublicKey.getQ(), new c.a.b.h.d(parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed()));
        }
        if (publicKey instanceof java.security.interfaces.ECPublicKey) {
            java.security.interfaces.ECPublicKey eCPublicKey2 = (java.security.interfaces.ECPublicKey) publicKey;
            ECParameterSpec eCParameterSpecConvertSpec = EC5Util.convertSpec(eCPublicKey2.getParams(), false);
            return new i(EC5Util.convertPoint(eCPublicKey2.getParams(), eCPublicKey2.getW(), false), new c.a.b.h.d(eCParameterSpecConvertSpec.getCurve(), eCParameterSpecConvertSpec.getG(), eCParameterSpecConvertSpec.getN(), eCParameterSpecConvertSpec.getH(), eCParameterSpecConvertSpec.getSeed()));
        }
        try {
            byte[] encoded = publicKey.getEncoded();
            if (encoded == null) {
                throw new InvalidKeyException("no encoding for EC public key");
            }
            PublicKey publicKey2 = BouncyCastleProvider.getPublicKey(c.a.a.j.b.getInstance(encoded));
            if (publicKey2 instanceof java.security.interfaces.ECPublicKey) {
                return generatePublicKeyParameter(publicKey2);
            }
            throw new InvalidKeyException("cannot identify EC public key.");
        } catch (Exception e2) {
            throw new InvalidKeyException("cannot identify EC public key: " + e2.toString());
        }
    }

    public static String getCurveName(C0364n c0364n) {
        String name = X962NamedCurves.getName(c0364n);
        if (name != null) {
            return name;
        }
        String strB = I.b(c0364n);
        if (strB == null) {
            strB = c.a.a.e.a.b(c0364n);
        }
        if (strB == null) {
            strB = o.b(c0364n);
        }
        if (strB == null) {
            strB = c.a.a.b.b.a(c0364n);
        }
        if (strB == null) {
            strB = c.a.a.a.b.b(c0364n);
        }
        return strB == null ? c.b(c0364n) : strB;
    }

    public static c.a.b.h.d getDomainParameters(c.a.c.a.a.b bVar, ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec instanceof ECNamedCurveParameterSpec) {
            ECNamedCurveParameterSpec eCNamedCurveParameterSpec = (ECNamedCurveParameterSpec) eCParameterSpec;
            return new g(getNamedCurveOid(eCNamedCurveParameterSpec.getName()), eCNamedCurveParameterSpec.getCurve(), eCNamedCurveParameterSpec.getG(), eCNamedCurveParameterSpec.getN(), eCNamedCurveParameterSpec.getH(), eCNamedCurveParameterSpec.getSeed());
        }
        if (eCParameterSpec != null) {
            return new c.a.b.h.d(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH(), eCParameterSpec.getSeed());
        }
        ECParameterSpec ecImplicitlyCa = bVar.getEcImplicitlyCa();
        return new c.a.b.h.d(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
    }

    public static X9ECParameters getNamedCurveByName(String str) {
        X9ECParameters byName = CustomNamedCurves.getByName(str);
        if (byName != null) {
            return byName;
        }
        X9ECParameters byName2 = X962NamedCurves.getByName(str);
        if (byName2 == null) {
            byName2 = I.c(str);
        }
        if (byName2 == null) {
            byName2 = c.a.a.e.a.a(str);
        }
        if (byName2 == null) {
            byName2 = o.a(str);
        }
        if (byName2 == null) {
            byName2 = c.a.a.a.b.c(str);
        }
        return byName2 == null ? c.c(str) : byName2;
    }

    public static X9ECParameters getNamedCurveByOid(C0364n c0364n) {
        X9ECParameters byOID = CustomNamedCurves.getByOID(c0364n);
        if (byOID != null) {
            return byOID;
        }
        X9ECParameters byOID2 = X962NamedCurves.getByOID(c0364n);
        if (byOID2 == null) {
            byOID2 = I.a(c0364n);
        }
        if (byOID2 == null) {
            byOID2 = c.a.a.e.a.a(c0364n);
        }
        if (byOID2 == null) {
            byOID2 = o.a(c0364n);
        }
        if (byOID2 == null) {
            byOID2 = c.a.a.a.b.a(c0364n);
        }
        return byOID2 == null ? c.a(c0364n) : byOID2;
    }

    public static C0364n getNamedCurveOid(String str) {
        if (str.indexOf(32) > 0) {
            str = str.substring(str.indexOf(32) + 1);
        }
        try {
            return (str.charAt(0) < '0' || str.charAt(0) > '2') ? lookupOidByName(str) : new C0364n(str);
        } catch (IllegalArgumentException unused) {
            return lookupOidByName(str);
        }
    }

    public static int getOrderBitLength(c.a.c.a.a.b bVar, BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger != null) {
            return bigInteger.bitLength();
        }
        ECParameterSpec ecImplicitlyCa = bVar.getEcImplicitlyCa();
        return ecImplicitlyCa == null ? bigInteger2.bitLength() : ecImplicitlyCa.getN().bitLength();
    }

    public static C0364n lookupOidByName(String str) {
        C0364n oid = X962NamedCurves.getOID(str);
        if (oid != null) {
            return oid;
        }
        C0364n c0364nD = I.d(str);
        if (c0364nD == null) {
            c0364nD = c.a.a.e.a.b(str);
        }
        if (c0364nD == null) {
            c0364nD = o.b(str);
        }
        if (c0364nD == null) {
            c0364nD = c.a.a.b.b.a(str);
        }
        if (c0364nD == null) {
            c0364nD = c.a.a.a.b.d(str);
        }
        return c0364nD == null ? c.d(str) : c0364nD;
    }

    public static String privateKeyToString(String str, BigInteger bigInteger, ECParameterSpec eCParameterSpec) {
        StringBuffer stringBuffer = new StringBuffer();
        String strA = l.a();
        ECPoint eCPointCalculateQ = calculateQ(bigInteger, eCParameterSpec);
        stringBuffer.append(str);
        stringBuffer.append(" Private Key [");
        stringBuffer.append(generateKeyFingerprint(eCPointCalculateQ, eCParameterSpec));
        stringBuffer.append("]");
        stringBuffer.append(strA);
        stringBuffer.append("            X: ");
        stringBuffer.append(eCPointCalculateQ.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(strA);
        stringBuffer.append("            Y: ");
        stringBuffer.append(eCPointCalculateQ.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(strA);
        return stringBuffer.toString();
    }

    public static String publicKeyToString(String str, ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        StringBuffer stringBuffer = new StringBuffer();
        String strA = l.a();
        stringBuffer.append(str);
        stringBuffer.append(" Public Key [");
        stringBuffer.append(generateKeyFingerprint(eCPoint, eCParameterSpec));
        stringBuffer.append("]");
        stringBuffer.append(strA);
        stringBuffer.append("            X: ");
        stringBuffer.append(eCPoint.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(strA);
        stringBuffer.append("            Y: ");
        stringBuffer.append(eCPoint.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(strA);
        return stringBuffer.toString();
    }

    public static C0364n getNamedCurveOid(ECParameterSpec eCParameterSpec) {
        Enumeration names = ECNamedCurveTable.getNames();
        while (names.hasMoreElements()) {
            String str = (String) names.nextElement();
            X9ECParameters byName = ECNamedCurveTable.getByName(str);
            if (byName.getN().equals(eCParameterSpec.getN()) && byName.getH().equals(eCParameterSpec.getH()) && byName.getCurve().equals(eCParameterSpec.getCurve()) && byName.getG().equals(eCParameterSpec.getG())) {
                return ECNamedCurveTable.getOID(str);
            }
        }
        return null;
    }

    public static c.a.b.h.d getDomainParameters(c.a.c.a.a.b bVar, X962Parameters x962Parameters) {
        c.a.b.h.d dVar;
        if (x962Parameters.isNamedCurve()) {
            C0364n c0364n = C0364n.getInstance(x962Parameters.getParameters());
            X9ECParameters namedCurveByOid = getNamedCurveByOid(c0364n);
            if (namedCurveByOid == null) {
                namedCurveByOid = (X9ECParameters) bVar.getAdditionalECParameters().get(c0364n);
            }
            return new g(c0364n, namedCurveByOid.getCurve(), namedCurveByOid.getG(), namedCurveByOid.getN(), namedCurveByOid.getH(), namedCurveByOid.getSeed());
        }
        if (x962Parameters.isImplicitlyCA()) {
            ECParameterSpec ecImplicitlyCa = bVar.getEcImplicitlyCa();
            dVar = new c.a.b.h.d(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
        } else {
            X9ECParameters x9ECParameters = X9ECParameters.getInstance(x962Parameters.getParameters());
            dVar = new c.a.b.h.d(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH(), x9ECParameters.getSeed());
        }
        return dVar;
    }
}
