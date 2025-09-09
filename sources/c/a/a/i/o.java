package c.a.a.i;

import c.a.a.C0364n;
import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.math.ec.ECCurve;

/* loaded from: classes2.dex */
public class o {

    /* renamed from: a, reason: collision with root package name */
    public static X9ECParametersHolder f7905a = new f();

    /* renamed from: b, reason: collision with root package name */
    public static X9ECParametersHolder f7906b = new g();

    /* renamed from: c, reason: collision with root package name */
    public static X9ECParametersHolder f7907c = new h();

    /* renamed from: d, reason: collision with root package name */
    public static X9ECParametersHolder f7908d = new i();

    /* renamed from: e, reason: collision with root package name */
    public static X9ECParametersHolder f7909e = new j();

    /* renamed from: f, reason: collision with root package name */
    public static X9ECParametersHolder f7910f = new k();

    /* renamed from: g, reason: collision with root package name */
    public static X9ECParametersHolder f7911g = new l();

    /* renamed from: h, reason: collision with root package name */
    public static X9ECParametersHolder f7912h = new m();

    /* renamed from: i, reason: collision with root package name */
    public static X9ECParametersHolder f7913i = new n();

    /* renamed from: j, reason: collision with root package name */
    public static X9ECParametersHolder f7914j = new a();

    /* renamed from: k, reason: collision with root package name */
    public static X9ECParametersHolder f7915k = new b();

    /* renamed from: l, reason: collision with root package name */
    public static X9ECParametersHolder f7916l = new c();

    /* renamed from: m, reason: collision with root package name */
    public static X9ECParametersHolder f7917m = new d();

    /* renamed from: n, reason: collision with root package name */
    public static X9ECParametersHolder f7918n = new e();

    /* renamed from: o, reason: collision with root package name */
    public static final Hashtable f7919o = new Hashtable();

    /* renamed from: p, reason: collision with root package name */
    public static final Hashtable f7920p = new Hashtable();

    /* renamed from: q, reason: collision with root package name */
    public static final Hashtable f7921q = new Hashtable();

    static {
        a("brainpoolp160r1", p.f7936o, f7905a);
        a("brainpoolp160t1", p.f7937p, f7906b);
        a("brainpoolp192r1", p.f7938q, f7907c);
        a("brainpoolp192t1", p.f7939r, f7908d);
        a("brainpoolp224r1", p.f7940s, f7909e);
        a("brainpoolp224t1", p.f7941t, f7910f);
        a("brainpoolp256r1", p.f7942u, f7911g);
        a("brainpoolp256t1", p.f7943v, f7912h);
        a("brainpoolp320r1", p.f7944w, f7913i);
        a("brainpoolp320t1", p.f7945x, f7914j);
        a("brainpoolp384r1", p.f7946y, f7915k);
        a("brainpoolp384t1", p.f7947z, f7916l);
        a("brainpoolp512r1", p.A, f7917m);
        a("brainpoolp512t1", p.B, f7918n);
    }

    public static ECCurve b(ECCurve eCCurve) {
        return eCCurve;
    }

    public static void a(String str, C0364n c0364n, X9ECParametersHolder x9ECParametersHolder) {
        f7919o.put(str, c0364n);
        f7921q.put(c0364n, str);
        f7920p.put(c0364n, x9ECParametersHolder);
    }

    public static C0364n b(String str) {
        return (C0364n) f7919o.get(c.a.d.l.a(str));
    }

    public static String b(C0364n c0364n) {
        return (String) f7921q.get(c0364n);
    }

    public static X9ECParameters a(String str) {
        C0364n c0364n = (C0364n) f7919o.get(c.a.d.l.a(str));
        if (c0364n != null) {
            return a(c0364n);
        }
        return null;
    }

    public static X9ECParameters a(C0364n c0364n) {
        X9ECParametersHolder x9ECParametersHolder = (X9ECParametersHolder) f7920p.get(c0364n);
        if (x9ECParametersHolder != null) {
            return x9ECParametersHolder.getParameters();
        }
        return null;
    }

    public static Enumeration a() {
        return f7919o.keys();
    }
}
