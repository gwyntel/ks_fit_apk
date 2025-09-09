package c.a.a.a;

import c.a.a.C0364n;
import c.a.d.l;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static X9ECParametersHolder f7695a = new a();

    /* renamed from: b, reason: collision with root package name */
    public static final Hashtable f7696b = new Hashtable();

    /* renamed from: c, reason: collision with root package name */
    public static final Hashtable f7697c = new Hashtable();

    /* renamed from: d, reason: collision with root package name */
    public static final Hashtable f7698d = new Hashtable();

    static {
        a("FRP256v1", c.f7699a, f7695a);
    }

    public static ECCurve b(ECCurve eCCurve) {
        return eCCurve;
    }

    public static X9ECParameters c(String str) {
        C0364n c0364nD = d(str);
        if (c0364nD == null) {
            return null;
        }
        return a(c0364nD);
    }

    public static C0364n d(String str) {
        return (C0364n) f7696b.get(l.a(str));
    }

    public static BigInteger b(String str) {
        return new BigInteger(1, Hex.decode(str));
    }

    public static void a(String str, C0364n c0364n, X9ECParametersHolder x9ECParametersHolder) {
        f7696b.put(l.a(str), c0364n);
        f7698d.put(c0364n, str);
        f7697c.put(c0364n, x9ECParametersHolder);
    }

    public static String b(C0364n c0364n) {
        return (String) f7698d.get(c0364n);
    }

    public static X9ECParameters a(C0364n c0364n) {
        X9ECParametersHolder x9ECParametersHolder = (X9ECParametersHolder) f7697c.get(c0364n);
        if (x9ECParametersHolder == null) {
            return null;
        }
        return x9ECParametersHolder.getParameters();
    }

    public static Enumeration a() {
        return f7698d.elements();
    }
}
