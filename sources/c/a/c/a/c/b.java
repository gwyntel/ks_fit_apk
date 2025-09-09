package c.a.c.a.c;

import c.a.a.C0364n;

/* loaded from: classes2.dex */
public abstract class b extends a {
    public void addSignatureAlgorithm(c.a.c.a.a.a aVar, String str, String str2, String str3, C0364n c0364n) {
        String str4 = str + "WITH" + str2;
        String str5 = str + "with" + str2;
        String str6 = str + "With" + str2;
        aVar.addAlgorithm("Signature." + str4, str3);
        aVar.addAlgorithm("Alg.Alias.Signature." + str5, str4);
        aVar.addAlgorithm("Alg.Alias.Signature." + str6, str4);
        aVar.addAlgorithm("Alg.Alias.Signature." + (str + "/" + str2), str4);
        aVar.addAlgorithm("Alg.Alias.Signature." + c0364n, str4);
        aVar.addAlgorithm("Alg.Alias.Signature.OID." + c0364n, str4);
    }

    public void registerOid(c.a.c.a.a.a aVar, C0364n c0364n, String str, c cVar) {
        aVar.addAlgorithm("Alg.Alias.KeyFactory." + c0364n, str);
        aVar.addAlgorithm("Alg.Alias.KeyPairGenerator." + c0364n, str);
        aVar.addKeyInfoConverter(c0364n, cVar);
    }

    public void registerOidAlgorithmParameterGenerator(c.a.c.a.a.a aVar, C0364n c0364n, String str) {
        aVar.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + c0364n, str);
        aVar.addAlgorithm("Alg.Alias.AlgorithmParameters." + c0364n, str);
    }

    public void registerOidAlgorithmParameters(c.a.c.a.a.a aVar, C0364n c0364n, String str) {
        aVar.addAlgorithm("Alg.Alias.AlgorithmParameters." + c0364n, str);
    }
}
