package c.a.a.e;

import c.a.a.C0364n;
import c.a.a.h.I;
import c.a.a.h.J;
import c.a.d.l;
import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.asn1.x9.X9ECParameters;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final Hashtable f7795a = new Hashtable();

    /* renamed from: b, reason: collision with root package name */
    public static final Hashtable f7796b = new Hashtable();

    static {
        a("B-571", J.F);
        a("B-409", J.D);
        a("B-283", J.f7889n);
        a("B-233", J.f7895t);
        a("B-163", J.f7887l);
        a("K-571", J.E);
        a("K-409", J.C);
        a("K-283", J.f7888m);
        a("K-233", J.f7894s);
        a("K-163", J.f7877b);
        a("P-521", J.B);
        a("P-384", J.A);
        a("P-256", J.H);
        a("P-224", J.f7901z);
        a("P-192", J.G);
    }

    public static void a(String str, C0364n c0364n) {
        f7795a.put(str, c0364n);
        f7796b.put(c0364n, str);
    }

    public static C0364n b(String str) {
        return (C0364n) f7795a.get(l.b(str));
    }

    public static String b(C0364n c0364n) {
        return (String) f7796b.get(c0364n);
    }

    public static X9ECParameters a(String str) {
        C0364n c0364n = (C0364n) f7795a.get(l.b(str));
        if (c0364n != null) {
            return a(c0364n);
        }
        return null;
    }

    public static X9ECParameters a(C0364n c0364n) {
        return I.a(c0364n);
    }

    public static Enumeration a() {
        return f7795a.keys();
    }
}
