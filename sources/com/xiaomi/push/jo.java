package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class jo implements jy<jo, Object>, Serializable, Cloneable {

    /* renamed from: a, reason: collision with other field name */
    public int f803a;

    /* renamed from: a, reason: collision with other field name */
    public long f804a;

    /* renamed from: a, reason: collision with other field name */
    public jc f805a;

    /* renamed from: a, reason: collision with other field name */
    public String f806a;

    /* renamed from: a, reason: collision with other field name */
    public List<String> f808a;

    /* renamed from: b, reason: collision with other field name */
    public int f810b;

    /* renamed from: b, reason: collision with other field name */
    public long f811b;

    /* renamed from: b, reason: collision with other field name */
    public String f812b;

    /* renamed from: c, reason: collision with other field name */
    public long f813c;

    /* renamed from: c, reason: collision with other field name */
    public String f814c;

    /* renamed from: d, reason: collision with other field name */
    public String f815d;

    /* renamed from: e, reason: collision with other field name */
    public String f816e;

    /* renamed from: f, reason: collision with other field name */
    public String f817f;

    /* renamed from: g, reason: collision with other field name */
    public String f818g;

    /* renamed from: h, reason: collision with other field name */
    public String f819h;

    /* renamed from: i, reason: collision with other field name */
    public String f820i;

    /* renamed from: j, reason: collision with other field name */
    public String f821j;

    /* renamed from: k, reason: collision with other field name */
    public String f822k;

    /* renamed from: l, reason: collision with other field name */
    public String f823l;

    /* renamed from: a, reason: collision with other field name */
    private static final ko f802a = new ko("XmPushActionRegistrationResult");

    /* renamed from: a, reason: collision with root package name */
    private static final kg f24271a = new kg("", (byte) 11, 1);

    /* renamed from: b, reason: collision with root package name */
    private static final kg f24272b = new kg("", (byte) 12, 2);

    /* renamed from: c, reason: collision with root package name */
    private static final kg f24273c = new kg("", (byte) 11, 3);

    /* renamed from: d, reason: collision with root package name */
    private static final kg f24274d = new kg("", (byte) 11, 4);

    /* renamed from: e, reason: collision with root package name */
    private static final kg f24275e = new kg("", (byte) 10, 6);

    /* renamed from: f, reason: collision with root package name */
    private static final kg f24276f = new kg("", (byte) 11, 7);

    /* renamed from: g, reason: collision with root package name */
    private static final kg f24277g = new kg("", (byte) 11, 8);

    /* renamed from: h, reason: collision with root package name */
    private static final kg f24278h = new kg("", (byte) 11, 9);

    /* renamed from: i, reason: collision with root package name */
    private static final kg f24279i = new kg("", (byte) 11, 10);

    /* renamed from: j, reason: collision with root package name */
    private static final kg f24280j = new kg("", (byte) 10, 11);

    /* renamed from: k, reason: collision with root package name */
    private static final kg f24281k = new kg("", (byte) 11, 12);

    /* renamed from: l, reason: collision with root package name */
    private static final kg f24282l = new kg("", (byte) 11, 13);

    /* renamed from: m, reason: collision with root package name */
    private static final kg f24283m = new kg("", (byte) 10, 14);

    /* renamed from: n, reason: collision with root package name */
    private static final kg f24284n = new kg("", (byte) 11, 15);

    /* renamed from: o, reason: collision with root package name */
    private static final kg f24285o = new kg("", (byte) 8, 16);

    /* renamed from: p, reason: collision with root package name */
    private static final kg f24286p = new kg("", (byte) 11, 17);

    /* renamed from: q, reason: collision with root package name */
    private static final kg f24287q = new kg("", (byte) 8, 18);

    /* renamed from: r, reason: collision with root package name */
    private static final kg f24288r = new kg("", (byte) 11, 19);

    /* renamed from: s, reason: collision with root package name */
    private static final kg f24289s = new kg("", (byte) 2, 20);

    /* renamed from: t, reason: collision with root package name */
    private static final kg f24290t = new kg("", (byte) 15, 21);

    /* renamed from: a, reason: collision with other field name */
    private BitSet f807a = new BitSet(6);

    /* renamed from: a, reason: collision with other field name */
    public boolean f809a = false;

    /* renamed from: a, reason: collision with other method in class */
    public boolean m625a() {
        return this.f806a != null;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m627b() {
        return this.f805a != null;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m628c() {
        return this.f812b != null;
    }

    public boolean d() {
        return this.f814c != null;
    }

    public boolean e() {
        return this.f807a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof jo)) {
            return m626a((jo) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f815d != null;
    }

    public boolean g() {
        return this.f816e != null;
    }

    public boolean h() {
        return this.f817f != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f818g != null;
    }

    public boolean j() {
        return this.f807a.get(1);
    }

    public boolean k() {
        return this.f819h != null;
    }

    public boolean l() {
        return this.f820i != null;
    }

    public boolean m() {
        return this.f807a.get(2);
    }

    public boolean n() {
        return this.f821j != null;
    }

    public boolean o() {
        return this.f807a.get(3);
    }

    public boolean p() {
        return this.f822k != null;
    }

    public boolean q() {
        return this.f807a.get(4);
    }

    public boolean r() {
        return this.f823l != null;
    }

    public boolean s() {
        return this.f807a.get(5);
    }

    public boolean t() {
        return this.f808a != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionRegistrationResult(");
        boolean z3 = false;
        if (m625a()) {
            sb.append("debug:");
            String str = this.f806a;
            if (str == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (m627b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            jc jcVar = this.f805a;
            if (jcVar == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(jcVar);
            }
        } else {
            z3 = z2;
        }
        if (!z3) {
            sb.append(", ");
        }
        sb.append("id:");
        String str2 = this.f812b;
        if (str2 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(com.xiaomi.push.service.bc.a(str2));
        }
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f814c;
        if (str3 == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            sb.append(str3);
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f804a);
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.f815d;
            if (str4 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str4);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("regId:");
            String str5 = this.f816e;
            if (str5 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str5);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f818g;
            if (str6 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str6);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("registeredAt:");
            sb.append(this.f811b);
        }
        if (k()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str7 = this.f819h;
            if (str7 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str7);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("clientId:");
            String str8 = this.f820i;
            if (str8 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str8);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("costTime:");
            sb.append(this.f813c);
        }
        if (n()) {
            sb.append(", ");
            sb.append("appVersion:");
            String str9 = this.f821j;
            if (str9 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str9);
            }
        }
        if (o()) {
            sb.append(", ");
            sb.append("pushSdkVersionCode:");
            sb.append(this.f803a);
        }
        if (p()) {
            sb.append(", ");
            sb.append("hybridPushEndpoint:");
            String str10 = this.f822k;
            if (str10 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str10);
            }
        }
        if (q()) {
            sb.append(", ");
            sb.append("appVersionCode:");
            sb.append(this.f810b);
        }
        if (r()) {
            sb.append(", ");
            sb.append("region:");
            String str11 = this.f823l;
            if (str11 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(str11);
            }
        }
        if (s()) {
            sb.append(", ");
            sb.append("isHybridFrame:");
            sb.append(this.f809a);
        }
        if (t()) {
            sb.append(", ");
            sb.append("autoMarkPkgs:");
            List<String> list = this.f808a;
            if (list == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append(list);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m622a() {
        return this.f812b;
    }

    public String b() {
        return this.f817f;
    }

    public String c() {
        return this.f818g;
    }

    public void d(boolean z2) {
        this.f807a.set(3, z2);
    }

    public void e(boolean z2) {
        this.f807a.set(4, z2);
    }

    public void f(boolean z2) {
        this.f807a.set(5, z2);
    }

    public long a() {
        return this.f804a;
    }

    public void b(boolean z2) {
        this.f807a.set(1, z2);
    }

    public void c(boolean z2) {
        this.f807a.set(2, z2);
    }

    public void a(boolean z2) {
        this.f807a.set(0, z2);
    }

    @Override // com.xiaomi.push.jy
    public void b(kj kjVar) throws kk {
        m624a();
        kjVar.a(f802a);
        if (this.f806a != null && m625a()) {
            kjVar.a(f24271a);
            kjVar.a(this.f806a);
            kjVar.b();
        }
        if (this.f805a != null && m627b()) {
            kjVar.a(f24272b);
            this.f805a.b(kjVar);
            kjVar.b();
        }
        if (this.f812b != null) {
            kjVar.a(f24273c);
            kjVar.a(this.f812b);
            kjVar.b();
        }
        if (this.f814c != null) {
            kjVar.a(f24274d);
            kjVar.a(this.f814c);
            kjVar.b();
        }
        kjVar.a(f24275e);
        kjVar.a(this.f804a);
        kjVar.b();
        if (this.f815d != null && f()) {
            kjVar.a(f24276f);
            kjVar.a(this.f815d);
            kjVar.b();
        }
        if (this.f816e != null && g()) {
            kjVar.a(f24277g);
            kjVar.a(this.f816e);
            kjVar.b();
        }
        if (this.f817f != null && h()) {
            kjVar.a(f24278h);
            kjVar.a(this.f817f);
            kjVar.b();
        }
        if (this.f818g != null && i()) {
            kjVar.a(f24279i);
            kjVar.a(this.f818g);
            kjVar.b();
        }
        if (j()) {
            kjVar.a(f24280j);
            kjVar.a(this.f811b);
            kjVar.b();
        }
        if (this.f819h != null && k()) {
            kjVar.a(f24281k);
            kjVar.a(this.f819h);
            kjVar.b();
        }
        if (this.f820i != null && l()) {
            kjVar.a(f24282l);
            kjVar.a(this.f820i);
            kjVar.b();
        }
        if (m()) {
            kjVar.a(f24283m);
            kjVar.a(this.f813c);
            kjVar.b();
        }
        if (this.f821j != null && n()) {
            kjVar.a(f24284n);
            kjVar.a(this.f821j);
            kjVar.b();
        }
        if (o()) {
            kjVar.a(f24285o);
            kjVar.mo679a(this.f803a);
            kjVar.b();
        }
        if (this.f822k != null && p()) {
            kjVar.a(f24286p);
            kjVar.a(this.f822k);
            kjVar.b();
        }
        if (q()) {
            kjVar.a(f24287q);
            kjVar.mo679a(this.f810b);
            kjVar.b();
        }
        if (this.f823l != null && r()) {
            kjVar.a(f24288r);
            kjVar.a(this.f823l);
            kjVar.b();
        }
        if (s()) {
            kjVar.a(f24289s);
            kjVar.a(this.f809a);
            kjVar.b();
        }
        if (this.f808a != null && t()) {
            kjVar.a(f24290t);
            kjVar.a(new kh((byte) 11, this.f808a.size()));
            Iterator<String> it = this.f808a.iterator();
            while (it.hasNext()) {
                kjVar.a(it.next());
            }
            kjVar.e();
            kjVar.b();
        }
        kjVar.c();
        kjVar.mo678a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public List<String> m623a() {
        return this.f808a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m626a(jo joVar) {
        if (joVar == null) {
            return false;
        }
        boolean zM625a = m625a();
        boolean zM625a2 = joVar.m625a();
        if ((zM625a || zM625a2) && !(zM625a && zM625a2 && this.f806a.equals(joVar.f806a))) {
            return false;
        }
        boolean zM627b = m627b();
        boolean zM627b2 = joVar.m627b();
        if ((zM627b || zM627b2) && !(zM627b && zM627b2 && this.f805a.m571a(joVar.f805a))) {
            return false;
        }
        boolean zM628c = m628c();
        boolean zM628c2 = joVar.m628c();
        if ((zM628c || zM628c2) && !(zM628c && zM628c2 && this.f812b.equals(joVar.f812b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = joVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.f814c.equals(joVar.f814c))) || this.f804a != joVar.f804a) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = joVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f815d.equals(joVar.f815d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = joVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.f816e.equals(joVar.f816e))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = joVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.f817f.equals(joVar.f817f))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = joVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f818g.equals(joVar.f818g))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = joVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f811b == joVar.f811b)) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = joVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f819h.equals(joVar.f819h))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = joVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f820i.equals(joVar.f820i))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = joVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f813c == joVar.f813c)) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = joVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.f821j.equals(joVar.f821j))) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = joVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.f803a == joVar.f803a)) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = joVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.f822k.equals(joVar.f822k))) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = joVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.f810b == joVar.f810b)) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = joVar.r();
        if ((zR || zR2) && !(zR && zR2 && this.f823l.equals(joVar.f823l))) {
            return false;
        }
        boolean zS = s();
        boolean zS2 = joVar.s();
        if ((zS || zS2) && !(zS && zS2 && this.f809a == joVar.f809a)) {
            return false;
        }
        boolean zT = t();
        boolean zT2 = joVar.t();
        if (zT || zT2) {
            return zT && zT2 && this.f808a.equals(joVar.f808a);
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(jo joVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        int iA10;
        int iA11;
        int iA12;
        int iA13;
        int iA14;
        int iA15;
        int iA16;
        int iA17;
        int iA18;
        int iA19;
        int iA20;
        if (!getClass().equals(joVar.getClass())) {
            return getClass().getName().compareTo(joVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(m625a()).compareTo(Boolean.valueOf(joVar.m625a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (m625a() && (iA20 = jz.a(this.f806a, joVar.f806a)) != 0) {
            return iA20;
        }
        int iCompareTo2 = Boolean.valueOf(m627b()).compareTo(Boolean.valueOf(joVar.m627b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (m627b() && (iA19 = jz.a(this.f805a, joVar.f805a)) != 0) {
            return iA19;
        }
        int iCompareTo3 = Boolean.valueOf(m628c()).compareTo(Boolean.valueOf(joVar.m628c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (m628c() && (iA18 = jz.a(this.f812b, joVar.f812b)) != 0) {
            return iA18;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(joVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA17 = jz.a(this.f814c, joVar.f814c)) != 0) {
            return iA17;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(joVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA16 = jz.a(this.f804a, joVar.f804a)) != 0) {
            return iA16;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(joVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA15 = jz.a(this.f815d, joVar.f815d)) != 0) {
            return iA15;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(joVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA14 = jz.a(this.f816e, joVar.f816e)) != 0) {
            return iA14;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(joVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA13 = jz.a(this.f817f, joVar.f817f)) != 0) {
            return iA13;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(joVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA12 = jz.a(this.f818g, joVar.f818g)) != 0) {
            return iA12;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(joVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA11 = jz.a(this.f811b, joVar.f811b)) != 0) {
            return iA11;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(joVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA10 = jz.a(this.f819h, joVar.f819h)) != 0) {
            return iA10;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(joVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA9 = jz.a(this.f820i, joVar.f820i)) != 0) {
            return iA9;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(joVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA8 = jz.a(this.f813c, joVar.f813c)) != 0) {
            return iA8;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(joVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA7 = jz.a(this.f821j, joVar.f821j)) != 0) {
            return iA7;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(joVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (o() && (iA6 = jz.a(this.f803a, joVar.f803a)) != 0) {
            return iA6;
        }
        int iCompareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(joVar.p()));
        if (iCompareTo16 != 0) {
            return iCompareTo16;
        }
        if (p() && (iA5 = jz.a(this.f822k, joVar.f822k)) != 0) {
            return iA5;
        }
        int iCompareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(joVar.q()));
        if (iCompareTo17 != 0) {
            return iCompareTo17;
        }
        if (q() && (iA4 = jz.a(this.f810b, joVar.f810b)) != 0) {
            return iA4;
        }
        int iCompareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(joVar.r()));
        if (iCompareTo18 != 0) {
            return iCompareTo18;
        }
        if (r() && (iA3 = jz.a(this.f823l, joVar.f823l)) != 0) {
            return iA3;
        }
        int iCompareTo19 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(joVar.s()));
        if (iCompareTo19 != 0) {
            return iCompareTo19;
        }
        if (s() && (iA2 = jz.a(this.f809a, joVar.f809a)) != 0) {
            return iA2;
        }
        int iCompareTo20 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(joVar.t()));
        if (iCompareTo20 != 0) {
            return iCompareTo20;
        }
        if (!t() || (iA = jz.a(this.f808a, joVar.f808a)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // com.xiaomi.push.jy
    public void a(kj kjVar) throws kk {
        kjVar.mo674a();
        while (true) {
            kg kgVarMo670a = kjVar.mo670a();
            byte b2 = kgVarMo670a.f24379a;
            if (b2 == 0) {
                kjVar.f();
                if (e()) {
                    m624a();
                    return;
                }
                throw new kk("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (kgVarMo670a.f922a) {
                case 1:
                    if (b2 == 11) {
                        this.f806a = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 2:
                    if (b2 == 12) {
                        jc jcVar = new jc();
                        this.f805a = jcVar;
                        jcVar.a(kjVar);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 3:
                    if (b2 == 11) {
                        this.f812b = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 4:
                    if (b2 == 11) {
                        this.f814c = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 5:
                default:
                    km.a(kjVar, b2);
                    break;
                case 6:
                    if (b2 == 10) {
                        this.f804a = kjVar.mo669a();
                        a(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 7:
                    if (b2 == 11) {
                        this.f815d = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 8:
                    if (b2 == 11) {
                        this.f816e = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 9:
                    if (b2 == 11) {
                        this.f817f = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 10:
                    if (b2 == 11) {
                        this.f818g = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 11:
                    if (b2 == 10) {
                        this.f811b = kjVar.mo669a();
                        b(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 12:
                    if (b2 == 11) {
                        this.f819h = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 13:
                    if (b2 == 11) {
                        this.f820i = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 14:
                    if (b2 == 10) {
                        this.f813c = kjVar.mo669a();
                        c(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 15:
                    if (b2 == 11) {
                        this.f821j = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 16:
                    if (b2 == 8) {
                        this.f803a = kjVar.mo668a();
                        d(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 17:
                    if (b2 == 11) {
                        this.f822k = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 18:
                    if (b2 == 8) {
                        this.f810b = kjVar.mo668a();
                        e(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 19:
                    if (b2 == 11) {
                        this.f823l = kjVar.mo675a();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 20:
                    if (b2 == 2) {
                        this.f809a = kjVar.mo680a();
                        f(true);
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
                case 21:
                    if (b2 == 15) {
                        kh khVarMo671a = kjVar.mo671a();
                        this.f808a = new ArrayList(khVarMo671a.f923a);
                        for (int i2 = 0; i2 < khVarMo671a.f923a; i2++) {
                            this.f808a.add(kjVar.mo675a());
                        }
                        kjVar.i();
                        break;
                    } else {
                        km.a(kjVar, b2);
                        break;
                    }
            }
            kjVar.g();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m624a() throws kk {
        if (this.f812b != null) {
            if (this.f814c != null) {
                return;
            }
            throw new kk("Required field 'appId' was not present! Struct: " + toString());
        }
        throw new kk("Required field 'id' was not present! Struct: " + toString());
    }
}
