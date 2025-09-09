package org.spongycastle.jcajce.provider.asymmetric.util;

import c.a.a.C0364n;
import c.a.a.b.a;
import c.a.a.e.b;
import c.a.b.f;
import c.a.d.e;
import c.a.d.l;
import com.yc.utesdk.utils.close.AESUtils;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.ShortBufferException;

/* loaded from: classes5.dex */
public abstract class BaseAgreementSpi extends KeyAgreementSpi {
    public static final Map<String, C0364n> defaultOids;
    public static final Hashtable des;
    public static final Map<String, Integer> keySizes;
    public static final Map<String, String> nameTable;
    public static final Hashtable oids;
    public final String kaAlgorithm;
    public final f kdf;
    public byte[] ukmParameters;

    static {
        HashMap map = new HashMap();
        defaultOids = map;
        HashMap map2 = new HashMap();
        keySizes = map2;
        HashMap map3 = new HashMap();
        nameTable = map3;
        Hashtable hashtable = new Hashtable();
        oids = hashtable;
        Hashtable hashtable2 = new Hashtable();
        des = hashtable2;
        Integer numA = e.a(64);
        Integer numA2 = e.a(128);
        Integer numA3 = e.a(192);
        Integer numA4 = e.a(256);
        map2.put("DES", numA);
        map2.put("DESEDE", numA3);
        map2.put("BLOWFISH", numA2);
        map2.put(AESUtils.AES, numA4);
        map2.put(b.f7816t.h(), numA2);
        map2.put(b.B.h(), numA3);
        map2.put(b.J.h(), numA4);
        map2.put(b.f7817u.h(), numA2);
        map2.put(b.C.h(), numA3);
        C0364n c0364n = b.K;
        map2.put(c0364n.h(), numA4);
        map2.put(b.f7819w.h(), numA2);
        map2.put(b.E.h(), numA3);
        map2.put(b.M.h(), numA4);
        map2.put(b.f7818v.h(), numA2);
        map2.put(b.D.h(), numA3);
        map2.put(b.L.h(), numA4);
        C0364n c0364n2 = b.f7820x;
        map2.put(c0364n2.h(), numA2);
        map2.put(b.F.h(), numA3);
        map2.put(b.N.h(), numA4);
        C0364n c0364n3 = b.f7822z;
        map2.put(c0364n3.h(), numA2);
        map2.put(b.H.h(), numA3);
        map2.put(b.P.h(), numA4);
        map2.put(b.f7821y.h(), numA2);
        map2.put(b.G.h(), numA3);
        map2.put(b.O.h(), numA4);
        C0364n c0364n4 = a.f7706f;
        map2.put(c0364n4.h(), numA4);
        map2.put(a.f7704d.h(), numA4);
        map2.put(a.f7705e.h(), numA4);
        map.put(AESUtils.AES, c0364n);
        map3.put(c0364n4.h(), "GOST28147");
        map3.put(c0364n2.h(), AESUtils.AES);
        map3.put(c0364n3.h(), AESUtils.AES);
        map3.put(c0364n3.h(), AESUtils.AES);
        hashtable.put(AESUtils.AES, c0364n);
        hashtable2.put("DES", "DES");
        hashtable2.put("DESEDE", "DES");
    }

    public BaseAgreementSpi(String str, f fVar) {
        this.kaAlgorithm = str;
        this.kdf = fVar;
    }

    public static String getAlgorithm(String str) {
        if (str.indexOf(91) > 0) {
            return str.substring(0, str.indexOf(91));
        }
        if (str.startsWith(b.f7815s.h())) {
            return AESUtils.AES;
        }
        if (str.startsWith(c.a.a.d.a.f7778i.h())) {
            return "Serpent";
        }
        String str2 = nameTable.get(l.b(str));
        return str2 != null ? str2 : str;
    }

    public static int getKeySize(String str) {
        if (str.indexOf(91) > 0) {
            return Integer.parseInt(str.substring(str.indexOf(91) + 1, str.indexOf(93)));
        }
        String strB = l.b(str);
        Map<String, Integer> map = keySizes;
        if (map.containsKey(strB)) {
            return map.get(strB).intValue();
        }
        return -1;
    }

    public static byte[] trimZeroes(byte[] bArr) {
        if (bArr[0] != 0) {
            return bArr;
        }
        int i2 = 0;
        while (i2 < bArr.length && bArr[i2] == 0) {
            i2++;
        }
        int length = bArr.length - i2;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, i2, bArr2, 0, length);
        return bArr2;
    }

    public abstract byte[] calcSecret();

    @Override // javax.crypto.KeyAgreementSpi
    public byte[] engineGenerateSecret() {
        if (this.kdf == null) {
            return calcSecret();
        }
        throw new UnsupportedOperationException("KDF can only be used when algorithm is known");
    }

    @Override // javax.crypto.KeyAgreementSpi
    public int engineGenerateSecret(byte[] bArr, int i2) throws ShortBufferException {
        byte[] bArrEngineGenerateSecret = engineGenerateSecret();
        if (bArr.length - i2 >= bArrEngineGenerateSecret.length) {
            System.arraycopy(bArrEngineGenerateSecret, 0, bArr, i2, bArrEngineGenerateSecret.length);
            return bArrEngineGenerateSecret.length;
        }
        throw new ShortBufferException(this.kaAlgorithm + " key agreement: need " + bArrEngineGenerateSecret.length + " bytes");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006b  */
    @Override // javax.crypto.KeyAgreementSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public javax.crypto.SecretKey engineGenerateSecret(java.lang.String r7) throws java.security.NoSuchAlgorithmException {
        /*
            r6 = this;
            byte[] r0 = r6.calcSecret()
            java.lang.String r1 = c.a.d.l.b(r7)
            java.util.Hashtable r2 = org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi.oids
            boolean r3 = r2.containsKey(r1)
            if (r3 == 0) goto L1b
            java.lang.Object r1 = r2.get(r1)
            c.a.a.n r1 = (c.a.a.C0364n) r1
            java.lang.String r1 = r1.h()
            goto L1c
        L1b:
            r1 = r7
        L1c:
            int r2 = getKeySize(r1)
            c.a.b.f r3 = r6.kdf
            r4 = 0
            if (r3 == 0) goto L55
            if (r2 < 0) goto L3e
            int r2 = r2 / 8
            byte[] r1 = new byte[r2]
            c.a.b.h.j r3 = new c.a.b.h.j
            byte[] r5 = r6.ukmParameters
            r3.<init>(r0, r5)
            c.a.b.f r0 = r6.kdf
            r0.a(r3)
            c.a.b.f r0 = r6.kdf
            r0.a(r1, r4, r2)
        L3c:
            r0 = r1
            goto L5f
        L3e:
            java.security.NoSuchAlgorithmException r7 = new java.security.NoSuchAlgorithmException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "unknown algorithm encountered: "
            r0.append(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0)
            throw r7
        L55:
            if (r2 <= 0) goto L5f
            int r2 = r2 / 8
            byte[] r1 = new byte[r2]
            java.lang.System.arraycopy(r0, r4, r1, r4, r2)
            goto L3c
        L5f:
            java.lang.String r7 = getAlgorithm(r7)
            java.util.Hashtable r1 = org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi.des
            boolean r1 = r1.containsKey(r7)
            if (r1 == 0) goto L6e
            c.a.b.h.c.a(r0)
        L6e:
            javax.crypto.spec.SecretKeySpec r1 = new javax.crypto.spec.SecretKeySpec
            r1.<init>(r0, r7)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi.engineGenerateSecret(java.lang.String):javax.crypto.SecretKey");
    }
}
