package org.spongycastle.jcajce.provider.asymmetric.ec;

import c.a.a.AbstractC0365o;
import c.a.a.C0342ba;
import c.a.a.j.a;
import c.a.a.r;
import c.a.b.h.d;
import c.a.b.h.i;
import c.a.c.a.a.b;
import com.huawei.hms.feature.dynamic.f.e;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.math.ec.ECCurve;

/* loaded from: classes5.dex */
public class BCECPublicKey implements ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
    public static final long serialVersionUID = 2422789860422731812L;
    public String algorithm;
    public transient b configuration;
    public transient i ecPublicKey;
    public transient ECParameterSpec ecSpec;
    public boolean withCompression;

    public BCECPublicKey(String str, BCECPublicKey bCECPublicKey) {
        this.algorithm = str;
        this.ecPublicKey = bCECPublicKey.ecPublicKey;
        this.ecSpec = bCECPublicKey.ecSpec;
        this.withCompression = bCECPublicKey.withCompression;
        this.configuration = bCECPublicKey.configuration;
    }

    private ECParameterSpec createSpec(EllipticCurve ellipticCurve, d dVar) {
        return new ECParameterSpec(ellipticCurve, new ECPoint(dVar.b().getAffineXCoord().toBigInteger(), dVar.b().getAffineYCoord().toBigInteger()), dVar.d(), dVar.c().intValue());
    }

    private void populateFromPubKeyInfo(c.a.a.j.b bVar) {
        byte b2;
        X962Parameters x962Parameters = X962Parameters.getInstance(bVar.getAlgorithm().getParameters());
        ECCurve curve = EC5Util.getCurve(this.configuration, x962Parameters);
        this.ecSpec = EC5Util.convertToSpec(x962Parameters, curve);
        byte[] bArrG = bVar.c().g();
        AbstractC0365o c0342ba = new C0342ba(bArrG);
        if (bArrG[0] == 4 && bArrG[1] == bArrG.length - 2 && (((b2 = bArrG[2]) == 2 || b2 == 3) && new X9IntegerConverter().getByteLength(curve) >= bArrG.length - 3)) {
            try {
                c0342ba = (AbstractC0365o) r.a(bArrG);
            } catch (IOException unused) {
                throw new IllegalArgumentException("error recovering public key");
            }
        }
        this.ecPublicKey = new i(new X9ECPoint(curve, c0342ba).getPoint(), ECUtil.getDomainParameters(this.configuration, x962Parameters));
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        byte[] bArr = (byte[]) objectInputStream.readObject();
        this.configuration = BouncyCastleProvider.CONFIGURATION;
        populateFromPubKeyInfo(c.a.a.j.b.getInstance(r.a(bArr)));
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }

    public i engineGetKeyParameters() {
        return this.ecPublicKey;
    }

    public org.spongycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        return eCParameterSpec != null ? EC5Util.convertSpec(eCParameterSpec, this.withCompression) : this.configuration.getEcImplicitlyCa();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BCECPublicKey)) {
            return false;
        }
        BCECPublicKey bCECPublicKey = (BCECPublicKey) obj;
        return this.ecPublicKey.b().equals(bCECPublicKey.ecPublicKey.b()) && engineGetSpec().equals(bCECPublicKey.engineGetSpec());
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new c.a.a.j.b(new a(X9ObjectIdentifiers.id_ecPublicKey, ECUtils.getDomainParametersFromName(this.ecSpec, this.withCompression)), AbstractC0365o.getInstance(new X9ECPoint(this.ecPublicKey.b(), this.withCompression).toASN1Primitive()).g()));
    }

    @Override // java.security.Key
    public String getFormat() {
        return e.f16169b;
    }

    @Override // org.spongycastle.jce.interfaces.ECKey
    public org.spongycastle.jce.spec.ECParameterSpec getParameters() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec == null) {
            return null;
        }
        return EC5Util.convertSpec(eCParameterSpec, this.withCompression);
    }

    @Override // java.security.interfaces.ECKey
    public ECParameterSpec getParams() {
        return this.ecSpec;
    }

    @Override // org.spongycastle.jce.interfaces.ECPublicKey
    public org.spongycastle.math.ec.ECPoint getQ() {
        org.spongycastle.math.ec.ECPoint eCPointB = this.ecPublicKey.b();
        return this.ecSpec == null ? eCPointB.getDetachedPoint() : eCPointB;
    }

    @Override // java.security.interfaces.ECPublicKey
    public ECPoint getW() {
        org.spongycastle.math.ec.ECPoint eCPointB = this.ecPublicKey.b();
        return new ECPoint(eCPointB.getAffineXCoord().toBigInteger(), eCPointB.getAffineYCoord().toBigInteger());
    }

    public int hashCode() {
        return this.ecPublicKey.b().hashCode() ^ engineGetSpec().hashCode();
    }

    @Override // org.spongycastle.jce.interfaces.ECPointEncoder
    public void setPointFormat(String str) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public String toString() {
        return ECUtil.publicKeyToString("EC", this.ecPublicKey.b(), engineGetSpec());
    }

    public BCECPublicKey(String str, ECPublicKeySpec eCPublicKeySpec, b bVar) {
        this.algorithm = str;
        ECParameterSpec params = eCPublicKeySpec.getParams();
        this.ecSpec = params;
        this.ecPublicKey = new i(EC5Util.convertPoint(params, eCPublicKeySpec.getW(), false), EC5Util.getDomainParameters(bVar, eCPublicKeySpec.getParams()));
        this.configuration = bVar;
    }

    public BCECPublicKey(String str, org.spongycastle.jce.spec.ECPublicKeySpec eCPublicKeySpec, b bVar) {
        this.algorithm = str;
        if (eCPublicKeySpec.getParams() != null) {
            EllipticCurve ellipticCurveConvertCurve = EC5Util.convertCurve(eCPublicKeySpec.getParams().getCurve(), eCPublicKeySpec.getParams().getSeed());
            this.ecPublicKey = new i(eCPublicKeySpec.getQ(), ECUtil.getDomainParameters(bVar, eCPublicKeySpec.getParams()));
            this.ecSpec = EC5Util.convertSpec(ellipticCurveConvertCurve, eCPublicKeySpec.getParams());
        } else {
            this.ecPublicKey = new i(bVar.getEcImplicitlyCa().getCurve().createPoint(eCPublicKeySpec.getQ().getAffineXCoord().toBigInteger(), eCPublicKeySpec.getQ().getAffineYCoord().toBigInteger()), EC5Util.getDomainParameters(bVar, null));
            this.ecSpec = null;
        }
        this.configuration = bVar;
    }

    public BCECPublicKey(String str, i iVar, ECParameterSpec eCParameterSpec, b bVar) {
        this.algorithm = "EC";
        d dVarA = iVar.a();
        this.algorithm = str;
        this.ecPublicKey = iVar;
        if (eCParameterSpec == null) {
            this.ecSpec = createSpec(EC5Util.convertCurve(dVarA.a(), dVarA.e()), dVarA);
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.configuration = bVar;
    }

    public BCECPublicKey(String str, i iVar, org.spongycastle.jce.spec.ECParameterSpec eCParameterSpec, b bVar) {
        this.algorithm = "EC";
        d dVarA = iVar.a();
        this.algorithm = str;
        if (eCParameterSpec == null) {
            this.ecSpec = createSpec(EC5Util.convertCurve(dVarA.a(), dVarA.e()), dVarA);
        } else {
            this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        }
        this.ecPublicKey = iVar;
        this.configuration = bVar;
    }

    public BCECPublicKey(String str, i iVar, b bVar) {
        this.algorithm = str;
        this.ecPublicKey = iVar;
        this.ecSpec = null;
        this.configuration = bVar;
    }

    public BCECPublicKey(ECPublicKey eCPublicKey, b bVar) {
        this.algorithm = "EC";
        this.algorithm = eCPublicKey.getAlgorithm();
        ECParameterSpec params = eCPublicKey.getParams();
        this.ecSpec = params;
        this.ecPublicKey = new i(EC5Util.convertPoint(params, eCPublicKey.getW(), false), EC5Util.getDomainParameters(bVar, eCPublicKey.getParams()));
    }

    public BCECPublicKey(String str, c.a.a.j.b bVar, b bVar2) {
        this.algorithm = str;
        this.configuration = bVar2;
        populateFromPubKeyInfo(bVar);
    }
}
