package org.spongycastle.jcajce.provider.asymmetric.ec;

import c.a.a.C0361k;
import c.a.a.C0364n;
import c.a.a.InterfaceC0346f;
import c.a.a.S;
import c.a.a.f.a;
import c.a.a.h.C0349a;
import c.a.a.r;
import c.a.b.h.d;
import c.a.b.h.h;
import c.a.c.a.a.b;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.util.Enumeration;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECPrivateKeySpec;
import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public class BCECPrivateKey implements ECPrivateKey, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    public static final long serialVersionUID = 994553197664784084L;
    public String algorithm;
    public transient PKCS12BagAttributeCarrierImpl attrCarrier;
    public transient b configuration;

    /* renamed from: d, reason: collision with root package name */
    public transient BigInteger f26787d;
    public transient ECParameterSpec ecSpec;
    public transient S publicKey;
    public boolean withCompression;

    public BCECPrivateKey() {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private ECPoint calculateQ(org.spongycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        return eCParameterSpec.getG().multiply(this.f26787d).normalize();
    }

    private S getPublicKeyDetails(BCECPublicKey bCECPublicKey) {
        try {
            return c.a.a.j.b.getInstance(r.a(bCECPublicKey.getEncoded())).c();
        } catch (IOException unused) {
            return null;
        }
    }

    private void populateFromPrivKeyInfo(a aVar) {
        X962Parameters x962Parameters = X962Parameters.getInstance(aVar.c().getParameters());
        this.ecSpec = EC5Util.convertToSpec(x962Parameters, EC5Util.getCurve(this.configuration, x962Parameters));
        InterfaceC0346f interfaceC0346fD = aVar.d();
        if (interfaceC0346fD instanceof C0361k) {
            this.f26787d = C0361k.getInstance(interfaceC0346fD).getValue();
            return;
        }
        C0349a c0349a = C0349a.getInstance(interfaceC0346fD);
        this.f26787d = c0349a.c();
        this.publicKey = c0349a.d();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        byte[] bArr = (byte[]) objectInputStream.readObject();
        this.configuration = BouncyCastleProvider.CONFIGURATION;
        populateFromPrivKeyInfo(a.getInstance(r.a(bArr)));
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }

    public org.spongycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        return eCParameterSpec != null ? EC5Util.convertSpec(eCParameterSpec, this.withCompression) : this.configuration.getEcImplicitlyCa();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BCECPrivateKey)) {
            return false;
        }
        BCECPrivateKey bCECPrivateKey = (BCECPrivateKey) obj;
        return getD().equals(bCECPrivateKey.getD()) && engineGetSpec().equals(bCECPrivateKey.engineGetSpec());
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override // org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public InterfaceC0346f getBagAttribute(C0364n c0364n) {
        return this.attrCarrier.getBagAttribute(c0364n);
    }

    @Override // org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    @Override // org.spongycastle.jce.interfaces.ECPrivateKey
    public BigInteger getD() {
        return this.f26787d;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        X962Parameters domainParametersFromName = ECUtils.getDomainParametersFromName(this.ecSpec, this.withCompression);
        ECParameterSpec eCParameterSpec = this.ecSpec;
        int orderBitLength = eCParameterSpec == null ? ECUtil.getOrderBitLength(this.configuration, null, getS()) : ECUtil.getOrderBitLength(this.configuration, eCParameterSpec.getOrder(), getS());
        try {
            return new a(new c.a.a.j.a(X9ObjectIdentifiers.id_ecPublicKey, domainParametersFromName), this.publicKey != null ? new C0349a(orderBitLength, getS(), this.publicKey, domainParametersFromName) : new C0349a(orderBitLength, getS(), domainParametersFromName)).getEncoded("DER");
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // java.security.Key
    public String getFormat() {
        return "PKCS#8";
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

    @Override // java.security.interfaces.ECPrivateKey
    public BigInteger getS() {
        return this.f26787d;
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    @Override // org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(C0364n c0364n, InterfaceC0346f interfaceC0346f) {
        this.attrCarrier.setBagAttribute(c0364n, interfaceC0346f);
    }

    @Override // org.spongycastle.jce.interfaces.ECPointEncoder
    public void setPointFormat(String str) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public String toString() {
        return ECUtil.privateKeyToString("EC", this.f26787d, engineGetSpec());
    }

    public BCECPrivateKey(ECPrivateKey eCPrivateKey, b bVar) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.f26787d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
        this.configuration = bVar;
    }

    public BCECPrivateKey(String str, ECPrivateKeySpec eCPrivateKeySpec, b bVar) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.f26787d = eCPrivateKeySpec.getD();
        if (eCPrivateKeySpec.getParams() != null) {
            this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams());
        } else {
            this.ecSpec = null;
        }
        this.configuration = bVar;
    }

    public BCECPrivateKey(String str, java.security.spec.ECPrivateKeySpec eCPrivateKeySpec, b bVar) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.f26787d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
        this.configuration = bVar;
    }

    public BCECPrivateKey(String str, BCECPrivateKey bCECPrivateKey) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.f26787d = bCECPrivateKey.f26787d;
        this.ecSpec = bCECPrivateKey.ecSpec;
        this.withCompression = bCECPrivateKey.withCompression;
        this.attrCarrier = bCECPrivateKey.attrCarrier;
        this.publicKey = bCECPrivateKey.publicKey;
        this.configuration = bCECPrivateKey.configuration;
    }

    public BCECPrivateKey(String str, h hVar, BCECPublicKey bCECPublicKey, ECParameterSpec eCParameterSpec, b bVar) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        d dVarA = hVar.a();
        this.algorithm = str;
        this.f26787d = hVar.b();
        this.configuration = bVar;
        if (eCParameterSpec == null) {
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(dVarA.a(), dVarA.e()), new java.security.spec.ECPoint(dVarA.b().getAffineXCoord().toBigInteger(), dVarA.b().getAffineYCoord().toBigInteger()), dVarA.d(), dVarA.c().intValue());
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.publicKey = getPublicKeyDetails(bCECPublicKey);
    }

    public BCECPrivateKey(String str, h hVar, BCECPublicKey bCECPublicKey, org.spongycastle.jce.spec.ECParameterSpec eCParameterSpec, b bVar) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        d dVarA = hVar.a();
        this.algorithm = str;
        this.f26787d = hVar.b();
        this.configuration = bVar;
        if (eCParameterSpec == null) {
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(dVarA.a(), dVarA.e()), new java.security.spec.ECPoint(dVarA.b().getAffineXCoord().toBigInteger(), dVarA.b().getAffineYCoord().toBigInteger()), dVarA.d(), dVarA.c().intValue());
        } else {
            this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        }
        try {
            this.publicKey = getPublicKeyDetails(bCECPublicKey);
        } catch (Exception unused) {
            this.publicKey = null;
        }
    }

    public BCECPrivateKey(String str, h hVar, b bVar) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.f26787d = hVar.b();
        this.ecSpec = null;
        this.configuration = bVar;
    }

    public BCECPrivateKey(String str, a aVar, b bVar) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.configuration = bVar;
        populateFromPrivKeyInfo(aVar);
    }
}
