package c.a.a.h;

import c.a.a.C0364n;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.endo.GLVTypeBEndomorphism;
import org.spongycastle.math.ec.endo.GLVTypeBParameters;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
public class I {

    /* renamed from: a, reason: collision with root package name */
    public static X9ECParametersHolder f7850a = new l();

    /* renamed from: b, reason: collision with root package name */
    public static X9ECParametersHolder f7851b = new w();

    /* renamed from: c, reason: collision with root package name */
    public static X9ECParametersHolder f7852c = new B();

    /* renamed from: d, reason: collision with root package name */
    public static X9ECParametersHolder f7853d = new C();

    /* renamed from: e, reason: collision with root package name */
    public static X9ECParametersHolder f7854e = new D();

    /* renamed from: f, reason: collision with root package name */
    public static X9ECParametersHolder f7855f = new E();

    /* renamed from: g, reason: collision with root package name */
    public static X9ECParametersHolder f7856g = new F();

    /* renamed from: h, reason: collision with root package name */
    public static X9ECParametersHolder f7857h = new G();

    /* renamed from: i, reason: collision with root package name */
    public static X9ECParametersHolder f7858i = new H();

    /* renamed from: j, reason: collision with root package name */
    public static X9ECParametersHolder f7859j = new C0350b();

    /* renamed from: k, reason: collision with root package name */
    public static X9ECParametersHolder f7860k = new C0351c();

    /* renamed from: l, reason: collision with root package name */
    public static X9ECParametersHolder f7861l = new C0352d();

    /* renamed from: m, reason: collision with root package name */
    public static X9ECParametersHolder f7862m = new C0353e();

    /* renamed from: n, reason: collision with root package name */
    public static X9ECParametersHolder f7863n = new C0354f();

    /* renamed from: o, reason: collision with root package name */
    public static X9ECParametersHolder f7864o = new C0355g();

    /* renamed from: p, reason: collision with root package name */
    public static X9ECParametersHolder f7865p = new C0356h();

    /* renamed from: q, reason: collision with root package name */
    public static X9ECParametersHolder f7866q = new C0357i();

    /* renamed from: r, reason: collision with root package name */
    public static X9ECParametersHolder f7867r = new C0358j();

    /* renamed from: s, reason: collision with root package name */
    public static X9ECParametersHolder f7868s = new k();

    /* renamed from: t, reason: collision with root package name */
    public static X9ECParametersHolder f7869t = new m();

    /* renamed from: u, reason: collision with root package name */
    public static X9ECParametersHolder f7870u = new n();

    /* renamed from: v, reason: collision with root package name */
    public static X9ECParametersHolder f7871v = new o();

    /* renamed from: w, reason: collision with root package name */
    public static X9ECParametersHolder f7872w = new p();

    /* renamed from: x, reason: collision with root package name */
    public static X9ECParametersHolder f7873x = new q();

    /* renamed from: y, reason: collision with root package name */
    public static X9ECParametersHolder f7874y = new r();

    /* renamed from: z, reason: collision with root package name */
    public static X9ECParametersHolder f7875z = new s();
    public static X9ECParametersHolder A = new t();
    public static X9ECParametersHolder B = new u();
    public static X9ECParametersHolder C = new v();
    public static X9ECParametersHolder D = new x();
    public static X9ECParametersHolder E = new y();
    public static X9ECParametersHolder F = new z();
    public static X9ECParametersHolder G = new A();
    public static final Hashtable H = new Hashtable();
    public static final Hashtable I = new Hashtable();
    public static final Hashtable J = new Hashtable();

    static {
        a("secp112r1", J.f7882g, f7850a);
        a("secp112r2", J.f7883h, f7851b);
        a("secp128r1", J.f7896u, f7852c);
        a("secp128r2", J.f7897v, f7853d);
        a("secp160k1", J.f7885j, f7854e);
        a("secp160r1", J.f7884i, f7855f);
        a("secp160r2", J.f7898w, f7856g);
        a("secp192k1", J.f7899x, f7857h);
        a("secp192r1", J.G, f7858i);
        a("secp224k1", J.f7900y, f7859j);
        a("secp224r1", J.f7901z, f7860k);
        a("secp256k1", J.f7886k, f7861l);
        a("secp256r1", J.H, f7862m);
        a("secp384r1", J.A, f7863n);
        a("secp521r1", J.B, f7864o);
        a("sect113r1", J.f7880e, f7865p);
        a("sect113r2", J.f7881f, f7866q);
        a("sect131r1", J.f7890o, f7867r);
        a("sect131r2", J.f7891p, f7868s);
        a("sect163k1", J.f7877b, f7869t);
        a("sect163r1", J.f7878c, f7870u);
        a("sect163r2", J.f7887l, f7871v);
        a("sect193r1", J.f7892q, f7872w);
        a("sect193r2", J.f7893r, f7873x);
        a("sect233k1", J.f7894s, f7874y);
        a("sect233r1", J.f7895t, f7875z);
        a("sect239k1", J.f7879d, A);
        a("sect283k1", J.f7888m, B);
        a("sect283r1", J.f7889n, C);
        a("sect409k1", J.C, D);
        a("sect409r1", J.D, E);
        a("sect571k1", J.E, F);
        a("sect571r1", J.F, G);
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
        return (C0364n) H.get(c.a.d.l.a(str));
    }

    public static ECCurve b(ECCurve eCCurve, GLVTypeBParameters gLVTypeBParameters) {
        return eCCurve.configure().setEndomorphism(new GLVTypeBEndomorphism(eCCurve, gLVTypeBParameters)).create();
    }

    public static BigInteger b(String str) {
        return new BigInteger(1, Hex.decode(str));
    }

    public static void a(String str, C0364n c0364n, X9ECParametersHolder x9ECParametersHolder) {
        H.put(str, c0364n);
        J.put(c0364n, str);
        I.put(c0364n, x9ECParametersHolder);
    }

    public static String b(C0364n c0364n) {
        return (String) J.get(c0364n);
    }

    public static X9ECParameters a(C0364n c0364n) {
        X9ECParametersHolder x9ECParametersHolder = (X9ECParametersHolder) I.get(c0364n);
        if (x9ECParametersHolder == null) {
            return null;
        }
        return x9ECParametersHolder.getParameters();
    }

    public static Enumeration a() {
        return J.elements();
    }
}
